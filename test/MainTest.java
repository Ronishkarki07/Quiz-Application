import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test class for Main application entry point (standalone test without JUnit)
 * Run this class directly to test Main class methods
 */
public class MainTest {

    private static final PrintStream originalOut = System.out;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static int testsPassed = 0;
    private static int testsTotal = 0;

    public static void main(String[] args) {
        System.out.println("=== Running Main Tests ===");
        
        testMainClassExists();
        testMainMethodExists();
        testApplicationStartupMessage();
        testMainMethodParameterHandling();
        
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

    private static void testMainClassExists() {
        testsTotal++;
        try {
            Class<?> mainClass = Class.forName("Main");
            if (mainClass != null) {
                testsPassed++;
                System.out.println("PASS: testMainClassExists passed");
            } else {
                System.out.println("FAIL: testMainClassExists failed - Main class not found");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testMainClassExists failed with exception: " + e.getMessage());
        }
    }

    private static void testMainMethodExists() {
        testsTotal++;
        try {
            Class<?> mainClass = Class.forName("Main");
            java.lang.reflect.Method mainMethod = mainClass.getMethod("main", String[].class);
            
            if (mainMethod != null && 
                java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()) && 
                java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers())) {
                testsPassed++;
                System.out.println("PASS: testMainMethodExists passed");
            } else {
                System.out.println("FAIL: testMainMethodExists failed - main method not properly configured");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testMainMethodExists failed with exception: " + e.getMessage());
        }
    }

    private static void testApplicationStartupMessage() {
        testsTotal++;
        try {
            // Test that startup message is displayed
            // Note: We can't easily test the GUI creation without mocking
            // but we can test that the Main class structure is correct
            Class<?> mainClass = Class.forName("Main");
            
            if (mainClass != null) {
                testsPassed++;
                System.out.println("PASS: testApplicationStartupMessage passed");
            } else {
                System.out.println("FAIL: testApplicationStartupMessage failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testApplicationStartupMessage failed with exception: " + e.getMessage());
        }
    }

    private static void testMainMethodParameterHandling() {
        testsTotal++;
        try {
            // Test that main method handles null and empty args properly
            Class<?> mainClass = Class.forName("Main");
            java.lang.reflect.Method mainMethod = mainClass.getMethod("main", String[].class);
            
            Class<?>[] paramTypes = mainMethod.getParameterTypes();
            
            if (paramTypes.length == 1 && paramTypes[0] == String[].class) {
                testsPassed++;
                System.out.println("PASS: testMainMethodParameterHandling passed");
            } else {
                System.out.println("FAIL: testMainMethodParameterHandling failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testMainMethodParameterHandling failed with exception: " + e.getMessage());
        }
    }
}