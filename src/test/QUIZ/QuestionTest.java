package test.QUIZ;

import QUIZ.Question;

/**
 * Test class for Question functionality
 * Run this class directly to execute all tests
 */
public class QuestionTest {

    private static Question question;
    private static int testsPassed = 0;
    private static int testsTotal = 0;

    public static void main(String[] args) {
        System.out.println("=== Running Question Tests ===");
        
        setUp();
        
        testConstructor();
        testGetOptions();
        testIsCorrect_CorrectAnswer();
        testIsCorrect_IncorrectAnswers();
        testIsCorrect_InvalidIndex();
        testCorrectAnswerValidation();
        testDifferentDifficultyLevels();
        testAllCorrectAnswerOptions();
        testQuestionContent();
        testQuestionProperties();
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + testsPassed + "/" + testsTotal);
        
        if (testsPassed == testsTotal) {
            System.out.println("PASS: All tests passed!");
        } else {
            System.out.println("FAIL: Some tests failed.");
        }
    }
    private static void setUp() {
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

    private static void testConstructor() {
        testsTotal++;
        try {
            String id = question.getId();
            String questionText = question.getQuestionText();
            String difficulty = question.getDifficultyLevel();
            
            if ("1".equals(id) && "What is the capital of France?".equals(questionText) && "Easy".equals(difficulty)) {
                testsPassed++;
                System.out.println("PASS: testConstructor passed");
            } else {
                System.out.println("FAIL: testConstructor failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testConstructor failed with exception: " + e.getMessage());
        }
    }

    private static void testGetOptions() {
        testsTotal++;
        try {
            String[] options = question.getOptions();
            if (options != null && options.length == 4 && 
                "London".equals(options[0]) && "Berlin".equals(options[1]) && 
                "Paris".equals(options[2]) && "Madrid".equals(options[3])) {
                testsPassed++;
                System.out.println("PASS: testGetOptions passed");
            } else {
                System.out.println("FAIL: testGetOptions failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetOptions failed with exception: " + e.getMessage());
        }
    }

    private static void testIsCorrect_CorrectAnswer() {
        testsTotal++;
        try {
            if (question.isCorrect(2)) { // C is correct
                testsPassed++;
                System.out.println("PASS: testIsCorrect_CorrectAnswer passed");
            } else {
                System.out.println("FAIL: testIsCorrect_CorrectAnswer failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testIsCorrect_CorrectAnswer failed with exception: " + e.getMessage());
        }
    }

    private static void testIsCorrect_IncorrectAnswers() {
        testsTotal++;
        try {
            if (!question.isCorrect(0) && !question.isCorrect(1) && !question.isCorrect(3)) {
                testsPassed++;
                System.out.println("PASS: testIsCorrect_IncorrectAnswers passed");
            } else {
                System.out.println("FAIL: testIsCorrect_IncorrectAnswers failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testIsCorrect_IncorrectAnswers failed with exception: " + e.getMessage());
        }
    }

    private static void testIsCorrect_InvalidIndex() {
        testsTotal++;
        try {
            if (!question.isCorrect(-1) && !question.isCorrect(4) && !question.isCorrect(10)) {
                testsPassed++;
                System.out.println("PASS: testIsCorrect_InvalidIndex passed");
            } else {
                System.out.println("FAIL: testIsCorrect_InvalidIndex failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testIsCorrect_InvalidIndex failed with exception: " + e.getMessage());
        }
    }

    private static void testCorrectAnswerValidation() {
        testsTotal++;
        try {
            if (question.isCorrect(2) && !question.isCorrect(0) && !question.isCorrect(1) && !question.isCorrect(3)) {
                testsPassed++;
                System.out.println("PASS: testCorrectAnswerValidation passed");
            } else {
                System.out.println("FAIL: testCorrectAnswerValidation failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testCorrectAnswerValidation failed with exception: " + e.getMessage());
        }
    }

    private static void testDifferentDifficultyLevels() {
        testsTotal++;
        try {
            Question easyQ = new Question(1, "Easy Question", "A", "B", "C", "D", "A", "Easy");
            Question mediumQ = new Question(2, "Medium Question", "A", "B", "C", "D", "B", "Medium");
            Question hardQ = new Question(3, "Hard Question", "A", "B", "C", "D", "C", "Hard");

            if ("Easy".equals(easyQ.getDifficultyLevel()) && 
                "Medium".equals(mediumQ.getDifficultyLevel()) &&
                "Hard".equals(hardQ.getDifficultyLevel())) {
                testsPassed++;
                System.out.println("PASS: testDifferentDifficultyLevels passed");
            } else {
                System.out.println("FAIL: testDifferentDifficultyLevels failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testDifferentDifficultyLevels failed with exception: " + e.getMessage());
        }
    }

    private static void testAllCorrectAnswerOptions() {
        testsTotal++;
        try {
            Question questionA = new Question(1, "Test A", "Correct", "B", "C", "D", "A", "Easy");
            Question questionB = new Question(2, "Test B", "A", "Correct", "C", "D", "B", "Easy");
            Question questionC = new Question(3, "Test C", "A", "B", "Correct", "D", "C", "Easy");
            Question questionD = new Question(4, "Test D", "A", "B", "C", "Correct", "D", "Easy");

            if (questionA.isCorrect(0) && questionB.isCorrect(1) && 
                questionC.isCorrect(2) && questionD.isCorrect(3)) {
                testsPassed++;
                System.out.println("PASS: testAllCorrectAnswerOptions passed");
            } else {
                System.out.println("FAIL: testAllCorrectAnswerOptions failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testAllCorrectAnswerOptions failed with exception: " + e.getMessage());
        }
    }

    private static void testQuestionContent() {
        testsTotal++;
        try {
            String questionText = question.getQuestionText();
            String[] options = question.getOptions();
            String difficulty = question.getDifficultyLevel();
            
            if (questionText != null && questionText.contains("capital of France") &&
                options != null && options.length == 4 &&
                difficulty != null && "Easy".equals(difficulty)) {
                testsPassed++;
                System.out.println("PASS: testQuestionContent passed");
            } else {
                System.out.println("FAIL: testQuestionContent failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testQuestionContent failed with exception: " + e.getMessage());
        }
    }

    private static void testQuestionProperties() {
        testsTotal++;
        try {
            String[] options = question.getOptions();
            String id = question.getId();
            
            if (options != null && options.length == 4 &&
                "London".equals(options[0]) && "Berlin".equals(options[1]) &&
                "Paris".equals(options[2]) && "Madrid".equals(options[3]) &&
                "1".equals(id)) {
                testsPassed++;
                System.out.println("PASS: testQuestionProperties passed");
            } else {
                System.out.println("FAIL: testQuestionProperties failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testQuestionProperties failed with exception: " + e.getMessage());
        }
    }
}