package com.shivam.lms.operations;

import com.shivam.lms.data.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CourseDisplay {
    public static void showCourses() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses");

            System.out.printf("%-5s %-20s %-50s\n", "ID", "Title", "Description");
            System.out.println("-------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-50s\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
