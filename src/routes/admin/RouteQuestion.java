package routes.admin;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import controllers.ControllerAnswer;
import controllers.ControllerQuestion;
import controllers.ControllerQuestionTag;
import facades.FacadeQuestion;
import models.Question;
import models.QuestionTag;
import utils.Utils;

@Path("question")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RouteQuestion {

	@GET
	@Path("/find")
	public Response find(@QueryParam("search") String search) {

		// "security"

		byte canBeSearched = ControllerQuestion.canBeSearched(search);

		if (canBeSearched != 0) {
			return Response.status(200).entity(
					Utils.userActionResponse(canBeSearched, ControllerQuestion.messages_search[canBeSearched], null))
					.build();
		}

		// query list of questions matching string

		try {
			final List<Question> questions = ControllerQuestion.search(search);

			if (questions.isEmpty()) {
				return Response.status(204).build();
			} else {
				return Response.status(200).entity(questions).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(500)
				.entity(Utils.userActionResponse((byte) 1, Utils.RESPONSE_MESSAGE_ERROR_UNKNOWN, null)).build();
	}

	@POST
	@Path("/admin/create")
	public Response create(Question q) {

		/**
		 * Check if the question can be created
		 */
		final byte canQuestionBeCreated = ControllerQuestion.canBeCreated(q);

		if (canQuestionBeCreated != 0) {

			return Response.status(400).entity(Utils.userActionResponse(canQuestionBeCreated,
					ControllerQuestion.messages[canQuestionBeCreated], null)).build();
		}

		/**
		 * If so, create it, and its children classes
		 */
		try {
			// question
			int questionId = ControllerQuestion.create(q);

			if (questionId == 0) {
				return Response.status(500)
						.entity(Utils.userActionResponse((byte) 1, Utils.RESPONSE_MESSAGE_ERROR_UNKNOWN, null)).build();
			}

			// answer
			ControllerAnswer.create(q.getAnswer(), questionId);

			// tags
			if (q.getTags() != null) {
				for (QuestionTag qt : q.getTags()) {
					ControllerQuestionTag.create(qt, questionId);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(500).entity(Utils.userActionResponse((byte) 1, e.getMessage(), null)).build();
		}

		return Response.status(201).entity(Utils.userActionResponse((byte) 0, ControllerQuestion.messages[0], null))
				.build();
	}

	@GET
	@Path("/admin")
	public Response findAll() {

		try {
			final List<Question> questions = FacadeQuestion.findAll(0);

			if (questions.isEmpty()) {
				return Response.status(204).build();
			} else {

				return Response.status(200).entity(questions).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/admin/{pagination}")
	public Response findAll(@PathParam("pagination") int pagination) {

		try {
			final List<Question> questions = FacadeQuestion.findAll(pagination);

			if (questions.isEmpty()) {
				return Response.status(204).build();
			} else {

				return Response.status(200).entity(questions).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

}
