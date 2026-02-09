package QUIZ;

import QUIZ.Name;
import QUIZ.RONCompetitor;

/**
 * Test class for RONCompetitor functionality (standalone test without JUnit)
 * Run this class directly to test RONCompetitor methods
 */
public class RONCompetitorTest {

    private static RONCompetitor competitor;
    private static Name testName;
    private static int testsPassed = 0;
    private static int testsTotal = 0;

    public static void main(String[] args) {
        System.out.println("=== Running RONCompetitor Tests ===");
        
        setUp();
        
        testConstructor();
        testAddQuizAttemptScore_Success();
        testAddQuizAttemptScore_ArrayFull();
        testGetOverallScore_NoScores();
        testGetOverallScore_WithScores();
        testGetFullDetails();
        testGetShortDetails();
        testSetScores();
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + testsPassed + "/" + testsTotal);
        
        if (testsPassed == testsTotal) {
            System.out.println("PASS: All tests passed!");
        } else {
            System.out.println("FAIL: Some tests failed.");
        }
    }

    private static void setUp() {
        testName = new Name("John", "Middle", "Smith");
        competitor = new RONCompetitor("COMP001", testName, "Beginner", "USA", 25, "password123");
    }

    private static void testConstructor() {
        testsTotal++;
        try {
            boolean passed = "COMP001".equals(competitor.getCompetitorID()) &&
                           testName.equals(competitor.getCompetitorName()) &&
                           "Beginner".equals(competitor.getCompetitorLevel()) &&
                           "USA".equals(competitor.getCountry()) &&
                           25 == competitor.getAge() &&
                           competitor.getScores() != null &&
                           competitor.getScores().length == 5;
                           
            if (passed) {
                testsPassed++;
                System.out.println("PASS: testConstructor passed");
            } else {
                System.out.println("FAIL: testConstructor failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testConstructor failed with exception: " + e.getMessage());
        }
    }

    private static void testAddQuizAttemptScore_Success() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            
            boolean addResult1 = competitor.addQuizAttemptScore(85);
            boolean addResult2 = competitor.addQuizAttemptScore(90);
            boolean addResult3 = competitor.addQuizAttemptScore(78);
            
            int[] scores = competitor.getScores();
            boolean scoresCorrect = scores[0] == 85 && scores[1] == 90 && scores[2] == 78;
            
            if (addResult1 && addResult2 && addResult3 && scoresCorrect) {
                testsPassed++;
                System.out.println("PASS: testAddQuizAttemptScore_Success passed");
            } else {
                System.out.println("FAIL: testAddQuizAttemptScore_Success failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testAddQuizAttemptScore_Success failed with exception: " + e.getMessage());
        }
    }

    private static void testAddQuizAttemptScore_ArrayFull() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            
            // Fill up the array
            boolean add1 = competitor.addQuizAttemptScore(85);
            boolean add2 = competitor.addQuizAttemptScore(90);
            boolean add3 = competitor.addQuizAttemptScore(78);
            boolean add4 = competitor.addQuizAttemptScore(88);
            boolean add5 = competitor.addQuizAttemptScore(95);
            
            // Try to add one more (should fail)
            boolean add6 = competitor.addQuizAttemptScore(100);
            
            if (add1 && add2 && add3 && add4 && add5 && !add6) {
                testsPassed++;
                System.out.println("PASS: testAddQuizAttemptScore_ArrayFull passed");
            } else {
                System.out.println("FAIL: testAddQuizAttemptScore_ArrayFull failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testAddQuizAttemptScore_ArrayFull failed with exception: " + e.getMessage());
        }
    }

    private static void testGetOverallScore_NoScores() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            
            // New competitor should have overall score of 0
            if (competitor.getOverallScore() == 0) {
                testsPassed++;
                System.out.println("PASS: testGetOverallScore_NoScores passed");
            } else {
                System.out.println("FAIL: testGetOverallScore_NoScores failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetOverallScore_NoScores failed with exception: " + e.getMessage());
        }
    }

    private static void testGetOverallScore_WithScores() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            
            competitor.addQuizAttemptScore(80);
            competitor.addQuizAttemptScore(90);
            competitor.addQuizAttemptScore(70);
            
            // Should calculate average: (80+90+70)/3 = 80
            if (competitor.getOverallScore() == 80) {
                testsPassed++;
                System.out.println("PASS: testGetOverallScore_WithScores passed");
            } else {
                System.out.println("FAIL: testGetOverallScore_WithScores failed - got: " + competitor.getOverallScore());
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetOverallScore_WithScores failed with exception: " + e.getMessage());
        }
    }

    private static void testGetFullDetails() {
        testsTotal++;
        try {
            String fullDetails = competitor.getFullDetails();
            boolean containsRequiredInfo = fullDetails != null &&
                                         fullDetails.contains("COMP001") &&
                                         fullDetails.contains("John") &&
                                         fullDetails.contains("Smith");
            
            if (containsRequiredInfo) {
                testsPassed++;
                System.out.println("PASS: testGetFullDetails passed");
            } else {
                System.out.println("FAIL: testGetFullDetails failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetFullDetails failed with exception: " + e.getMessage());
        }
    }

    private static void testGetShortDetails() {
        testsTotal++;
        try {
            String shortDetails = competitor.getShortDetails();
            boolean containsRequiredInfo = shortDetails != null &&
                                         shortDetails.contains("COMP001");
            
            if (containsRequiredInfo) {
                testsPassed++;
                System.out.println("PASS: testGetShortDetails passed");
            } else {
                System.out.println("FAIL: testGetShortDetails failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetShortDetails failed with exception: " + e.getMessage());
        }
    }

    private static void testSetScores() {
        testsTotal++;
        try {
            int[] newScores = {90, 85, 95, 88, 92};
            competitor.setScores(newScores);
            
            int[] retrievedScores = competitor.getScores();
            boolean arraysEqual = java.util.Arrays.equals(newScores, retrievedScores);
            
            if (arraysEqual) {
                testsPassed++;
                System.out.println("PASS: testSetScores passed");
            } else {
                System.out.println("FAIL: testSetScores failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testSetScores failed with exception: " + e.getMessage());
        }
    }
}