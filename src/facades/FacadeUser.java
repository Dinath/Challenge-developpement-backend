package facades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import utils.Utils;

public class FacadeUser {

	public static User findByEmailAndGroup(String email, short groupId) throws SQLException {

		try (Connection connection = Utils.datasource.getConnection();

				PreparedStatement statement = connection.prepareStatement(
						"SELECT * FROM " + User.TABLE_NAME + " WHERE entityemail = ? AND entitygroupid = ? LIMIT 1");) {

			statement.setString(1, email);
			statement.setShort(2, groupId);

			try (ResultSet rs = statement.executeQuery()) {

				if (rs.next()) {

					User u = new User();
					u.setId(rs.getInt("id"));
					u.setEntityEmail(rs.getString("entityemail"));
					u.setEntityGroup(rs.getShort("entitygroupid"));
					u.setEntityPassword(rs.getString("entitypassword"));

					return u;
				}
			}

		}
		return null;
	}

//	public static List<User> findAll() throws SQLException {
//
//		List<User> users = new ArrayList<>();
//
//		try (Connection connection = Utils.datasource.getConnection();
//				Statement statement = connection.createStatement();) {
//
//			try (ResultSet rs = statement.executeQuery(
//					"SELECT * FROM " + User.TABLE_NAME + " LIMIT " + Utils.DATABASE_DEFAULT_LIMIT + ";")) {
//				while (rs.next()) {
//
//					User u = new User();
//					u.setId(rs.getInt("id"));
//					u.setEntityEmail(rs.getString("entityemail"));
//					u.setEntityGroup(rs.getShort("entitygroup"));
//					users.add(u);
//				}
//			}
//
//		}
//
//		return users;
//	}

}
