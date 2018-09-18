package app;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import utils.Utils;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {

		Utils.debug("Perform action during application's startup");

		try {

			/**
			 * Register a datasource
			 */
			final Context initContext = new InitialContext();
			final Context envContext = (Context) initContext.lookup("java:/comp/env");
			final DataSource dataSource = (DataSource) envContext.lookup("jdbc/ChallengeDS");

			Utils.datasource = dataSource;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Perform action during application's shutdown
	}
}