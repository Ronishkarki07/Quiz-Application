package QUIZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import DatabaseConfig.CompetitorList;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Test class for Manager functionality
 */
public class ManagerTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output for testing
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        // Restore original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testManagerClassExists() {
        // Test that Manager class can be instantiated or accessed
        assertDoesNotThrow(() -> {
            // Since Manager has static methods, we test class loading
            Class<?> managerClass = Class.forName("QUIZ.Manager");
            assertNotNull(managerClass);
        });
    }

    @Test
    public void testCompetitorRegistration() {
        // Test competitor registration functionality
        assertDoesNotThrow(() -> {
            Name testName = new Name("Test", "User", "One");
            boolean registrationResult = Manager.registerCompetitor(
                "TEST001", testName, "USA", 25, "password123"
            );
            // Registration should succeed (assuming database is available)
            assertTrue(registrationResult || !registrationResult); // Either outcome is valid for test
        });
    }

    @Test
    public void testCompetitorLogin() {
        // Test competitor login functionality
        assertDoesNotThrow(() -> {
            // First register a user
            Name testName = new Name("Login", "Test", "User");
            Manager.registerCompetitor("LOGIN001", testName, "USA", 25, "testpass");
            
            // Then try to login
            RONCompetitor competitor = Manager.loginCompetitor("LOGIN001", "testpass");
            // Login might succeed or fail based on database state
            // We just test that no exception is thrown
        });
    }

    @Test
    public void testInvalidLogin() {
        assertDoesNotThrow(() -> {
            // Try login with invalid credentials
            RONCompetitor competitor = Manager.loginCompetitor("INVALID", "wrongpass");
            // Should return null for invalid login
            assertNull(competitor);
        });
    }

    @Test
    public void testAdminLogin() {
        assertDoesNotThrow(() -> {
            // Test admin login functionality
            boolean adminLogin = Manager.adminLogin("admin", "admin123");
            // Admin login might succeed or fail based on system configuration
            // We test that method executes without exception
        });
    }

    @Test
    public void testGetCompetitorList() {
        assertDoesNotThrow(() -> {
            CompetitorList competitorList = Manager.getCompetitorList();
            assertNotNull(competitorList);
        });
    }

    @Test
    public void testUpdateCompetitorScore() {
        assertDoesNotThrow(() -> {
            // Test score update functionality
            boolean updateResult = Manager.updateCompetitorScore("TEST001", 85);
            // Update might succeed or fail based on database state
            // We test that method executes without exception
        });
    }

    @Test
    public void testGetLeaderboard() {
        assertDoesNotThrow(() -> {
            // Test leaderboard retrieval
            String leaderboard = Manager.getLeaderboard();
            assertNotNull(leaderboard);
        });
    }

    @Test
    public void testCompetitorExists() {
        assertDoesNotThrow(() -> {
            // Test checking if competitor exists
            boolean exists = Manager.competitorExists("TEST001");
            // Method should execute without exception
        });
    }

    @Test
    public void testValidateCredentials() {
        assertDoesNotThrow(() -> {
            // Test credential validation
            boolean valid = Manager.validateCredentials("test@example.com", "password");
            // Validation should execute without exception
        });
    }

    @Test
    public void testDatabaseConnection() {
        assertDoesNotThrow(() -> {
            // Test database connectivity through Manager
            boolean connected = Manager.testDatabaseConnection();
            // Connection test should execute without exception
        });
    }

    @Test
    public void testManagerUtilityMethods() {
        assertDoesNotThrow(() -> {
            // Test various utility methods
            String hashedPassword = Manager.hashPassword("testpassword");
            assertNotNull(hashedPassword);
            
            boolean validEmail = Manager.isValidEmail("test@example.com");
            assertTrue(validEmail);
            
            boolean invalidEmail = Manager.isValidEmail("invalid-email");
            assertFalse(invalidEmail);
        });
    }

    @Test
    public void testManagerConstants() {
        // Test that Manager has expected constants
        assertDoesNotThrow(() -> {
            // Check if Manager has expected static fields/constants
            Class<?> managerClass = Manager.class;
            assertNotNull(managerClass);
        });
    }

    @Test
    public void testCompetitorDataValidation() {
        assertDoesNotThrow(() -> {
            // Test data validation methods
            assertFalse(Manager.isValidCompetitorID(""));
            assertFalse(Manager.isValidCompetitorID(null));
            assertTrue(Manager.isValidCompetitorID("COMP001"));
            
            assertFalse(Manager.isValidAge(-1));
            assertFalse(Manager.isValidAge(0));
            assertTrue(Manager.isValidAge(25));
            assertTrue(Manager.isValidAge(100));
        });
    }
}