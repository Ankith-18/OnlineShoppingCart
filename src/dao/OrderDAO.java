package dao;

import db.DBConnection;
import model.*;
import util.OrderXMLUtil;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class OrderDAO {
    private static final Logger logger = Logger.getLogger(OrderDAO.class.getName());

    // Place new order and store it in DB + XML file
    public boolean placeOrder(Order order) {
        String insertOrder = "INSERT INTO orders (user_id, total) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUser().getId());
            ps.setDouble(2, order.getTotalAmount());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                order.setId(orderId);

                // Save order items and XML backup
                saveOrderItems(orderId, order.getItems());
                OrderXMLUtil.saveOrderAsXML(order);

                logger.info(" Order placed successfully (ID: " + orderId + ")");
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Error placing order", e);
        }
        return false;
    }

    // Save order items in the database
    private void saveOrderItems(int orderId, List<CartItem> items) throws SQLException {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            for (CartItem item : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, item.getProduct().getId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getProduct().getPrice() * item.getQuantity()); // ✅ subtotal = price * quantity
                ps.addBatch();
            }
            ps.executeBatch();
            logger.info("Order items saved for Order ID: " + orderId);
        }
    }

    // Retrieve all orders placed by a specific user
    public List<Order> getOrdersByUser(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("id"),
                            new User(userId, "", "", "customer"),
                            new ArrayList<>(),
                            rs.getDouble("total")
                    );
                    orders.add(order);
                }
            }
            logger.info(" Retrieved " + orders.size() + " orders for User ID: " + userId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Error retrieving orders", e);
        }
        return orders;
    }
}
