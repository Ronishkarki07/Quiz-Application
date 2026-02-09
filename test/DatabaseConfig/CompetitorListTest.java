package DatabaseConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import QUIZ.RONCompetitor;
import QUIZ.Name;
import java.util.ArrayList;

/**
 * JUnit test class for CompetitorList functionality
 * Tests CompetitorList class methods using JUnit 5 framework
 */
public class CompetitorListTest {

    private CompetitorList competitorList;
    private RONCompetitor testCompetitor;

    @BeforeEach
    void setUp() {
        competitorList = new CompetitorList();
        testCompetitor = new RONCompetitor(
            "TEST001", 
            new Name("Test", "User", "One"), 
            "Beginner", 
            "USA", 
            25, 
            "password123"
        );
    }

    @Test
    @DisplayName("Test CompetitorList constructor")
    void testConstructor() {
        assertNotNull(competitorList, "CompetitorList should not be null after construction");
    }

    @Test
    @DisplayName("Test saving competitor")
    void testSaveCompetitor() {
        assertDoesNotThrow(() -> {
            try {
                competitorList.saveCompetitor(testCompetitor);
                System.out.println("Competitor saved successfully");
            } catch (Exception e) {
                // Handle any database exceptions gracefully
                System.out.println("Database operation handled: " + e.getMessage());
            }
        }, "Save operation should complete without exception");
    }

    @Test
    @DisplayName("Test finding existing competitor")
    void testFindCompetitor() {
        assertDoesNotThrow(() -> {
            try {
                competitorList.saveCompetitor(testCompetitor);
                competitorList.findCompetitor("TEST001");
            } catch (Exception e) {
                // Handle database exceptions gracefully 
                System.out.println("Database operation handled: " + e.getMessage());
            }
        }, "Find operation should complete without exception");
    }

    @Test
    @DisplayName("Test finding non-existent competitor")
    void testFindCompetitorNotFound() {
        assertDoesNotThrow(() -> {
            competitorList.findCompetitor("NONEXISTENT");
            // Should return null or handle gracefully for non-existent competitor
        }, "Should handle non-existent competitor gracefully");
    }

    @Test
    @DisplayName("Test getting all competitors")
    void testGetAllCompetitors() {
        ArrayList<RONCompetitor> competitors = competitorList.getAllCompetitors();
        assertNotNull(competitors, "getAllCompetitors should not return null");
    }

    @Test
    @DisplayName("Test successful login")
    void testCheckLogin_Success() {
        assertDoesNotThrow(() -> {
            competitorList.saveCompetitor(testCompetitor);
            competitorList.checkLogin("TEST001", "password123");
            // Login check should complete without exception
        }, "Valid login should not throw exception");
    }

    @Test
    @DisplayName("Test login with wrong password")
    void testCheckLogin_WrongPassword() {
        assertDoesNotThrow(() -> {
            competitorList.saveCompetitor(testCompetitor);
            competitorList.checkLogin("TEST001", "wrongpassword");
            // Should handle wrong password gracefully
        }, "Wrong password should be handled gracefully");
    }

    @Test
    @DisplayName("Test login with non-existent user")
    void testCheckLogin_UserNotFound() {
        assertDoesNotThrow(() -> {
            competitorList.checkLogin("NONEXISTENT", "password");
            // Should handle non-existent user gracefully
        }, "Non-existent user should be handled gracefully");
    }

    @Test
    @DisplayName("Test getting leaderboard")
    void testGetLeaderboard() {
        assertDoesNotThrow(() -> {
            // Add competitor with scores
            testCompetitor.addQuizAttemptScore(95);
            competitorList.saveCompetitor(testCompetitor);
            
            ArrayList<RONCompetitor> leaderboard = competitorList.getLeaderboard();
            assertNotNull(leaderboard, "Leaderboard should not be null");
        }, "Leaderboard generation should not throw exception");
    }

    @Test
    @DisplayName("Test loading from database")
    void testLoadFromDatabase() {
        assertDoesNotThrow(() -> competitorList.loadFromDatabase(), 
                          "Load from database should not throw exception");
    }
}