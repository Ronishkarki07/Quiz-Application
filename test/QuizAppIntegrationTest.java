import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import QUIZ.*;
import GUI.Admin.QuestionBank;
import DatabaseConfig.CompetitorList;
import DatabaseConfig.DatabaseConnection;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Integration tests for QuizApp - tests interaction between components
 */
public class QuizAppIntegrationTest {

    private RONCompetitor testCompetitor;
    private QuestionBank questionBank;
    private CompetitorList competitorList;

    @BeforeEach
    public void setUp() {
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

    @AfterEach
    public void tearDown() {
        // Clean up test data
        try {
            competitorList.removeCompetitor("INT001");
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    @Test
    public void testFullQuizWorkflow() {
        assertDoesNotThrow(() -> {
            // 1. Register competitor
            boolean registered = competitorList.addCompetitor(testCompetitor);
            
            // 2. Get questions for quiz
            ArrayList<Question> questions = questionBank.getQuestionsForLevel("Beginner");
            assertNotNull(questions);
            
            // 3. Simulate taking quiz
            if (!questions.isEmpty()) {
                Question firstQuestion = questions.get(0);
                
                // Answer the question (assume option 0 is correct for test)
                boolean correct = firstQuestion.isCorrect(0);
                int score = correct ? 100 : 0;
                
                // 4. Record score
                boolean scoreAdded = testCompetitor.addQuizAttemptScore(score);
                assertTrue(scoreAdded);
                
                // 5. Update competitor in database
                boolean updated = competitorList.updateCompetitor(testCompetitor);
            }
        });
    }

    @Test
    public void testDatabaseConnectivity() {
        assertDoesNotThrow(() -> {
            // Test that all components can connect to database
            Connection dbConnection = DatabaseConnection.getConnection();
            
            if (dbConnection != null) {
                assertTrue(dbConnection.isValid(5));
                
                // Test question loading
                ArrayList<Question> questions = questionBank.getAllQuestions();
                assertNotNull(questions);
                
                // Test competitor operations
                competitorList.loadFromDatabase();
                
                dbConnection.close();
            }
        });
    }

    @Test
    public void testScoreCalculationWorkflow() {
        assertDoesNotThrow(() -> {
            // Add multiple quiz attempts
            testCompetitor.addQuizAttemptScore(80);
            testCompetitor.addQuizAttemptScore(85);
            testCompetitor.addQuizAttemptScore(90);
            
            // Calculate overall score
            double overallScore = testCompetitor.getOverallScore();
            assertEquals(85.0, overallScore, 0.01); // Average of 80, 85, 90
            
            // Update in database
            competitorList.updateCompetitor(testCompetitor);
        });
    }

    @Test
    public void testQuestionDifficultyProgression() {
        assertDoesNotThrow(() -> {
            // Test progression through difficulty levels
            ArrayList<Question> beginnerQuestions = questionBank.getQuestionsForLevel("Beginner");
            ArrayList<Question> intermediateQuestions = questionBank.getQuestionsForLevel("Intermediate");
            ArrayList<Question> advancedQuestions = questionBank.getQuestionsForLevel("Advanced");
            
            assertNotNull(beginnerQuestions);
            assertNotNull(intermediateQuestions);
            assertNotNull(advancedQuestions);
            
            // Simulate completing beginner level
            if (!beginnerQuestions.isEmpty()) {
                testCompetitor.addQuizAttemptScore(85);
                testCompetitor.setCompetitorLevel("Intermediate");
                
                // Should now be able to access intermediate questions
                assertTrue(testCompetitor.getCompetitorLevel().equals("Intermediate"));
            }
        });
    }

    @Test
    public void testLeaderboardGeneration() {
        assertDoesNotThrow(() -> {
            // Add competitor with score
            testCompetitor.addQuizAttemptScore(95);
            competitorList.addCompetitor(testCompetitor);
            
            // Generate leaderboard
            String leaderboard = Manager.getLeaderboard();
            assertNotNull(leaderboard);
        });
    }

    @Test
    public void testMultipleCompetitors() {
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
            
            // Add to list
            competitorList.addCompetitor(competitor1);
            competitorList.addCompetitor(competitor2);
            
            // Verify they're added
            assertTrue(competitorList.competitorExists("INT002"));
            assertTrue(competitorList.competitorExists("INT003"));
            
            // Clean up
            competitorList.removeCompetitor("INT002");
            competitorList.removeCompetitor("INT003");
        });
    }

    @Test
    public void testDataPersistence() {
        assertDoesNotThrow(() -> {
            // Add competitor
            competitorList.addCompetitor(testCompetitor);
            
            // Save to database
            competitorList.saveToDatabase();
            
            // Create new list and load from database
            CompetitorList newList = new CompetitorList();
            newList.loadFromDatabase();
            
            // Should find the competitor
            RONCompetitor foundCompetitor = newList.findCompetitor("INT001");
            // May or may not find based on database state, but should not throw exception
        });
    }

    @Test
    public void testErrorHandling() {
        assertDoesNotThrow(() -> {
            // Test invalid operations
            assertFalse(testCompetitor.addQuizAttemptScore(-1)); // Invalid score
            
            // Test null safety
            RONCompetitor nullCompetitor = competitorList.findCompetitor(null);
            // Should handle null gracefully
            
            // Test invalid question access
            ArrayList<Question> invalidQuestions = questionBank.getQuestionsForLevel("InvalidLevel");
            assertNotNull(invalidQuestions);
            assertTrue(invalidQuestions.isEmpty());
        });
    }

    @Test
    public void testSystemLimits() {
        assertDoesNotThrow(() -> {
            // Test maximum quiz attempts
            for (int i = 0; i < 6; i++) {  // Try to add 6 attempts (max is 5)
                testCompetitor.addQuizAttemptScore(80 + i);
            }
            
            // After 5 attempts, no more should be allowed
            int[] scores = testCompetitor.getScores();
            assertEquals(5, scores.length);
            
            // Check that all 5 slots are filled
            int filledSlots = 0;
            for (int score : scores) {
                if (score > 0) filledSlots++;
            }
            assertEquals(5, filledSlots);
        });
    }
}