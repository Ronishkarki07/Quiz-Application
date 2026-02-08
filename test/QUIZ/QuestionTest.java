package QUIZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Question functionality
 */
public class QuestionTest {

    private Question question;

    @BeforeEach
    public void setUp() {
        question = new Question(
            1,
            "What is the capital of France?",
            "London",
            "Berlin", 
            "Paris",
            "Madrid",
            "C",
            "Easy"
        );
    }

    @Test
    public void testConstructor() {
        assertEquals("1", question.getId());
        assertEquals("What is the capital of France?", question.getQuestionText());
        assertEquals("Easy", question.getDifficultyLevel());
    }

    @Test
    public void testGetOptions() {
        String[] options = question.getOptions();
        assertNotNull(options);
        assertEquals(4, options.length);
        assertEquals("London", options[0]);
        assertEquals("Berlin", options[1]);
        assertEquals("Paris", options[2]);
        assertEquals("Madrid", options[3]);
    }

    @Test
    public void testIsCorrect_CorrectAnswer() {
        // Option C (index 2) is correct
        assertTrue(question.isCorrect(2));
    }

    @Test
    public void testIsCorrect_IncorrectAnswers() {
        assertFalse(question.isCorrect(0)); // A - London
        assertFalse(question.isCorrect(1)); // B - Berlin 
        assertFalse(question.isCorrect(3)); // D - Madrid
    }

    @Test
    public void testIsCorrect_InvalidIndex() {
        assertFalse(question.isCorrect(-1));
        assertFalse(question.isCorrect(4));
        assertFalse(question.isCorrect(10));
    }

    @Test
    public void testGetCorrectAnswerLetter() {
        assertEquals("C", question.getCorrectAnswerLetter());
    }

    @Test
    public void testDifferentDifficultyLevels() {
        Question easyQ = new Question(1, "Easy Question", "A", "B", "C", "D", "A", "Easy");
        Question mediumQ = new Question(2, "Medium Question", "A", "B", "C", "D", "B", "Medium");
        Question hardQ = new Question(3, "Hard Question", "A", "B", "C", "D", "C", "Hard");

        assertEquals("Easy", easyQ.getDifficultyLevel());
        assertEquals("Medium", mediumQ.getDifficultyLevel());
        assertEquals("Hard", hardQ.getDifficultyLevel());
    }

    @Test
    public void testAllCorrectAnswerOptions() {
        Question questionA = new Question(1, "Test A", "Correct", "B", "C", "D", "A", "Easy");
        Question questionB = new Question(2, "Test B", "A", "Correct", "C", "D", "B", "Easy");
        Question questionC = new Question(3, "Test C", "A", "B", "Correct", "D", "C", "Easy");
        Question questionD = new Question(4, "Test D", "A", "B", "C", "Correct", "D", "Easy");

        assertTrue(questionA.isCorrect(0)); // A is correct
        assertTrue(questionB.isCorrect(1)); // B is correct
        assertTrue(questionC.isCorrect(2)); // C is correct
        assertTrue(questionD.isCorrect(3)); // D is correct
    }

    @Test
    public void testQuestionDisplay() {
        String display = question.getQuestionDisplay();
        assertNotNull(display);
        assertTrue(display.contains("What is the capital of France?"));
        assertTrue(display.contains("A)"));
        assertTrue(display.contains("B)"));
        assertTrue(display.contains("C)"));
        assertTrue(display.contains("D)"));
    }

    @Test
    public void testQuestionToString() {
        String questionStr = question.toString();
        assertNotNull(questionStr);
        assertTrue(questionStr.contains("What is the capital of France?"));
    }

    @Test
    public void testGetters() {
        assertEquals("London", question.getOptionA());
        assertEquals("Berlin", question.getOptionB()); 
        assertEquals("Paris", question.getOptionC());
        assertEquals("Madrid", question.getOptionD());
    }

    @Test
    public void testSetters() {
        question.setQuestionText("Updated question?");
        assertEquals("Updated question?", question.getQuestionText());
        
        question.setDifficultyLevel("Hard");
        assertEquals("Hard", question.getDifficultyLevel());
    }
}