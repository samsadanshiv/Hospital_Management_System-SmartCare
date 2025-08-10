package hospital.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class conn {
    Connection connection;
    Statement statement;

    public conn() {
        try {
            // Load MySQL JDBC Driver - THIS IS THE KEY ADDITION
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system?useSSL=false&serverTimezone=UTC",
                    "root",
                    "PHW#84#jeor"
            );
            statement = connection.createStatement();

            System.out.println("Database connection established successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    // Method to close connections properly
    public void closeConnection() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}