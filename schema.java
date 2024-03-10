import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class schema {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/database_name"; // Replace with your database URL
        String username = "root";
        String password = "root";

        // SQL schema for the 'product' table
        String createProductTableSQL = """
                CREATE TABLE product (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  name VARCHAR(255) NOT NULL,
                  description TEXT,
                  SKU VARCHAR(50) NOT NULL,
                  category_id INT NOT NULL,
                  inventory_id INT NOT NULL,
                  price DECIMAL(10,2) NOT NULL,
                  discount_id INT,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  modified_at TIMESTAMP DEFAULT NULL,
                  deleted_at TIMESTAMP DEFAULT NULL,
                  FOREIGN KEY (category_id) REFERENCES product_category(id),
                  FOREIGN KEY (discount_id) REFERENCES discount(id)
                );
                """;

        // SQL schema for the 'product_category' table
        String createCategoryTableSQL = """
                CREATE TABLE product_category (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  name VARCHAR(255) NOT NULL,
                  description TEXT,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  modified_at TIMESTAMP DEFAULT NULL,
                  deleted_at TIMESTAMP DEFAULT NULL
                );
                """;

        // SQL schema for the 'product_inventory' table
        String createInventoryTableSQL = """
                CREATE TABLE product_inventory (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  product_id INT NOT NULL,
                  quantity INT NOT NULL,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  modified_at TIMESTAMP DEFAULT NULL,
                  deleted_at TIMESTAMP DEFAULT NULL,
                  FOREIGN KEY (product_id) REFERENCES product(id)
                );
                """;

        // SQL schema for the 'discount' table
        String createDiscountTableSQL = """
                CREATE TABLE discount (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  name VARCHAR(255) NOT NULL,
                  description TEXT,
                  discount_percentage DECIMAL(5,2) NOT NULL,
                  active BOOLEAN NOT NULL DEFAULT true,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                  modified_at TIMESTAMP DEFAULT NULL,
                  deleted_at TIMESTAMP DEFAULT NULL
                );
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            // Execute SQL schema creation for the 'product' table
            statement.execute(createProductTableSQL);
            System.out.println("Product table created successfully!");

            // Execute SQL schema creation for the 'product_category' table
            statement.execute(createCategoryTableSQL);
            System.out.println("Product_Category table created successfully!");

            // Execute SQL schema creation for the 'product_inventory' table
            statement.execute(createInventoryTableSQL);
            System.out.println("Product_Inventory table created successfully!");

            // Execute SQL schema creation for the 'discount' table
            statement.execute(createDiscountTableSQL);
            System.out.println("Discount table created successfully!");

        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.err.println("Error creating table: " + e.getMessage());
        }
    }
}
