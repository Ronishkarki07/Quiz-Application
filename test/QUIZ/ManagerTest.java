package QUIZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Manager functionality
 * Tests Manager class methods and structure
 */
class ManagerTest {

    @BeforeEach
    void setUp() {
        // Reset output stream for each test if needed
    }

    @Test
    @DisplayName("Test Manager class exists and can be loaded")
    void testManagerClassExists() {
        assertDoesNotThrow(() -> {
            Class<?> managerClass = Class.forName("QUIZ.Manager");
            assertNotNull(managerClass, "Manager class should exist and be loadable");
        }, "Manager class should be found and loadable");
    }

    @Test
    @DisplayName("Test Manager main method exists")
    void testManagerMainMethodExists() {
        assertDoesNotThrow(() -> {
            Class<?> managerClass = Class.forName("QUIZ.Manager");
            java.lang.reflect.Method mainMethod = managerClass.getMethod("main", String[].class);
            assertNotNull(mainMethod, "Manager class should have a main method");
            
            // Check method modifiers (should be public static)
            assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()), 
                      "Main method should be public");
            assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()), 
                      "Main method should be static");
        }, "Manager main method should exist and be accessible");
    }
}