package com.shivam.lms.dao;

import com.shivam.lms.data.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserDAO {

    // View Enrolled Courses for a user by username
    public static void viewEnrolledCourses(String username) {
        String sql = """
                SELECT c.id, c.title, c.description
                FROM enrollments e
                JOIN courses c ON e.course_id = c.id
                WHERE e.username = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nEnrolled Courses for " + username + ":");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(" - [" + rs.getInt("id") + "] " + rs.getString("title")
                        + " → " + rs.getString("description"));
            }

            if (!found) System.out.println("No enrolled courses found.");

        } catch (Exception e) {
            System.out.println("Error fetching enrolled courses: " + e.getMessage());
        }
    }

    // Issue Book to User by username
    public static void issueBookToUser(Scanner sc) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter book ID to issue: ");
        int bookId = sc.nextInt();
        sc.nextLine(); // consume newline

        String findUserIdSQL = "SELECT id FROM users WHERE username = ?";
        String insertIssueSQL = "INSERT INTO issued_books (user_id, book_id, issue_date) VALUES (?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement findUserStmt = conn.prepareStatement(findUserIdSQL)) {

            findUserStmt.setString(1, username);
            ResultSet rs = findUserStmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");

                try (PreparedStatement issueStmt = conn.prepareStatement(insertIssueSQL)) {
                    issueStmt.setInt(1, userId);
                    issueStmt.setInt(2, bookId);

                    int rows = issueStmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("pdf of document issued successfully to " + username);
                    } else {
                        System.out.println("Failed to issue pdf of document .");
                    }
                }

            } else {
                System.out.println("User not found.");
            }

        } catch (Exception e) {
            System.out.println("Error while issuing document: " + e.getMessage());
        }
    }

    // View Issued Books for a user by username
    public static void viewIssuedBooks(String username) {
        String sql = """
                SELECT b.id, b.title, b.author, ib.issue_date
                FROM users u
                JOIN issued_books ib ON u.id = ib.user_id
                JOIN books b ON ib.book_id = b.id
                WHERE u.username = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nIssued Books for " + username + ":");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println(" - [" + rs.getInt("id") + "] " + rs.getString("title") +
                        " by " + rs.getString("author") +
                        " | Issued: " + rs.getDate("issue_date"));
            }

            if (!found) System.out.println("No issued document found.");

        } catch (Exception e) {
            System.out.println("Error fetching issued documents: " + e.getMessage());
        }
    }
}
