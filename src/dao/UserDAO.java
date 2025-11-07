package dao;

import db.DBConnection;
import model.User;
import java.sql.*;
import java.util.logging.*;

public class UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    //  Register a new user
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, password, role, email, address, phone) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhone());

            int rows = ps.executeUpdate();

            // Get the auto-generated user ID
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }

            if (rows > 0) {
                logger.info(" User registered: " + user.getUsername() + " (ID: " + user.getId() + ")");
                return true;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Error registering user", e);
        }
        return false;
    }

    //  Validate login credentials
    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setEmail(rs.getString("email"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getString("phone"));

                    logger.info(" User login success: " + username);
                    return user;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Login failed", e);
        }

        logger.warning("⚠ Invalid credentials for: " + username);
        return null;
    }
}
