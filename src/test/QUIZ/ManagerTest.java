package test.QUIZ;

import QUIZ.Manager;
import QUIZ.Name;
import QUIZ.RONCompetitor;
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
    public void testManagerMainMethodExists() {
        // Test that main method exists with correct signature
        assertDoesNotThrow(() -> {
            Class<?> managerClass = Class.forName("QUIZ.Manager");
            java.lang.reflect.Method mainMethod = managerClass.getMethod("main", String[].class);
            assertNotNull(mainMethod);
            
            // Verify it's static and public
            assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
        });
    }
}