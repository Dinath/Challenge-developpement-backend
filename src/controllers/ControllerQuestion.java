package controllers;

import java.sql.SQLException;
import java.util.List;

import facades.FacadeQuestion;
import models.Question;
import utils.Utils;

public class ControllerQuestion {

	public static final String[] messages_search = { "",
			"You must supply at least " + Utils.SEARCH_lENGTH_MIN + " characters.",
			"Your query cannot contains special characters." };

	public static final String[] messages = { "Your question has been successfully created.",
			"Question label cannot be empty.", "You must specify an answer with a label.",
			"Question tags are optional, but you must specify label if any." };

	public static List<Question> search(String search) throws SQLException {

		// bit of security
		search = search.replaceAll(Utils.REGEX_STRING_ONLY_CHARS, "");
		// replace space(s) with %
		search = search.replaceAll("\\s+", "%");

		return FacadeQuestion.search("%" + search + "%");

	}

	public static byte canBeSearched(String search) {

		if (search.length() < Utils.SEARCH_lENGTH_MIN) {
			return 1;
		} else if (Utils.isStringContainingSpecials(search)) {
			return 2;
		}

		return 0;
	}

	public static byte canBeCreated(Question q) {

		if (q.getLabel() == null || q.getLabel().isEmpty()) {
			return 1;
		} else if (!ControllerAnswer.canBeCreated(q.getAnswer())) {
			return 2;
		} else if (!ControllerQuestionTag.canBeCreated(q.getTags())) {
			return 3;
		}

		return 0;
	}

	public static int create(Question q) throws SQLException {

		final int questionId = FacadeQuestion.create(q.getLabel());

		if (questionId == 0) {
			throw new SQLException(Utils.RESPONSE_MESSAGE_DB_UNKNOW_ERROR);
		}

		return questionId;
	}

}
