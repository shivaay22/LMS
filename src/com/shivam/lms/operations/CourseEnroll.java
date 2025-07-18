package com.shivam.lms.operations;

import com.shivam.lms.data.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CourseEnroll {
    public static void enroll(Scanner sc) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Step 1: Get username
            System.out.print("Enter Username: ");
            String username = sc.nextLine().trim();

            // Step 2: Get user_id from username
            String userQuery = "SELECT id FROM users WHERE username = ?";
            PreparedStatement userStmt = conn.prepareStatement(userQuery);
            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (!userRs.next()) {
                System.out.println("❌ User not found!");
                return;
            }
            int userId = userRs.getInt("id");

            // Step 3: Get course ID
            System.out.print("Enter Course ID: ");
            int courseId = Integer.parseInt(sc.nextLine().trim());

            // Step 4: Check if course exists
            String courseQuery = "SELECT id FROM courses WHERE id = ?";
            PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
            courseStmt.setInt(1, courseId);
            ResultSet courseRs = courseStmt.executeQuery();

            if (!courseRs.next()) {
                System.out.println("❌ Course not found!");
                return;
            }

            // Step 5: Enroll user into course
            String sql = "INSERT INTO enrollments (username, course_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);  // Use the username directly
            stmt.setInt(2, courseId);

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "✅ User enrolled successfully!" : "❌ Enrollment failed.");


        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
