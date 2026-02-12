package DatabaseConfig;

import QUIZ.Name;
import QUIZ.RONCompetitor;
import java.sql.*;
import java.util.ArrayList;

/**
 * The CompetitorList class manages a collection of competitors in the quiz application.
 * It provides comprehensive database operations including loading, saving, searching,
 * and authentication functionality for competitors. The class maintains an in-memory
 * list of competitors synchronized with the database.
 * 
 * <p>This class serves as the primary data access layer for competitor management,
 * handling all CRUD operations and providing specialized methods for leaderboard
 * generation and user authentication.</p>
 * zz
 * @author Ronish Karki
 * @version 1.0
 * @since 1.0
 */
public class CompetitorList {
    /**
     * List containing all competitors loaded from the database.
     * This list is maintained in memory for efficient access and operations.
     */
    private ArrayList<RONCompetitor> competitors;

    /**
     * Constructs a new CompetitorList instance and automatically loads
     * all competitors from the database.
     * 
     * <p>Upon instantiation, this constructor initializes the internal
     * competitors list and populates it with data from the database by
     * calling {@link #loadFromDatabase()}.</p>
     */

    public CompetitorList() {
        competitors = new ArrayList<>();
        loadFromDatabase();
    }

    /**
     * Loads all competitor data from the database into the internal list.
     * This method clears the existing list and refreshes it with current database data.
     * 
     * <p>The method executes a SELECT * query on the Competitors table and creates
     * RONCompetitor objects for each record found. It handles all competitor attributes
     * including personal information, scores, and authentication credentials.</p>
     * 
     * <p>If the database connection fails or any SQL error occurs, detailed error
     * information is printed to the console, and the competitors list remains empty.</p>
     * 
     * @see RONCompetitor
     * @see DatabaseConnection#getConnection()
     */

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

    /**
     * Saves a competitor to the database, either inserting a new record or updating an existing one.
     * This method automatically determines whether to perform an INSERT or UPDATE operation
     * based on whether the competitor already exists in the database.
     * 
     * <p>The method checks if a competitor with the same ID already exists using
     * {@link #findCompetitor(String)}. If found, it calls {@link #updateCompetitor(RONCompetitor)},
     * otherwise it calls {@link #insertCompetitor(RONCompetitor)}.</p>
     * 
     * @param c the RONCompetitor object to save to the database
     * @throws SQLException if a database access error occurs during the save operation
     * @see #insertCompetitor(RONCompetitor)
     * @see #updateCompetitor(RONCompetitor)
     */

    public void saveCompetitor(RONCompetitor c) throws SQLException {
        if (findCompetitor(c.getCompetitorID()) != null) {
            updateCompetitor(c);
        } else {
            insertCompetitor(c);
        }
    }

    /**
     * Inserts a new competitor record into the database.
     * This private method is called by {@link #saveCompetitor(RONCompetitor)} when
     * a competitor doesn't already exist in the database.
     * 
     * <p>The method inserts all competitor attributes including personal information,
     * quiz scores, and calculated overall score into the Competitors table.</p>
     * 
     * @param c the RONCompetitor object to insert into the database
     * @throws SQLException if a database access error occurs during the insert operation
     */
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

    /**
     * Updates an existing competitor record in the database.
     * This private method is called by {@link #saveCompetitor(RONCompetitor)} when
     * a competitor already exists in the database.
     * 
     * <p>The method updates the competitor's level, individual quiz scores,
     * overall score, and password based on the competitor's ID.</p>
     * 
     * @param c the RONCompetitor object containing updated information
     * @throws SQLException if a database access error occurs during the update operation
     */
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

    /**
     * Searches for a competitor by their unique ID.
     * This method performs a linear search through the in-memory competitors list.
     * 
     * <p>The search is case-sensitive and requires an exact match of the competitor ID.</p>
     * 
     * @param id the unique identifier of the competitor to find
     * @return the RONCompetitor object if found, null otherwise
     * @see RONCompetitor#getCompetitorID()
     */
    public RONCompetitor findCompetitor(String id) {
        for (RONCompetitor c : competitors) {
            if (c.getCompetitorID().equals(id)) return c;
        }
        return null;
    }

    /**
     * Performs detailed login validation for a competitor.
     * This method provides comprehensive authentication with specific error codes
     * to help identify different failure scenarios.
     * 
     * <p>The method first refreshes the competitor data from the database to ensure
     * the most current information is used for authentication. It then performs
     * multiple validation checks in sequence.</p>
     * 
     * @param id the competitor's unique identifier
     * @param password the competitor's password
     * @return a status string indicating the result:
     *         <ul>
     *         <li>"SUCCESS" - login successful</li>
     *         <li>"DB_EMPTY" - database connection issue or no competitors found</li>
     *         <li>"USER_NOT_FOUND" - competitor ID not found</li>
     *         <li>"WRONG_PASSWORD" - incorrect password provided</li>
     *         </ul>
     * @see #loadFromDatabase()
     * @see #findCompetitor(String)
     */

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

    /**
     * Returns the complete list of competitors currently loaded in memory.
     * This method provides direct access to the internal competitors list.
     * 
     * <p>Note: The returned list is a reference to the internal list, so modifications
     * to the returned list will affect the internal state. Use with caution.</p>
     * 
     * @return ArrayList containing all RONCompetitor objects
     * @see RONCompetitor
     */
    public ArrayList<RONCompetitor> getAllCompetitors() {
        return competitors;
    }

    /**
     * Generates a leaderboard by returning competitors sorted by overall score in descending order.
     * This method creates a ranking of all competitors based on their quiz performance.
     * 
     * <p>The method first refreshes the data from the database to ensure the most current
     * scores are used. It then creates a copy of the competitors list and sorts it by
     * overall score using a custom comparator.</p>
     * 
     * <p>The sorting is performed in descending order, meaning the competitor with the
     * highest overall score appears first in the returned list.</p>
     * 
     * @return ArrayList of RONCompetitor objects sorted by overall score (highest to lowest)
     * @see RONCompetitor#getOverallScore()
     * @see #loadFromDatabase()
     */

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
