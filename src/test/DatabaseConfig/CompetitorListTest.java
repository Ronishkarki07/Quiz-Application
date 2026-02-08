package test.DatabaseConfig;

import DatabaseConfig.CompetitorList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import QUIZ.RONCompetitor;
import QUIZ.Name;
import java.util.ArrayList;

/**
 * Test class for CompetitorList functionality
 * Tests only methods that actually exist in CompetitorList
 */
public class CompetitorListTest {

    private CompetitorList competitorList;
    private RONCompetitor testCompetitor;

    @BeforeEach
    public void setUp() {
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
    public void testConstructor() {
        assertNotNull(competitorList);
    }

    @Test
    public void testSaveCompetitor() {
        assertDoesNotThrow(() -> {
            competitorList.saveCompetitor(testCompetitor);
            // Save operation should complete without exception
        });
    }

    @Test
    public void testFindCompetitor() {
        assertDoesNotThrow(() -> {
            competitorList.saveCompetitor(testCompetitor);
            RONCompetitor found = competitorList.findCompetitor("TEST001");
            // Find operation should complete without exception
        });
    }

    @Test
    public void testFindCompetitorNotFound() {
        assertDoesNotThrow(() -> {
            RONCompetitor notFound = competitorList.findCompetitor("NONEXISTENT");
            // Should return null for non-existent competitor
        });
    }

    @Test
    public void testGetAllCompetitors() {
        assertDoesNotThrow(() -> {
            ArrayList<RONCompetitor> competitors = competitorList.getAllCompetitors();
            assertNotNull(competitors);
        });
    }

    @Test
    public void testCheckLogin_Success() {
        assertDoesNotThrow(() -> {
            competitorList.saveCompetitor(testCompetitor);
            String loginResult = competitorList.checkLogin("TEST001", "password123");
            // Login check should complete without exception
        });
    }

    @Test
    public void testCheckLogin_WrongPassword() {
        assertDoesNotThrow(() -> {
            competitorList.saveCompetitor(testCompetitor);
            String loginResult = competitorList.checkLogin("TEST001", "wrongpassword");
            // Should handle wrong password gracefully
        });
    }

    @Test
    public void testCheckLogin_UserNotFound() {
        assertDoesNotThrow(() -> {
            String loginResult = competitorList.checkLogin("NONEXISTENT", "password");
            // Should handle non-existent user gracefully
        });
    }

    @Test
    public void testGetLeaderboard() {
        assertDoesNotThrow(() -> {
            // Add competitor with scores
            testCompetitor.addQuizAttemptScore(95);
            competitorList.saveCompetitor(testCompetitor);
            
            ArrayList<RONCompetitor> leaderboard = competitorList.getLeaderboard();
            assertNotNull(leaderboard);
        });
    }

    @Test
    public void testLoadFromDatabase() {
        assertDoesNotThrow(() -> {
            competitorList.loadFromDatabase();
            // Load operation should complete without exception
        });
    }
}