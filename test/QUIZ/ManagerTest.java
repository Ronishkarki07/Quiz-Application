package QUIZ;

import QUIZ.Manager;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test class for Manager functionality (standalone test without JUnit)
 * Run this class directly to test Manager methods
 */
public class ManagerTest {

    private static final PrintStream originalOut = System.out;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static int testsPassed = 0;
    private static int testsTotal = 0;

    public static void main(String[] args) {
        System.out.println("=== Running Manager Tests ===");
        
        testManagerClassExists();
        testManagerMainMethodExists();
        
        // Restore output
        System.setOut(originalOut);
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + testsPassed + "/" + testsTotal);
        
        if (testsPassed == testsTotal) {
            System.out.println("PASS: All tests passed!");
        } else {
            System.out.println("FAIL: Some tests failed.");
        }
    }

    private static void testManagerClassExists() {
        testsTotal++;
        try {
            // Since Manager has static methods, we test class loading
            Class<?> managerClass = Class.forName("QUIZ.Manager");
            if (managerClass != null) {
                testsPassed++;
                System.out.println("PASS: testManagerClassExists passed");
            } else {
                System.out.println("FAIL: testManagerClassExists failed - Manager class not found");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testManagerClassExists failed with exception: " + e.getMessage());
        }
    }

    private static void testManagerMainMethodExists() {
        testsTotal++;
        try {
            Class<?> managerClass = Class.forName("QUIZ.Manager");
            java.lang.reflect.Method mainMethod = managerClass.getMethod("main", String[].class);
            
            if (mainMethod != null && 
                java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()) && 
                java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers())) {
                testsPassed++;
                System.out.println("PASS: testManagerMainMethodExists passed");
            } else {
                System.out.println("FAIL: testManagerMainMethodExists failed - main method not properly configured");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testManagerMainMethodExists failed with exception: " + e.getMessage());
        }
    }
}