
package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {

	/**
	 * Responses
	 */

	public static String RESPONSE_MESSAGE_DB_UNKNOW_ERROR = "Unknow database error.";
	public static String RESPONSE_MESSAGE_ERROR_UNKNOWN = "An unknowed error happened.";
	public static String RESPONSE_MESSAGE_UNAUTHORIZED = "Bad credentials, please contact the admin.";
	public static String RESPONSE_INTERNAL_SERVER_ERROR = "An error happened, please contact the admin.";

	public static Map<String, String> userActionResponse(byte success, String message,
			Map<String, String> optionalData) {

		Map<String, String> response = new HashMap<>();

		response.put("success", success == 0 ? "true" : "false");
		response.put("message", message);

		if (optionalData != null) {
			response.putAll(optionalData);
		}

		return response;

	}

	/**
	 * Security
	 */
	public static final boolean DEBUG = false;
	public static final boolean SECURITY_ENABLED = !DEBUG;

	public static final byte PASSWORD_HASH_WORKLOAD = 10;

	public static boolean passwordCheck(String password, String passwordHashed) {

		if (null == passwordHashed || !passwordHashed.startsWith("$2a$")) {
			return false;
		}

		return BCrypt.checkpw(password, passwordHashed);
	}

	/**
	 * Processing
	 */
	public static final byte SEARCH_lENGTH_MIN = 3;

	public static final String REGEX_STRING_ONLY_CHARS = "[^A-Za-z0-9]";

	public static boolean isStringContainingSpecials(String s) {
		return Pattern.compile(REGEX_STRING_ONLY_CHARS, Pattern.CASE_INSENSITIVE).matcher(s).find();
	}

	/**
	 * Global
	 */
	public static final short USER_GROUP_ID_ADMIN = 1;

	/**
	 * Database
	 */
	public static final byte DATABASE_DEFAULT_LIMIT = 10;

	/**
	 * The global datasource for our project since there is not DI available to
	 * use @Resource
	 */
	public static DataSource datasource;

	/**
	 * 
	 * @param s
	 */
	public static void debug(String s) {
		if (DEBUG) {
			System.out.println(s);
		}
	}

}