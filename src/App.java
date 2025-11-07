import service.ShoppingService;
import model.*;
import util.LogUtil;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        LogUtil.setupLogger();

        ShoppingService service = new ShoppingService();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Welcome to Online Shopping Cart ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose option: ");
        int choice = sc.nextInt();
        sc.nextLine();

        User user = null;

        if (choice == 1) {
            System.out.print("Enter username: ");
            String uname = sc.nextLine();
            System.out.print("Enter password: ");
            String pass = sc.nextLine();
            user = service.login(uname, pass);
            if (user == null) {
                System.out.println(" Invalid login!");
                return;
            }
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String uname = sc.nextLine();
            System.out.print("Enter password: ");
            String pass = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            System.out.print("Enter address: ");
            String addr = sc.nextLine();
            System.out.print("Enter phone: ");
            String phone = sc.nextLine();

            user = new User(0, uname, pass, "customer", email, addr, phone);
            if (service.register(user)) {
                System.out.println(" Registration successful!");
            } else {
                System.out.println(" Registration failed!");
                return;
            }
        }

        System.out.println("\nWelcome, " + user.getUsername() + "!");
        List<Product> products = service.viewAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        while (true) {
            System.out.println("\n=== Product Catalog ===");
            for (Product p : products) {
                System.out.println(p.getId() + ". " + p.getName() + " (Rs." + p.getPrice() + ")");
            }

            System.out.println("Enter product ID to add to cart (0 to checkout): ");
            int pid = sc.nextInt();
            if (pid == 0) {
                break;
            }
            System.out.println("Enter quantity: ");
            int qty = sc.nextInt();

            Product selected = null;
            for (Product p : products) {
                if (p.getId() == pid) {
                    selected = p;
                    break;
                }
            }

            if (selected != null) {
                service.addToCart(selected, qty);
                System.out.println("Added " + selected.getName() + " to cart.");
            } else {
                System.out.println("Invalid product ID!");
            }
        }

        service.viewCart();
        service.checkout(user);
        sc.close();
    }
}
