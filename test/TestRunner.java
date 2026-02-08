import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * Simple test runner for QuizApp tests
 * Can be run as a main class to execute all tests
 */
public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("QuizApp Test Runner");
        System.out.println("===================");
        
        // Create launcher
        Launcher launcher = LauncherFactory.create();
        
        // Create summary listener
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        
        // Build discovery request to find all test classes
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(
                selectPackage("QUIZ"),
                selectPackage("DatabaseConfig"), 
                selectPackage("GUI.Admin")
            )
            .build();
        
        // Execute tests
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
        
        // Print summary
        TestExecutionSummary summary = listener.getSummary();
        System.out.println("\nTest Execution Summary:");
        System.out.println("======================");
        System.out.println("Tests found: " + summary.getTestsFoundCount());
        System.out.println("Tests started: " + summary.getTestsStartedCount());
        System.out.println("Tests successful: " + summary.getTestsSucceededCount());
        System.out.println("Tests skipped: " + summary.getTestsSkippedCount());
        System.out.println("Tests failed: " + summary.getTestsFailedCount());
        
        if (summary.getTestsFailedCount() > 0) {
            System.out.println("\nFailures:");
            summary.getFailures().forEach(failure -> {
                System.out.println("- " + failure.getTestIdentifier().getDisplayName());
                System.out.println("  " + failure.getException().getMessage());
            });
        }
        
        // Print overall result
        if (summary.getTestsFailedCount() == 0) {
            System.out.println("\n✅ All tests passed!");
        } else {
            System.out.println("\n❌ Some tests failed!");
            System.exit(1);
        }
    }
    
    /**
     * Run basic functionality tests without database dependency
     */
    public static void runBasicTests() {
        System.out.println("Running basic functionality tests...");
        
        try {
            // Test Name functionality
            QUIZ.Name testName = new QUIZ.Name("Test", "User", "Name");
            assert testName.getFullName().contains("Test User Name");
            System.out.println("✓ Name class test passed");
            
            // Test RONCompetitor functionality
            QUIZ.RONCompetitor competitor = new QUIZ.RONCompetitor(
                "TEST001", testName, "Beginner", "USA", 25, "password"
            );
            assert competitor.getCompetitorID().equals("TEST001");
            assert competitor.addQuizAttemptScore(85);
            assert competitor.getOverallScore() == 85.0;
            System.out.println("✓ RONCompetitor class test passed");
            
            // Test Question functionality
            QUIZ.Question question = new QUIZ.Question(
                1, "Test question?", "A", "B", "C", "D", "A", "Easy"
            );
            assert question.isCorrect(0); // Option A should be correct
            assert !question.isCorrect(1); // Option B should be incorrect
            System.out.println("✓ Question class test passed");
            
            System.out.println("\n✅ Basic functionality tests completed successfully!");
            
        } catch (Exception e) {
            System.out.println("❌ Basic tests failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}