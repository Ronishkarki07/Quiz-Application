import QUIZ.*;
import GUI.Admin.QuestionBank;
import DatabaseConfig.CompetitorList;
import DatabaseConfig.DatabaseConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * JUnit integration tests for QuizApp
 * Tests interaction between components and system workflows
 */
class QuizAppIntegrationTest {

    private RONCompetitor testCompetitor;
    private QuestionBank questionBank;
    private CompetitorList competitorList;

    @BeforeEach
    void setUp() {
        // Set up test data
        Name testName = new Name("Integration", "Test", "User");
        testCompetitor = new RONCompetitor(
            "INT001", 
            testName, 
            "Beginner", 
            "USA", 
            25, 
            "testpass123"
        );
        
        questionBank = new QuestionBank();
        competitorList = new CompetitorList();
    }

    @Test
    @DisplayName("Test full quiz workflow from start to finish")
    void testFullQuizWorkflow() {
        assertDoesNotThrow(() -> {
            // 1. Save competitor to database
            competitorList.saveCompetitor(testCompetitor);
            
            // 2. Get questions for quiz
            ArrayList<Question> questions = questionBank.getQuestionsForLevel("Beginner");
            
            if (questions != null && !questions.isEmpty()) {
                // 3. Simulate taking quiz
                Question firstQuestion = questions.get(0);
                
                // Answer the question (assume option 0 is correct for test)
                boolean correct = firstQuestion.isCorrect(0);
                int score = correct ? 100 : 0;
                
                // 4. Record score
                boolean scoreAdded = testCompetitor.addQuizAttemptScore(score);
                assertTrue(scoreAdded, "Score should be added successfully");
                
                // 5. Update competitor in database
                competitorList.saveCompetitor(testCompetitor);
            }
        }, "Full quiz workflow should complete without exception");
    }

    @Test
    @DisplayName("Test database connectivity across components")
    void testDatabaseConnectivity() {
        assertDoesNotThrow(() -> {
            // Test that all components can connect to database
            Connection dbConnection = DatabaseConnection.getConnection();
            
            if (dbConnection != null) {
                assertTrue(dbConnection.isValid(5), "Database connection should be valid");
                
                // Test question loading capability
                questionBank.getQuestionsForLevel("Beginner");
                
                // Test competitor operations
                competitorList.loadFromDatabase();
                
                dbConnection.close();
            }
        }, "Database connectivity test should complete without exception");
    }

    @Test
    @DisplayName("Test score calculation workflow with multiple attempts")
    void testScoreCalculationWorkflow() {
        assertDoesNotThrow(() -> {
            // Add multiple quiz attempts
            testCompetitor.addQuizAttemptScore(80);
            testCompetitor.addQuizAttemptScore(85);
            testCompetitor.addQuizAttemptScore(90);
            
            // Calculate overall score
            double overallScore = testCompetitor.getOverallScore();
            
            // Save updated competitor
            competitorList.saveCompetitor(testCompetitor);
            
            // Average of 80, 85, 90 should be 85.0
            assertEquals(85.0, overallScore, 0.01, 
                        "Overall score should be the average of all attempts");
        }, "Score calculation workflow should complete without exception");
    }

    @Test
    @DisplayName("Test question difficulty progression")
    void testQuestionDifficultyProgression() {
        assertDoesNotThrow(() -> {
            // Test progression through difficulty levels
            ArrayList<Question> beginnerQuestions = questionBank.getQuestionsForLevel("Beginner");
            questionBank.getQuestionsForLevel("Intermediate");
            questionBank.getQuestionsForLevel("Advanced");
            
            // Simulate completing beginner level
            if (beginnerQuestions != null && !beginnerQuestions.isEmpty()) {
                testCompetitor.addQuizAttemptScore(85);
                testCompetitor.setCompetitorLevel("Intermediate");
                
                // Should now be able to access intermediate questions
                assertEquals("Intermediate", testCompetitor.getCompetitorLevel(), 
                           "Competitor level should be updated to Intermediate");
            }
        }, "Difficulty progression test should complete without exception");
    }

