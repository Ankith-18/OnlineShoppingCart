package dao;

import db.DBConnection;
import model.Product;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class ProductDAO {
    private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());

    // Add new product
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, price, category, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getCategory());
            ps.setInt(4, product.getStock());
            ps.executeUpdate();
            logger.info(" Product added: " + product.getName());
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding product", e);
            return false;
        }
    }

    // Update product details
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name=?, price=?, category=?, stock=? WHERE id=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getCategory());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                logger.info(" Product updated: " + product.getName());
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating product", e);
        }
        return false;
    }

    // Delete product
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE id=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, productId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                logger.info("🗑 Product deleted: ID=" + productId);
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting product", e);
        }
        return false;
    }

    // Fetch all products
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement stmt = DBConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getInt("stock")
                ));
            }
            logger.info(" Fetched " + list.size() + " products.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching products", e);
        }
        return list;
    }

    // Search product by name or category
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("category"),
                            rs.getInt("stock")
                    ));
                }
            }
            logger.info(" Search completed for keyword: " + keyword);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error searching products", e);
        }
        return list;
    }
}
