package controllers;

import java.sql.SQLException;
import java.util.List;

import facades.FacadeQuestionTag;
import models.QuestionTag;

public class ControllerQuestionTag {

	public static boolean canBeCreated(List<QuestionTag> qts) {

		// true because they are optional, if none, never mind
		if (qts == null || qts.isEmpty()) {
			return true;
		}

		// check for null label
		for (QuestionTag qt : qts) {
			final boolean canBeCreated = canBeCreated(qt);
			if (canBeCreated == false) {
				return false;
			}
		}

		return true;
	}

	private static boolean canBeCreated(QuestionTag qt) {

		return !(qt.getLabel() == null || qt.getLabel().isEmpty());
	}

	public static boolean create(QuestionTag qt, int questionId) throws SQLException {

		return FacadeQuestionTag.create(qt.getLabel(), questionId);

	}

}
