package QUIZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Question functionality
 * Tests Question class methods using JUnit 5 framework
 */
class QuestionTest {

    private Question question;

    @BeforeEach
    void setUp() {
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
    @DisplayName("Test Question constructor initializes all fields correctly")
    void testConstructor() {
        assertAll("Question constructor should initialize all fields correctly",
                () -> assertEquals("1", question.getId(), "Question ID should match constructor parameter"),
                () -> assertEquals("What is the capital of France?", question.getQuestionText(), "Question text should match"),
                () -> assertEquals("Easy", question.getDifficultyLevel(), "Difficulty should match")
        );
    }

    @Test
    @DisplayName("Test getting question options array")
    void testGetOptions() {
        String[] options = question.getOptions();

        assertAll("Options array should contain all four choices",
                () -> assertNotNull(options, "Options array should not be null"),
                () -> assertEquals(4, options.length, "Should have exactly 4 options"),
                () -> assertEquals("London", options[0], "First option should be London"),
                () -> assertEquals("Berlin", options[1], "Second option should be Berlin"),
                () -> assertEquals("Paris", options[2], "Third option should be Paris"),
                () -> assertEquals("Madrid", options[3], "Fourth option should be Madrid")
        );
    }

    @Test
    @DisplayName("Test correct answer validation - option C")
    void testIsCorrect_CorrectAnswer() {
        // The correct answer is "C" which corresponds to index 2 (Paris)
        assertTrue(question.isCorrect(2), "Option C (Paris) should be the correct answer");
    }

    @Test
    @DisplayName("Test incorrect answer validation")
    void testIsCorrect_IncorrectAnswers() {
        assertAll("Incorrect options should return false",
                () -> assertFalse(question.isCorrect(0), "Option A (London) should be incorrect"),
                () -> assertFalse(question.isCorrect(1), "Option B (Berlin) should be incorrect"),
                () -> assertFalse(question.isCorrect(3), "Option D (Madrid) should be incorrect")
        );
    }

    @Test
    @DisplayName("Test question text getter")
    void testGetQuestionText() {
        String questionText = question.getQuestionText();
        
        assertAll("Question text should be properly formatted",
                () -> assertNotNull(questionText, "Question text should not be null"),
                () -> assertEquals("What is the capital of France?", questionText, "Question text should match constructor parameter"),
                () -> assertTrue(questionText.contains("capital of France"), "Question should contain expected content")
        );
    }

    @Test
    @DisplayName("Test question ID getter")
    void testGetId() {
        assertEquals("1", question.getId(), "Question ID should match constructor parameter");
    }

    @Test
    @DisplayName("Test difficulty level getter")
    void testGetDifficultyLevel() {
        assertEquals("Easy", question.getDifficultyLevel(), "Difficulty level should match constructor parameter");
    }

    @Test
    @DisplayName("Test all correct answer positions work properly")
    void testAllCorrectAnswerOptions() {
        Question questionA = new Question(1, "Test A", "Correct", "B", "C", "D", "A", "Easy");
        Question questionB = new Question(2, "Test B", "A", "Correct", "C", "D", "B", "Easy");
        Question questionC = new Question(3, "Test C", "A", "B", "Correct", "D", "C", "Easy");
        Question questionD = new Question(4, "Test D", "A", "B", "C", "Correct", "D", "Easy");

        assertAll("All answer positions should work as correct answers",
                () -> assertTrue(questionA.isCorrect(0), "Answer A should be correct for questionA"),
                () -> assertTrue(questionB.isCorrect(1), "Answer B should be correct for questionB"),
                () -> assertTrue(questionC.isCorrect(2), "Answer C should be correct for questionC"),
                () -> assertTrue(questionD.isCorrect(3), "Answer D should be correct for questionD")
        );
    }

    @Test
    @DisplayName("Test question content validation")
    void testQuestionContent() {
        String questionText = question.getQuestionText();
        String[] options = question.getOptions();
        String id = question.getId();
        
        assertAll("Question content should be properly initialized",
            () -> assertNotNull(questionText, "Question text should not be null"),
            () -> assertNotNull(options, "Options should not be null"),
            () -> assertEquals(4, options.length, "Should have exactly 4 options"),
            () -> assertEquals("London", options[0], "First option should be London"),
            () -> assertEquals("Berlin", options[1], "Second option should be Berlin"),
            () -> assertEquals("Paris", options[2], "Third option should be Paris"),
            () -> assertEquals("Madrid", options[3], "Fourth option should be Madrid"),
            () -> assertEquals("1", id, "Question ID should match")
        );
    }
}