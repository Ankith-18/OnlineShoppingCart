package db;

import java.sql.*;
import java.util.Properties;
import java.util.logging.*;
import java.io.FileInputStream;
import java.io.IOException;

public class DBConnection {
    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, " MySQL Driver not found", e);
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            conn = DriverManager.getConnection(url, username, password);
            logger.info(" Database connected successfully.");
        } catch (IOException | SQLException e) {
            logger.log(Level.SEVERE, " Database connection failed", e);
        }
        return conn;
    }
}
