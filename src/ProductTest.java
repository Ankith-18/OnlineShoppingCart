import dao.ProductDAO;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class ProductTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductDAO dao = new ProductDAO();

        while (true) {
            System.out.println("\n=== Product Management Menu ===");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View All Products");
            System.out.println("5. Search Product");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    // --- Add new product ---
                    System.out.print("Enter product name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter price: ");
                    double price = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter stock: ");
                    int stock = sc.nextInt();

                    Product newProduct = new Product(0, name, price, category, stock);
                    if (dao.addProduct(newProduct))
                        System.out.println(" Product added successfully!");
                    else
                        System.out.println(" Failed to add product.");
                    break;

                case 2:
                    // --- Update product ---
                    System.out.print("Enter product ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter new price: ");
                    double newPrice = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter new category: ");
                    String newCategory = sc.nextLine();
                    System.out.print("Enter new stock: ");
                    int newStock = sc.nextInt();

                    Product updated = new Product(updateId, newName, newPrice, newCategory, newStock);
                    if (dao.updateProduct(updated))
                        System.out.println(" Product updated successfully!");
                    else
                        System.out.println(" Product not found or update failed.");
                    break;

                case 3:
                    // --- Delete product ---
                    System.out.print("Enter product ID to delete: ");
                    int deleteId = sc.nextInt();
                    if (dao.deleteProduct(deleteId))
                        System.out.println("🗑 Product deleted successfully!");
                    else
                        System.out.println(" Failed to delete product.");
                    break;

                case 4:
                    // --- View all products ---
                    List<Product> all = dao.getAllProducts();
                    if (all.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        System.out.println("\n=== Product List ===");
                        for (Product p : all) {
                            System.out.printf("ID: %d | Name: %s | Price: %.2f | Category: %s | Stock: %d%n",
                                    p.getId(), p.getName(), p.getPrice(), p.getCategory(), p.getStock());
                        }
                    }
                    break;

                case 5:
                    // --- Search products ---
                    System.out.print("Enter keyword: ");
                    String keyword = sc.nextLine();
                    List<Product> results = dao.searchProducts(keyword);
                    if (results.isEmpty()) {
                        System.out.println("No products matched your search.");
                    } else {
                        System.out.println("\n=== Search Results ===");
                        for (Product p : results) {
                            System.out.printf("ID: %d | Name: %s | Price: %.2f | Category: %s | Stock: %d%n",
                                    p.getId(), p.getName(), p.getPrice(), p.getCategory(), p.getStock());
                        }
                    }
                    break;

                case 6:
                    System.out.println("Exiting Product Management. ");
                    return;

                default:
                    System.out.println("⚠ Invalid option. Please try again.");
            }
        }
    }
}
