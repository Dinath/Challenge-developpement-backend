package routes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RouteMain {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String main() {
		return "<a target='_blank' href='https://github.com/Dinath/Challenge-developpement-backend'>https://github.com/Dinath/Challenge-developpement-backend</a>";
	}

}
