package QUIZ;

import DatabaseConfig.CompetitorList;
import DatabaseConfig.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Manager {
    // Shared resources
    private static final Scanner scanner = new Scanner(System.in);
    private static final CompetitorList competitorList = new CompetitorList();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("   COMPETITION MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Login as Competitor (User)");
            System.out.println("2. Login/Signup as Admin (Manager)");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleUserFlow();
                    break;
                case "2":
                    handleAdminFlow();
                    break;
                case "3":
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ================= USER (COMPETITOR) SECTION =================

    private static void handleUserFlow() {
        System.out.println("\n--- COMPETITOR LOGIN ---");
        System.out.print("Enter Competitor ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();

        // Use the existing checkLogin logic from CompetitorList
        String status = competitorList.checkLogin(id, pass);

        if (status.equals("SUCCESS")) {
            RONCompetitor c = competitorList.findCompetitor(id);
            System.out.println("\nLogin Successful!");
            System.out.println("---------------------------------");
            System.out.println("Welcome, " + c.getCompetitorName().getFullName());
            System.out.println(c.getShortDetails());
            System.out.println("Scores: " + java.util.Arrays.toString(c.getScores()));
            System.out.println("---------------------------------");
            System.out.println("Press Enter to return to main menu...");
            scanner.nextLine();
        } else {
            System.out.println("Login Failed: " + status);
        }
    }

    // ================= ADMIN (MANAGER) SECTION =================

    private static void handleAdminFlow() {
        while (true) {
            System.out.println("\n--- ADMIN PORTAL ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Admin Signup");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                if (adminLogin()) {
                    adminDashboard(); // Go to dashboard on success
                    break;
                }
            } else if (choice.equals("2")) {
                adminSignup();
            } else if (choice.equals("3")) {
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private static boolean adminLogin() {
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        String sql = "SELECT * FROM Admins WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Admin Login Successful!");
                return true;
            } else {
                System.out.println("Invalid Credentials.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
            return false;
        }
    }

    private static void adminSignup() {
        System.out.println("\n--- ADMIN REGISTRATION ---");

        System.out.print("Enter New Admin ID (e.g., ADM002): ");
        String id = scanner.nextLine();

        System.out.print("Enter Username: ");
        String user = scanner.nextLine();

        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();

        System.out.print("Confirm Password: ");
        String confirmPass = scanner.nextLine();

        // Validation
        if (id.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            System.out.println("Error: All fields are required.");
            return;
        }
        if (!pass.equals(confirmPass)) {
            System.out.println("Error: Passwords do not match!");
            return;
        }

        String sql = "INSERT INTO Admins (admin_id, username, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, user);
            pstmt.setString(3, pass);
            pstmt.executeUpdate();
            System.out.println("Admin Account Created Successfully! Please Login.");

        } catch (SQLException e) {
            System.out.println("Error creating admin (ID or Username might be taken): " + e.getMessage());
        }
    }

    private static void adminDashboard() {
        while (true) {
            System.out.println("\n=== MANAGER DASHBOARD ===");
            System.out.println("1. View All Players & Scores");
            System.out.println("2. Add New Question to Database");
            System.out.println("3. Search Specific Player");
            System.out.println("4. Logout");
            System.out.print("Action: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllPlayers();
                    break;
                case "2":
                    addNewQuestion();
                    break;
                case "3":
                    searchPlayer();
                    break;
                case "4":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // --- Admin Feature 1: View Players ---
    private static void viewAllPlayers() {
        competitorList.loadFromDatabase(); // Refresh data
        System.out.println("\n--- ALL COMPETITOR REPORT ---");
        System.out.printf("%-5s %-20s %-15s %-10s%n", "ID", "Name", "Level", "Score");
        System.out.println("----------------------------------------------------");
        for (RONCompetitor c : competitorList.getAllCompetitors()) {
            System.out.printf("%-5s %-20s %-15s %-10.1f%n",
                    c.getCompetitorID(),
                    c.getCompetitorName().getFullName(),
                    c.getCompetitorLevel(),
                    c.getOverallScore());
        }
    }

    // --- Admin Feature 2: Add Question ---
    private static void addNewQuestion() {
        System.out.println("\n--- ADD NEW QUESTION ---");
        try {
            System.out.print("Enter Question Text: ");
            String text = scanner.nextLine();

            System.out.print("Option A: "); String opA = scanner.nextLine();
            System.out.print("Option B: "); String opB = scanner.nextLine();
            System.out.print("Option C: "); String opC = scanner.nextLine();
            System.out.print("Option D: "); String opD = scanner.nextLine();

            System.out.print("Correct Answer (A/B/C/D): ");
            String ans = scanner.nextLine().toUpperCase();

            System.out.print("Difficulty (Beginner/Intermediate/Advanced): ");
            String diff = scanner.nextLine();

            String sql = "INSERT INTO Questions (question_text, option_a, option_b, option_c, option_d, correct_ans, difficulty) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, text);
                pstmt.setString(2, opA);
                pstmt.setString(3, opB);
                pstmt.setString(4, opC);
                pstmt.setString(5, opD);
                pstmt.setString(6, ans);
                pstmt.setString(7, diff);

                int rows = pstmt.executeUpdate();
                if(rows > 0) System.out.println("Question added to database!");
            }
        } catch (Exception e) {
            System.out.println("Error adding question: " + e.getMessage());
        }
    }

    // --- Admin Feature 3: Search Player ---
    private static void searchPlayer() {
        System.out.print("Enter Competitor ID to search: ");
        String id = scanner.nextLine();
        RONCompetitor c = competitorList.findCompetitor(id);
        if (c != null) {
            System.out.println(c.getFullDetails());
        } else {
            System.out.println("Competitor not found.");
        }
    }
}