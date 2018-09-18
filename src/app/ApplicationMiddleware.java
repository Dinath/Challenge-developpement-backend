package app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import facades.FacadeUser;
import models.User;
import utils.Utils;

@Provider
public class ApplicationMiddleware implements ContainerRequestFilter {

	private void responseDenied(ContainerRequestContext requestContext) {
		requestContext.abortWith(
				Response.status(Response.Status.UNAUTHORIZED).entity(Utils.RESPONSE_MESSAGE_UNAUTHORIZED).build());
	}

	private void responseError(ContainerRequestContext requestContext) {
		requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(Utils.RESPONSE_INTERNAL_SERVER_ERROR).build());
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		if (Utils.SECURITY_ENABLED) {

			/**
			 * Only protect the "admin" URI
			 */

			if (requestContext.getUriInfo().getPath().contains("admin")) {

				String basicHeaderAuth = requestContext.getHeaderString("Authorization");

				if (basicHeaderAuth != null) {

					/**
					 * Decode the authorization basic header
					 */
					basicHeaderAuth = basicHeaderAuth.substring("Basic".length()).trim();

					try {
						final String[] basicHeaderDecoded = new String(Base64.getDecoder().decode(basicHeaderAuth),
								StandardCharsets.UTF_8).split(":");

						/**
						 * Check if the user in database has the same encoded passwd
						 */
						final User u = FacadeUser.findByEmailAndGroup(basicHeaderDecoded[0], Utils.USER_GROUP_ID_ADMIN);

						if (u == null) {
							this.responseDenied(requestContext);
						} else {
							if (!Utils.passwordCheck(basicHeaderDecoded[1], u.getEntityPassword())) {
								this.responseDenied(requestContext);
							}
						}
					}
					// prevent sql exception
					// prevent bad Base64 code
					catch (Exception e) {
						e.printStackTrace();
						this.responseError(requestContext);
					}

				} else {
					this.responseDenied(requestContext);
				}

			}
		}
	}
}