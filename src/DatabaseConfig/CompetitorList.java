package DatabaseConfig;

import QUIZ.Name;
import QUIZ.RONCompetitor;
import java.sql.*;
import java.util.ArrayList;

public class CompetitorList {
    private ArrayList<RONCompetitor> competitors;

    public CompetitorList() {
        competitors = new ArrayList<>();
        loadFromDatabase();
    }

    // UPDATED: Now prints detailed errors if loading fails
    public void loadFromDatabase() {
        competitors.clear();
        String sql = "SELECT * FROM Competitors";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String fName = rs.getString("name_first");
                String lName = rs.getString("name_last");
                String level = rs.getString("level");
                String country = rs.getString("country");
                int age = rs.getInt("age");
                String pass = rs.getString("password");
                if (pass == null) pass = "1234";

                int[] scores = {
                        rs.getInt("score1"), rs.getInt("score2"), rs.getInt("score3"),
                        rs.getInt("score4"), rs.getInt("score5")
                };

                RONCompetitor c = new RONCompetitor(id, new Name(fName, lName), level, country, age, pass);
                c.setScores(scores);
                competitors.add(c);
            }
            System.out.println("Data Loaded. Total Competitors: " + competitors.size());
        } catch (SQLException e) {
            System.err.println("Database Load Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveCompetitor(RONCompetitor c) throws SQLException {
        if (findCompetitor(c.getCompetitorID()) != null) {
            updateCompetitor(c);
        } else {
            insertCompetitor(c);
        }
    }

    private void insertCompetitor(RONCompetitor c) throws SQLException {
        String sql = "INSERT INTO Competitors (id, name_first, name_last, level, country, age, password, score1, score2, score3, score4, score5, overall_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, c.getCompetitorID());
            pstmt.setString(2, c.getCompetitorName().getFirstName());
            pstmt.setString(3, c.getCompetitorName().getLastName());
            pstmt.setString(4, c.getCompetitorLevel());
            pstmt.setString(5, c.getCountry());
            pstmt.setInt(6, c.getAge());
            pstmt.setString(7, c.getPassword());
            int[] s = c.getScores();
            for(int i=0; i<5; i++) pstmt.setInt(8+i, s[i]);
            pstmt.setDouble(13, c.getOverallScore());
            pstmt.executeUpdate();

            if(findCompetitor(c.getCompetitorID()) == null) competitors.add(c);
        }
    }

    private void updateCompetitor(RONCompetitor c) throws SQLException {
        String sql = "UPDATE Competitors SET level=?, score1=?, score2=?, score3=?, score4=?, score5=?, overall_score=?, password=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, c.getCompetitorLevel());
            int[] s = c.getScores();
            for(int i=0; i<5; i++) pstmt.setInt(2+i, s[i]);
            pstmt.setDouble(7, c.getOverallScore());
            pstmt.setString(8, c.getPassword());
            pstmt.setString(9, c.getCompetitorID());
            pstmt.executeUpdate();
        }
    }

    public RONCompetitor findCompetitor(String id) {
        for (RONCompetitor c : competitors) {
            if (c.getCompetitorID().equals(id)) return c;
        }
        return null;
    }

    // NEW: Detailed Login Check
    public String checkLogin(String id, String password) {
        // 1. Force refresh from DB to ensure we have latest users
        loadFromDatabase();

        // 2. Check if DB is empty (Connection issue?)
        if(competitors.isEmpty()) {
            return "DB_EMPTY";
        }

        RONCompetitor c = findCompetitor(id);

        // 3. User not found
        if (c == null) {
            return "USER_NOT_FOUND";
        }

        // 4. Password mismatch
        if (!c.getPassword().equals(password)) {
            return "WRONG_PASSWORD";
        }

        // 5. Success
        return "SUCCESS";
    }

    public ArrayList<RONCompetitor> getAllCompetitors() {
        return competitors;
    }

    // ... existing code ...

    // NEW: Returns list sorted by Overall Score (Highest to Lowest)
    public ArrayList<RONCompetitor> getLeaderboard() {
        // Refresh data to ensure latest scores
        loadFromDatabase();

        // Create a copy to avoid messing up the main list order
        ArrayList<RONCompetitor> sortedList = new ArrayList<>(competitors);

        // Sort: Descending Order (Highest score first)
        sortedList.sort((c1, c2) -> Double.compare(c2.getOverallScore(), c1.getOverallScore()));

        return sortedList;
    }
}
