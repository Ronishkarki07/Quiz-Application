/**
 * Simple test runner for QuizApp tests compatible with JUnit 5.14.0
 * Can be run as a main class to execute all tests
 */
public class TestRunner {

    /**
     * Run basic functionality tests without database dependency
     */
    public static void runBasicTests() {
        System.out.println("Running basic functionality tests...");
        
        try {
            // Test Name functionality
            QUIZ.Name testName = new QUIZ.Name("Test", "User", "Name");
            assert testName.getFullName().contains("Test User Name");
            System.out.println("PASS: Name class test passed");
            
            // Test RONCompetitor functionality
            QUIZ.RONCompetitor competitor = new QUIZ.RONCompetitor(
                "TEST001", testName, "Beginner", "USA", 25, "password"
            );
            assert competitor.getCompetitorID().equals("TEST001");
            assert competitor.addQuizAttemptScore(85);
            assert competitor.getOverallScore() == 85.0;
            System.out.println("PASS: RONCompetitor class test passed");
            
            // Test Question functionality
            QUIZ.Question question = new QUIZ.Question(
                1, "Test question?", "A", "B", "C", "D", "A", "Easy"
            );
            assert question.isCorrect(0); // Option A should be correct
            assert !question.isCorrect(1); // Option B should be incorrect
            System.out.println("PASS: Question class test passed");
            
            System.out.println("\nPASS: Basic functionality tests completed successfully!");
            
        } catch (Exception e) {
            System.out.println("FAIL: Basic tests failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}