package facades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Answer;
import utils.Utils;

public class FacadeAnswer {

	public static boolean create(String label, int questionId) throws SQLException {

		final String sqlQuery = "INSERT INTO " + Answer.TABLE_NAME + " (entitylabel, entityquestion) VALUES(?, ?)";

		try (Connection connection = Utils.datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlQuery.toString());) {

			statement.setString(1, label);
			statement.setInt(2, questionId);

			return statement.execute();

		}

	}

}
