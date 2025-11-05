import java.sql.*;
import java.util.*;

public class PartB_ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/college";
    static final String USER = "root";
    static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            con.setAutoCommit(false);
            boolean exit = false;

            while (!exit) {
                System.out.println("\n--- Product Management ---");
                System.out.println("1. Insert Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> insertProduct(con, sc);
                    case 2 -> viewProducts(con);
                    case 3 -> updateProduct(con, sc);
                    case 4 -> deleteProduct(con, sc);
                    case 5 -> exit = true;
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void insertProduct(Connection con, Scanner sc) {
        try {
            String query = "INSERT INTO Product(ProductName, Price, Quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.print("Enter Product Name: ");
            sc.nextLine();
            ps.setString(1, sc.nextLine());
            System.out.print("Enter Price: ");
            ps.setDouble(2, sc.nextDouble());
            System.out.print("Enter Quantity: ");
            ps.setInt(3, sc.nextInt());
            ps.executeUpdate();
            con.commit();
            System.out.println("Product inserted successfully!");
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        }
    }

    static void viewProducts(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
        System.out.println("ID\tName\tPrice\tQuantity");
        System.out.println("--------------------------------");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" +
                               rs.getDouble(3) + "\t" + rs.getInt(4));
        }
    }

    static void updateProduct(Connection con, Scanner sc) {
        try {
            String query = "UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();
            System.out.print("Enter New Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter New Quantity: ");
            int qty = sc.nextInt();
            ps.setDouble(1, price);
            ps.setInt(2, qty);
            ps.setInt(3, id);
            ps.executeUpdate();
            con.commit();
            System.out.println("Product updated successfully!");
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        }
    }

    static void deleteProduct(Connection con, Scanner sc) {
        try {
            String query = "DELETE FROM Product WHERE ProductID=?";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.print("Enter Product ID to delete: ");
            ps.setInt(1, sc.nextInt());
            ps.executeUpdate();
            con.commit();
            System.out.println("Product deleted successfully!");
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        }
    }
}
