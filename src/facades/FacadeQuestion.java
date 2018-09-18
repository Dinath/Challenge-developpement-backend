package facades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Answer;
import models.Question;
import models.QuestionTag;
import utils.Utils;

public class FacadeQuestion {

	public static List<Question> search(String search) throws SQLException {

		List<Question> questions = new ArrayList<>();

		StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append(
				"SELECT q.id as 'q.id', q.entitylabel as 'q.entitylabel', a.id as 'a.id', a.entitylabel as 'a.entitylabel', a.entityquestion as 'a.entityquestion'");
		sqlQuery.append(" FROM " + Question.TABLE_NAME + " AS q");
		sqlQuery.append(" INNER JOIN " + Answer.TABLE_NAME + " AS a ON a.entityquestion = q.id");
		sqlQuery.append(" INNER JOIN " + QuestionTag.TABLE_NAME + " AS qt ON q.id = qt.entityquestion");
		sqlQuery.append(" WHERE");
		sqlQuery.append(" a.entitylabel LIKE ? OR");
		sqlQuery.append(" qt.entitylabel LIKE ? OR");
		sqlQuery.append(" q.entitylabel LIKE ?");
		sqlQuery.append(" LIMIT " + Utils.DATABASE_DEFAULT_LIMIT);

		try (Connection connection = Utils.datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlQuery.toString());) {

			// ugly
			statement.setString(1, search);
			statement.setString(2, search);
			statement.setString(3, search);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					questions.add(__constructFromDB(rs));
				}
			}

		}

		return questions;
	}

	/**
	 * 
	 * @param label
	 * @param questionId
	 * @return
	 * @throws SQLException
	 */
	public static int create(String label) throws SQLException {

		final String sqlQuery = "INSERT INTO " + Question.TABLE_NAME + " (entitylabel) VALUES(?)";

		try (Connection connection = Utils.datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlQuery.toString(),
						Statement.RETURN_GENERATED_KEYS);) {

			statement.setString(1, label);

			if (statement.executeUpdate() > 0) {
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						return (int) generatedKeys.getLong(1);
					}
				}
			}

		}
		return 0;

	}

	public static Question find(int id) throws SQLException {

		StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append(
				"SELECT q.id as 'q.id', q.entitylabel as 'q.entitylabel', a.id as 'a.id', a.entitylabel as 'a.entitylabel', a.entityquestion as 'a.entityquestion'");
		sqlQuery.append(" FROM " + Question.TABLE_NAME + " AS q INNER JOIN " + Answer.TABLE_NAME + " AS a ON ");
		sqlQuery.append(" a.entityquestion = q.id WHERE q.id = ? LIMIT 1");

		try (Connection connection = Utils.datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlQuery.toString());) {

			statement.setInt(1, id);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {

					return __constructFromDB(rs);

				}
			}

		}

		return null;
	}

	public static List<Question> findAll(int pagination) throws SQLException {

		List<Question> questions = new ArrayList<>();
		StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append(
				"SELECT q.id as 'q.id', q.entitylabel as 'q.entitylabel', a.id as 'a.id', a.entitylabel as 'a.entitylabel', a.entityquestion as 'a.entityquestion'");
		sqlQuery.append(" FROM " + Question.TABLE_NAME + " AS q INNER JOIN " + Answer.TABLE_NAME + " AS a ON ");
		sqlQuery.append(" a.entityquestion = q.id LIMIT " + pagination + "," + Utils.DATABASE_DEFAULT_LIMIT);

		try (Connection connection = Utils.datasource.getConnection();
				Statement statement = connection.createStatement();) {

			try (ResultSet rs = statement.executeQuery(sqlQuery.toString())) {
				while (rs.next()) {

					questions.add(__constructFromDB(rs));
				}
			}

		}

		return questions;
	}

	/**
	 * Custruct the question object from a resultset
	 * 
	 * @param rs resultset containing fileds of question & answer
	 * @return a models.Question object
	 * @throws SQLException
	 */
	private static Question __constructFromDB(ResultSet rs) throws SQLException {

		// answer
		Answer a = new Answer();
		a.setId(rs.getInt("a.id"));
		a.setLabel(rs.getString("a.entitylabel"));
		a.setQuestion(rs.getInt("a.entityquestion"));

		// question
		Question q = new Question();
		q.setId(rs.getInt("q.id"));
		q.setLabel(rs.getString("q.entitylabel"));

		// filling joins
		q.setAnswer(a);
		q.setTags(FacadeQuestionTag.findAllByQuestion(q.getId()));

		return q;
	}

}
