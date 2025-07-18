package com.shivam.lms.operations;

import com.shivam.lms.data.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookDisplay {
    public static void showBooks() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");

            System.out.printf("%-5s %-30s %-25s %-10s\n", "ID", "Title", "Author", "Quantity");
            System.out.println("-----------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-30s %-25s %-10d\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
