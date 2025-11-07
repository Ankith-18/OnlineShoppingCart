package model;

import java.util.List;
import java.util.Date;

public class Order {
    private int id;
    private User user;
    private List<CartItem> items;
    private double totalAmount;
    private Date orderDate;
    private String status; // e.g., "Pending", "Confirmed", "Delivered"

    public Order() {}

    public Order(int id, User user, List<CartItem> items, double totalAmount) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderDate = new Date();
        this.status = "Pending";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<CartItem> getItems() {
        return items;
    }
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order [ID=" + id + ", User=" + user.getUsername() + ", Total=" + totalAmount +
                ", Date=" + orderDate + ", Status=" + status + "]";
    }
}
