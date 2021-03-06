package listeners;

import helpers.Activity;
import helpers.FriendManager;
import helpers.Quiz;
import helpers.Sign;
import helpers.User;
import helpers.messaging.Message;
import helpers.messaging.MessageManager;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mysql.jdbc.Statement;

import db.DBConnection;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	DBConnection db;
    
    public ContextListener() {
    	db = new DBConnection();
    }

    public void contextInitialized(ServletContextEvent arg0) {
        arg0.getServletContext().setAttribute("database", db);
        User.setDB(db);
        Message.setDB(db);
        Quiz.setDB(db);
        Sign.setDB(db);
        FriendManager.setDB(db);
        MessageManager.setDB(db);
        Activity.setDB(db);
    }

    public void contextDestroyed(ServletContextEvent arg0) {
    	db.close();
    }
	
}