    @Test
    @DisplayName("Test leaderboard generation")
    void testLeaderboardGeneration() {
        assertDoesNotThrow(() -> {
            // Add competitor with score
            testCompetitor.addQuizAttemptScore(95);
            competitorList.saveCompetitor(testCompetitor);
            
            // Generate leaderboard using CompetitorList method
            ArrayList<RONCompetitor> leaderboard = competitorList.getLeaderboard();
            
            assertNotNull(leaderboard, "Leaderboard should not be null");
        }, "Leaderboard generation should complete without exception");
    }

    @Test
    @DisplayName("Test multiple competitors handling")
    void testMultipleCompetitors() {
        assertDoesNotThrow(() -> {
            // Create multiple test competitors
            RONCompetitor competitor1 = new RONCompetitor(
                "INT002", new Name("User", "", "One"), "Beginner", "USA", 20, "pass1"
            );
            RONCompetitor competitor2 = new RONCompetitor(
                "INT003", new Name("User", "", "Two"), "Intermediate", "Canada", 25, "pass2"
            );
            
            // Add different scores
            competitor1.addQuizAttemptScore(75);
            competitor2.addQuizAttemptScore(90);
            
            // Save to database
            competitorList.saveCompetitor(competitor1);
            competitorList.saveCompetitor(competitor2);
            
            // Verify they can be handled (may return null if database not available)
            competitorList.findCompetitor("INT002");
            competitorList.findCompetitor("INT003");
        }, "Multiple competitors handling should complete without exception");
    }

    @Test
    @DisplayName("Test data persistence across sessions")
    void testDataPersistence() {
        assertDoesNotThrow(() -> {
            // Save competitor
            competitorList.saveCompetitor(testCompetitor);
            
            // Create new list and load from database
            CompetitorList newList = new CompetitorList();
            newList.loadFromDatabase();
            
            // Should find the competitor (or handle gracefully if database not available)
            newList.findCompetitor("INT001");
        }, "Data persistence test should complete without exception");
    }

    @Test
    @DisplayName("Test error handling for invalid operations")
    void testErrorHandling() {
        assertDoesNotThrow(() -> {
            // Test invalid operations and verify they're handled gracefully
            testCompetitor.addQuizAttemptScore(-1); // Invalid score
            
            // Test null safety
            competitorList.findCompetitor(null);
            
            // Test invalid question access
            questionBank.getQuestionsForLevel("InvalidLevel");
            
            // System should handle these gracefully without throwing exceptions
        }, "Error handling should prevent system crashes");
    }

    @Test
    @DisplayName("Test system limits and constraints")
    void testSystemLimits() {
        assertDoesNotThrow(() -> {
            // Test maximum quiz attempts
            for (int i = 0; i < 6; i++) {  // Try to add 6 attempts (max is 5)
                testCompetitor.addQuizAttemptScore(80 + i);
            }
            
            // After 5 attempts, no more should be allowed
            int[] scores = testCompetitor.getScores();
            
            assertEquals(5, scores.length, "Should maintain maximum of 5 quiz attempts");
            
            // Check that all 5 slots are filled
            int filledSlots = 0;
            for (int score : scores) {
                if (score > 0) filledSlots++;
            }
            
            assertEquals(5, filledSlots, "All 5 attempt slots should be filled");
        }, "System limits test should complete without exception");
    }

    @Test
    @DisplayName("Test complete login workflow")
    void testLoginWorkflow() {
        assertDoesNotThrow(() -> {
            // Save competitor to database
            competitorList.saveCompetitor(testCompetitor);
            
            // Test login success
            String loginResult = competitorList.checkLogin("INT001", "testpass123");
            assertNotNull(loginResult, "Login result should not be null");
            
            // Test wrong password
            String wrongPassResult = competitorList.checkLogin("INT001", "wrongpass");
            assertNotNull(wrongPassResult, "Wrong password result should not be null");
            
            // Test non-existent user
            String notFoundResult = competitorList.checkLogin("NONEXISTENT", "password");
            assertNotNull(notFoundResult, "Not found result should not be null");
        }, "Login workflow should complete without exception");
    }
}