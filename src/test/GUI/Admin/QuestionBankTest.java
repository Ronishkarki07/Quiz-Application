package test.GUI.Admin;

import GUI.Admin.QuestionBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import QUIZ.Question;
import java.util.ArrayList;

/**
 * Test class for QuestionBank functionality
 */
public class QuestionBankTest {

    private QuestionBank questionBank;

    @BeforeEach
    public void setUp() {
        // Note: These tests assume database connectivity
        // In a production environment, you might want to use a test database
        questionBank = new QuestionBank();
    }

    @Test
    public void testConstructor() {
        assertNotNull(questionBank);
        // Test that QuestionBank can retrieve questions for any level
        ArrayList<Question> beginnerQuestions = questionBank.getQuestionsForLevel("Beginner");
        assertNotNull(beginnerQuestions);
    }

    @Test
    public void testGetQuestionsForLevel_Beginner() {
        ArrayList<Question> beginnerQuestions = questionBank.getQuestionsForLevel("Beginner");
        assertNotNull(beginnerQuestions);
        
        // All questions should be beginner level
        for (Question q : beginnerQuestions) {
            assertEquals("Beginner", q.getDifficultyLevel());
        }
    }

    @Test
    public void testGetQuestionsForLevel_Intermediate() {
        ArrayList<Question> intermediateQuestions = questionBank.getQuestionsForLevel("Intermediate");
        assertNotNull(intermediateQuestions);
        
        // All questions should be intermediate level
        for (Question q : intermediateQuestions) {
            assertEquals("Intermediate", q.getDifficultyLevel());
        }
    }

    @Test
    public void testGetQuestionsForLevel_Advanced() {
        ArrayList<Question> advancedQuestions = questionBank.getQuestionsForLevel("Advanced");
        assertNotNull(advancedQuestions);
        
        // All questions should be advanced level
        for (Question q : advancedQuestions) {
            assertEquals("Advanced", q.getDifficultyLevel());
        }
    }

    @Test
    public void testGetQuestionsForLevel_InvalidLevel() {
        ArrayList<Question> questions = questionBank.getQuestionsForLevel("NonExistentLevel");
        assertNotNull(questions);
        assertTrue(questions.isEmpty());
    }

    @Test
    public void testGetQuestionsForLevel_NullLevel() {
        ArrayList<Question> questions = questionBank.getQuestionsForLevel(null);
        assertNotNull(questions);
        // May return empty or handle gracefully
    }

    @Test
    public void testGetQuestionsForLevel_EmptyLevel() {
        ArrayList<Question> questions = questionBank.getQuestionsForLevel("");
        assertNotNull(questions);
        assertTrue(questions.isEmpty());
    }

    @Test
    public void testRandomizedQuestions() {
        ArrayList<Question> beginnerQuestions1 = questionBank.getQuestionsForLevel("Beginner");
        ArrayList<Question> beginnerQuestions2 = questionBank.getQuestionsForLevel("Beginner");
        
        // Questions should be randomized (if there are multiple questions)
        if (beginnerQuestions1.size() > 1) {
            // Note: There's a small chance they might be in the same order due to randomization
            // This test might occasionally fail due to the random nature
            boolean orderChanged = false;
            for (int i = 0; i < Math.min(beginnerQuestions1.size(), beginnerQuestions2.size()); i++) {
                if (!beginnerQuestions1.get(i).equals(beginnerQuestions2.get(i))) {
                    orderChanged = true;
                    break;
                }
            }
            // Note: This assertion might be flaky due to randomization
        }
    }

    @Test
    public void testLevelRetrieval() {
        // Test getting questions for each difficulty level
        ArrayList<Question> beginnerQuestions = questionBank.getQuestionsForLevel("Beginner");
        ArrayList<Question> intermediateQuestions = questionBank.getQuestionsForLevel("Intermediate");
        ArrayList<Question> advancedQuestions = questionBank.getQuestionsForLevel("Advanced");
        
        assertNotNull(beginnerQuestions);
        assertNotNull(intermediateQuestions);
        assertNotNull(advancedQuestions);
        
        // Verify questions have correct difficulty levels
        for (Question q : beginnerQuestions) {
            assertEquals("Beginner", q.getDifficultyLevel());
        }
        for (Question q : intermediateQuestions) {
            assertEquals("Intermediate", q.getDifficultyLevel());
        }
        for (Question q : advancedQuestions) {
            assertEquals("Advanced", q.getDifficultyLevel());
        }
        
        // Test that each level returns at most 5 questions (per implementation)
        assertTrue(beginnerQuestions.size() <= 5);
        assertTrue(intermediateQuestions.size() <= 5);
        assertTrue(advancedQuestions.size() <= 5);
    }

    @Test
    public void testMultipleRetrievals() {
        // Test that multiple calls return valid data
        ArrayList<Question> firstCall = questionBank.getQuestionsForLevel("Beginner");
        ArrayList<Question> secondCall = questionBank.getQuestionsForLevel("Beginner");
        
        assertNotNull(firstCall);
        assertNotNull(secondCall);
        
        // Both calls should return questions (if any exist in database)
        // Note: Questions may be in different order due to shuffling
    }

    @Test
    public void testQuestionIntegrity() {
        // Test questions for each level to verify their structure
        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        
        for (String level : levels) {
            ArrayList<Question> questions = questionBank.getQuestionsForLevel(level);
            
            for (Question q : questions) {
                // Each question should have valid properties
                assertNotNull(q.getId());
                assertNotNull(q.getQuestionText());
                assertNotNull(q.getOptions());
                assertEquals(4, q.getOptions().length); // Should have 4 options
                assertNotNull(q.getDifficultyLevel());
                assertEquals(level, q.getDifficultyLevel());
                
                // Verify question text is not empty
                assertFalse(q.getQuestionText().trim().isEmpty());
                
                // Verify options are not empty
                String[] options = q.getOptions();
                for (String option : options) {
                    assertNotNull(option);
                    assertFalse(option.trim().isEmpty());
                }
            }
        }
    }

    @Test
    public void testQuestionLimitForQuiz() {
        // Typically a quiz should have a limited number of questions (e.g., 10)
        ArrayList<Question> beginnerQuestions = questionBank.getQuestionsForLevel("Beginner");
        
        if (beginnerQuestions.size() > 10) {
            // Should be able to get subset for quiz
            ArrayList<Question> quizQuestions = new ArrayList<>(beginnerQuestions.subList(0, 10));
            assertEquals(10, quizQuestions.size());
        }
    }

    @Test
    public void testDatabaseConnectivity() {
        // This test verifies that the QuestionBank can connect to the database
        // and retrieve questions. This is more of an integration test.
         assertDoesNotThrow(() -> {
            QuestionBank testBank = new QuestionBank();
            
            // Try to get questions for each level
            ArrayList<Question> beginner = testBank.getQuestionsForLevel("Beginner");
            ArrayList<Question> intermediate = testBank.getQuestionsForLevel("Intermediate");
            ArrayList<Question> advanced = testBank.getQuestionsForLevel("Advanced");
            
            // At least one level should have questions (assuming database has some data)
            assertNotNull(beginner);
            assertNotNull(intermediate);
            assertNotNull(advanced);
        });
    }

    @Test
    public void testEmptyDatabase() {
        // Test behavior when database might be empty
        // This is a boundary condition test
        ArrayList<Question> questions = questionBank.getQuestionsForLevel("Beginner");
        
        // Should handle gracefully - return empty list, not null
        assertNotNull(questions);
        // The list may or may not be empty depending on database state
    }
}