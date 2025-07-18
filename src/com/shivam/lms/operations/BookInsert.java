package com.shivam.lms.operations;

import com.shivam.lms.data.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class BookInsert {
    public static void addBook(Scanner sc) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("Enter book title: ");
            String title = sc.nextLine();
            System.out.print("Enter author name: ");
            String author = sc.nextLine();
            System.out.print("Enter quantity: ");
            int quantity = sc.nextInt();
            sc.nextLine(); // consume newline

            String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, quantity);

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "✅ Book added successfully!" : "❌ Book insert failed.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
