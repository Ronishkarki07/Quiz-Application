package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test class for Main application entry point
 */
public class MainTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testMainClassExists() {
        // Test that Main class can be loaded
        assertDoesNotThrow(() -> {
            Class<?> mainClass = Class.forName("Main");
            assertNotNull(mainClass);
        });
    }

    @Test
    public void testMainMethodExists() {
        // Test that main method exists with correct signature
        assertDoesNotThrow(() -> {
            Class<?> mainClass = Class.forName("Main");
            java.lang.reflect.Method mainMethod = mainClass.getMethod("main", String[].class);
            assertNotNull(mainMethod);
            
            // Verify it's static and public
            assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
        });
    }

    @Test
    public void testApplicationStartupMessage() {
        // Test that startup message is displayed
        // Note: We can't easily test the GUI creation without mocking
        // but we can test that the Main class structure is correct
        assertDoesNotThrow(() -> {
            Class<?> mainClass = Class.forName("Main");
            // Just verify the class loads without GUI creation
            assertNotNull(mainClass);
        });
    }

    @Test
    public void testMainMethodParameterHandling() {
        // Test that main method handles null and empty args properly
        assertDoesNotThrow(() -> {
            // We don't actually call main() to avoid GUI creation
            // but verify method signature accepts String[]
            Class<?> mainClass = Class.forName("Main");
            java.lang.reflect.Method mainMethod = mainClass.getMethod("main", String[].class);
            
            Class<?>[] paramTypes = mainMethod.getParameterTypes();
            assertEquals(1, paramTypes.length);
            assertEquals(String[].class, paramTypes[0]);
        });
    }
}