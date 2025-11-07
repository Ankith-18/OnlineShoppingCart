package service;

import dao.*;
import model.*;
import java.util.*;
import java.util.logging.*;

public class ShoppingService {
    private static final Logger logger = Logger.getLogger(ShoppingService.class.getName());

    private ProductDAO productDAO = new ProductDAO();
    private UserDAO userDAO = new UserDAO();
    private OrderDAO orderDAO = new OrderDAO();

    private List<CartItem> cart = new ArrayList<>();

    // Authenticate user
    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    // Register new user
    public boolean register(User user) {
        return userDAO.registerUser(user);
    }

    // List all products
    public List<Product> viewAllProducts() {
        return productDAO.getAllProducts();
    }

    // Add product to cart
    public void addToCart(Product product, int quantity) {
        cart.add(new CartItem(product, quantity));
        logger.info("Added to cart: " + product.getName() + " (x" + quantity + ")");
    }

    // View cart
    public void viewCart() {
        System.out.println("=== Your Cart ===");
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            for (CartItem item : cart) {
                System.out.println(item);
            }
        }
    }

    // Checkout and place order
    public void checkout(User user) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }

        double total = 0;
        for (CartItem item : cart) {
            total += item.getTotalPrice();
        }

        Order order = new Order(0, user, new ArrayList<>(cart), total);
        boolean success = orderDAO.placeOrder(order);

        if (success) {
            System.out.println(" Order placed successfully! Total Amount: Rs." + total);
            logger.info("Order placed for user: " + user.getUsername());
            cart.clear();
        } else {
            System.out.println(" Order placement failed.");
        }
    }
}

