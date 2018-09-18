package facades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.QuestionTag;
import utils.Utils;

public class FacadeQuestionTag {

	public static boolean create(String label, int questionId) throws SQLException {

		final String sqlQuery = "INSERT INTO " + QuestionTag.TABLE_NAME
				+ " (entitylabel, entityquestion) VALUES (?, ?)";

		try (Connection connection = Utils.datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlQuery);) {

			statement.setString(1, label);
			statement.setInt(2, questionId);

			return statement.execute();

		}

	}

	public static List<QuestionTag> findAllByQuestion(int questionId) throws SQLException {

		List<QuestionTag> questionTags = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append("SELECT id, entitylabel");
		sqlQuery.append(" FROM " + QuestionTag.TABLE_NAME + " WHERE entityquestion = ?");
		sqlQuery.append(" LIMIT " + Utils.DATABASE_DEFAULT_LIMIT);

		try (Connection connection = Utils.datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlQuery.toString());) {

			statement.setInt(1, questionId);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {

					QuestionTag qt = new QuestionTag();

					qt.setId(rs.getInt("id"));
					qt.setLabel(rs.getString("entitylabel"));
					qt.setQuestion(questionId);

					questionTags.add(qt);
				}
			}

		}

		return questionTags;
	}

}
