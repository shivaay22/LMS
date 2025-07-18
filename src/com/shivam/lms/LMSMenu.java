package com.shivam.lms;

import com.shivam.lms.dao.UserDAO;
import com.shivam.lms.operations.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LMSMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exitApp = false;
        boolean loggedIn = false;
        String loggedInUser = null;

        while (!exitApp) {
            // Show login menu first
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int mainChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (mainChoice) {
                case 1 -> {
                    // Login
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    try (Connection conn = com.shivam.lms.data.DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(
                                 "SELECT * FROM users WHERE username = ? AND password = ?")) {

                        stmt.setString(1, username);
                        stmt.setString(2, password);

                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            loggedIn = true;
                            loggedInUser = username;
                            System.out.println("Logged in as: " + loggedInUser);

                            // Logged-in menu
                            int choice;
                            do {
                                System.out.println("\n=== LMS MENU ===");
                                System.out.println("1. Add pdf Documentation");
                                System.out.println("2. View All pdf Documentation");
                                System.out.println("3. Issue Documentation to User");
                                System.out.println("4. Add User (Student/Teacher)");
                                System.out.println("5. Enroll in Course");
                                System.out.println("6. View Courses");
                                System.out.println("7. View My Enrolled Courses");
                                System.out.println("8. View My Issued pdf Documentation");
                                System.out.println("9. Logout");
                                System.out.print("Enter your choice: ");
                                choice = sc.nextInt();
                                sc.nextLine(); // consume newline

                                switch (choice) {
                                    case 1 -> BookInsert.addBook(sc);
                                    case 2 -> BookDisplay.showBooks();
                                    case 3 -> UserDAO.issueBookToUser(sc);
                                    case 4 -> UserInsert.addUser(sc);
                                    case 5 -> CourseEnroll.enroll(sc);
                                    case 6 -> CourseDisplay.showCourses();
                                    case 7 -> UserDAO.viewEnrolledCourses(loggedInUser);
                                    case 8 -> UserDAO.viewIssuedBooks(loggedInUser);
                                    case 9 -> {
                                        System.out.println("Logged out.");
                                        loggedIn = false;
                                        loggedInUser = null;
                                    }
                                    default -> System.out.println("Invalid choice. Try again.");
                                }

                            } while (loggedIn); // loop until logout

                        } else {
                            System.out.println("Invalid credentials. Try again.");
                        }

                    } catch (Exception e) {
                        System.out.println("Error during login: " + e.getMessage());
                    }
                }

                case 2 -> {
                    System.out.println("Exiting LMS. Goodbye!");
                    exitApp = true;
                }

                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }
}
