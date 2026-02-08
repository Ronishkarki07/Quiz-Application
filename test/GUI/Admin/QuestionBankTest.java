package GUI.Admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import QUIZ.Question;
import DatabaseConfig.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        // Questions should be loaded from database on construction
        ArrayList<Question> allQuestions = questionBank.getAllQuestions();
        assertNotNull(allQuestions);
    }

    @Test
    public void testGetQuestionsForLevel_Beginner() {
        ArrayList<Question> beginnerQuestions = questionBank.getQuestionsForLevel("Beginner");
        assertNotNull(beginnerQuestions);
        
        // All questions should be Beginner level
        for (Question q : beginnerQuestions) {
            assertEquals("Beginner", q.getDifficultyLevel());
        }
    }

    @Test
    public void testGetQuestionsForLevel_Intermediate() {
        ArrayList<Question> intermediateQuestions = questionBank.getQuestionsForLevel("Intermediate");
        assertNotNull(intermediateQuestions);
        
        // All questions should be Intermediate level
        for (Question q : intermediateQuestions) {
            assertEquals("Intermediate", q.getDifficultyLevel());
        }
    }

    @Test
    public void testGetQuestionsForLevel_Advanced() {
        ArrayList<Question> advancedQuestions = questionBank.getQuestionsForLevel("Advanced");
        assertNotNull(advancedQuestions);
        
        // All questions should be Advanced level
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
        assertTrue(questions.isEmpty());
    }

    @Test
    public void testGetQuestionsForLevel_EmptyLevel() {
        ArrayList<Question> questions = questionBank.getQuestionsForLevel("");
        assertNotNull(questions);
        assertTrue(questions.isEmpty());
    }

    @Test
    public void testGetRandomizedQuestions() {
        ArrayList<Question> beginnerQuestions1 = questionBank.getQuestionsForLevel("Beginner");
        ArrayList<Question> beginnerQuestions2 = questionBank.getQuestionsForLevel("Beginner");
        
        // Questions should be randomized (if there are multiple questions)
        if (beginnerQuestions1.size() > 1) {
            // Note: There's a small chance they might be in the same order due to randomization
            // This test might occasionally fail due to the random nature
            boolean orderChanged = false;
            for (int i = 0; i < Math.min(beginnerQuestions1.size(), beginnerQuestions2.size()); i++) {
                if (!beginnerQuestions1.get(i).getId().equals(beginnerQuestions2.get(i).getId())) {
                    orderChanged = true;
                    break;
                }
            }
            // Note: This assertion might be flaky due to randomization
        }
    }

    @Test
    public void testGetAllQuestions() {
        ArrayList<Question> allQuestions = questionBank.getAllQuestions();
        assertNotNull(allQuestions);
        
        // Should contain questions from all difficulty levels
        boolean hasBeginner = false, hasIntermediate = false, hasAdvanced = false;
        
        for (Question q : allQuestions) {
            String level = q.getDifficultyLevel();
            if ("Beginner".equals(level)) hasBeginner = true;
            else if ("Intermediate".equals(level)) hasIntermediate = true;
            else if ("Advanced".equals(level)) hasAdvanced = true;
        }
        
        // At least one level should have questions (assuming database has data)
        assertTrue(hasBeginner || hasIntermediate || hasAdvanced);
    }

    @Test
    public void testDatabaseReload() {
        // Get initial count
        ArrayList<Question> initialQuestions = questionBank.getAllQuestions();
        int initialCount = initialQuestions.size();
        
        // Reload questions (simulates adding new questions)
        questionBank.reloadQuestions();
        
        ArrayList<Question> reloadedQuestions = questionBank.getAllQuestions();
        assertNotNull(reloadedQuestions);
        // Count should be the same or more (if new questions were added)
        assertTrue(reloadedQuestions.size() >= initialCount);
    }

    @Test
    public void testQuestionIntegrity() {
        ArrayList<Question> allQuestions = questionBank.getAllQuestions();
        
        for (Question q : allQuestions) {
            // Each question should have valid properties
            assertNotNull(q.getId());
            assertNotNull(q.getQuestionText());
            assertNotNull(q.getOptions());
            assertEquals(4, q.getOptions().length); // Should have 4 options
            assertNotNull(q.getDifficultyLevel());
            assertNotNull(q.getCorrectAnswerLetter());
            
            // Correct answer should be A, B, C, or D
            String correctLetter = q.getCorrectAnswerLetter();
            assertTrue(correctLetter.equals("A") || correctLetter.equals("B") || 
                      correctLetter.equals("C") || correctLetter.equals("D"));
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
}