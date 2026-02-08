import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Test Suite for QuizApp - runs all test classes
 */
@Suite
@SelectClasses({
    // Core QUIZ package tests
    QUIZ.RONCompetitorTest.class,
    QUIZ.NameTest.class,
    QUIZ.QuestionTest.class,
    QUIZ.ManagerTest.class,
    
    // Database configuration tests
    DatabaseConfig.DatabaseConnectionTest.class,
    DatabaseConfig.CompetitorListTest.class,
    
    // GUI Admin tests  
    GUI.Admin.QuestionBankTest.class
})
public class AllTestsSuite {
    // Test suite class - no additional code needed
    // JUnit 5 will automatically run all selected test classes
}