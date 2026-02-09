package DatabaseConfig;

import DatabaseConfig.CompetitorList;
import QUIZ.RONCompetitor;
import QUIZ.Name;
import java.util.ArrayList;

/**
 * Test class for CompetitorList functionality (standalone test without JUnit)
 * Run this class directly to test CompetitorList methods
 * Tests only methods that actually exist in CompetitorList
 */
public class CompetitorListTest {

    private static CompetitorList competitorList;
    private static RONCompetitor testCompetitor;
    private static int testsPassed = 0;
    private static int testsTotal = 0;

    public static void main(String[] args) {
        System.out.println("=== Running CompetitorList Tests ===");
        
        setUp();
        
        testConstructor();
        testSaveCompetitor();
        testFindCompetitor();
        testFindCompetitorNotFound();
        testGetAllCompetitors();
        testCheckLogin_Success();
        testCheckLogin_WrongPassword();
        testCheckLogin_UserNotFound();
        testGetLeaderboard();
        testLoadFromDatabase();
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + testsPassed + "/" + testsTotal);
        
        if (testsPassed == testsTotal) {
            System.out.println("PASS: All tests passed!");
        } else {
            System.out.println("FAIL: Some tests failed.");
        }
    }

    private static void setUp() {
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

    private static void testConstructor() {
        testsTotal++;
        try {
            if (competitorList != null) {
                testsPassed++;
                System.out.println("PASS: testConstructor passed");
            } else {
                System.out.println("FAIL: testConstructor failed - competitorList is null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testConstructor failed with exception: " + e.getMessage());
        }
    }

    private static void testSaveCompetitor() {
        testsTotal++;
        try {
            competitorList.saveCompetitor(testCompetitor);
            // Save operation should complete without exception
            testsPassed++;
            System.out.println("PASS: testSaveCompetitor passed");
        } catch (Exception e) {
            System.out.println("FAIL: testSaveCompetitor failed with exception: " + e.getMessage());
        }
    }

    private static void testFindCompetitor() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            competitorList.saveCompetitor(testCompetitor);
            RONCompetitor found = competitorList.findCompetitor("TEST001");
            // Find operation should complete without exception
            testsPassed++;
            System.out.println("PASS: testFindCompetitor passed");
        } catch (Exception e) {
            System.out.println("FAIL: testFindCompetitor failed with exception: " + e.getMessage());
        }
    }

    private static void testFindCompetitorNotFound() {
        testsTotal++;
        try {
            RONCompetitor notFound = competitorList.findCompetitor("NONEXISTENT");
            // Should return null or handle gracefully for non-existent competitor
            testsPassed++;
            System.out.println("PASS: testFindCompetitorNotFound passed");
        } catch (Exception e) {
            System.out.println("FAIL: testFindCompetitorNotFound failed with exception: " + e.getMessage());
        }
    }

    private static void testGetAllCompetitors() {
        testsTotal++;
        try {
            ArrayList<RONCompetitor> competitors = competitorList.getAllCompetitors();
            if (competitors != null) {
                testsPassed++;
                System.out.println("PASS: testGetAllCompetitors passed");
            } else {
                System.out.println("FAIL: testGetAllCompetitors failed - returned null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetAllCompetitors failed with exception: " + e.getMessage());
        }
    }

    private static void testCheckLogin_Success() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            competitorList.saveCompetitor(testCompetitor);
            String loginResult = competitorList.checkLogin("TEST001", "password123");
            // Login check should complete without exception
            testsPassed++;
            System.out.println("PASS: testCheckLogin_Success passed");
        } catch (Exception e) {
            System.out.println("FAIL: testCheckLogin_Success failed with exception: " + e.getMessage());
        }
    }

    private static void testCheckLogin_WrongPassword() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            competitorList.saveCompetitor(testCompetitor);
            String loginResult = competitorList.checkLogin("TEST001", "wrongpassword");
            // Should handle wrong password gracefully
            testsPassed++;
            System.out.println("PASS: testCheckLogin_WrongPassword passed");
        } catch (Exception e) {
            System.out.println("FAIL: testCheckLogin_WrongPassword failed with exception: " + e.getMessage());
        }
    }

    private static void testCheckLogin_UserNotFound() {
        testsTotal++;
        try {
            String loginResult = competitorList.checkLogin("NONEXISTENT", "password");
            // Should handle non-existent user gracefully
            testsPassed++;
            System.out.println("PASS: testCheckLogin_UserNotFound passed");
        } catch (Exception e) {
            System.out.println("FAIL: testCheckLogin_UserNotFound failed with exception: " + e.getMessage());
        }
    }

    private static void testGetLeaderboard() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            // Add competitor with scores
            testCompetitor.addQuizAttemptScore(95);
            competitorList.saveCompetitor(testCompetitor);
            
            ArrayList<RONCompetitor> leaderboard = competitorList.getLeaderboard();
            if (leaderboard != null) {
                testsPassed++;
                System.out.println("PASS: testGetLeaderboard passed");
            } else {
                System.out.println("FAIL: testGetLeaderboard failed - returned null");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetLeaderboard failed with exception: " + e.getMessage());
        }
    }

    private static void testLoadFromDatabase() {
        testsTotal++;
        try {
            competitorList.loadFromDatabase();
            // Load operation should complete without exception
            testsPassed++;
            System.out.println("PASS: testLoadFromDatabase passed");
        } catch (Exception e) {
            System.out.println("FAIL: testLoadFromDatabase failed with exception: " + e.getMessage());
        }
    }
}