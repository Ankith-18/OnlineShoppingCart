package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;        // "admin" or "customer"
    private String email;
    private String address;
    private String phone;

    public User() {}

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(int id, String username, String password, String role, String email, String address, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User [ID=" + id + ", Username=" + username + ", Role=" + role +
                ", Email=" + email + ", Address=" + address + ", Phone=" + phone + "]";
    }
}
