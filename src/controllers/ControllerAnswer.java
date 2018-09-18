package controllers;

import java.sql.SQLException;

import facades.FacadeAnswer;
import models.Answer;

public class ControllerAnswer {

	public static boolean canBeCreated(Answer a) {

		if (a == null) {
			return false;
		}

		if (a.getLabel() == null) {
			return false;
		}

		if (a.getLabel().isEmpty()) {
			return false;
		}

		return true;

	}

	public static boolean create(Answer a, int questionId) throws SQLException {

		return FacadeAnswer.create(a.getLabel(), questionId);

	}

}
