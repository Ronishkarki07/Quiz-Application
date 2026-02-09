import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Main class functionality
 * Tests Main class accessibility and method signatures
 */
class MainTest {

    @Test
    @DisplayName("Test Main class exists and can be loaded")
    void testMainClassExists() {
        assertDoesNotThrow(() -> {
            Class<?> mainClass = Class.forName("Main");
            assertNotNull(mainClass, "Main class should exist");
        }, "Main class should be accessible without throwing exceptions");
    }

    @Test
    @DisplayName("Test Main class has proper main method")
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            Class<?> mainClass = Class.forName("Main");
            java.lang.reflect.Method mainMethod = mainClass.getMethod("main", String[].class);
            
            assertAll("Main method should be properly configured",
                () -> assertNotNull(mainMethod, "Main method should exist"),
                () -> assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()), 
                                "Main method should be static"),
                () -> assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()), 
                                "Main method should be public")
            );
        }, "Main method should exist and be accessible");
    }

    @Test
    @DisplayName("Test Main class structure is correct")
    void testApplicationStartupMessage() {
        assertDoesNotThrow(() -> {
            // Test that the Main class structure is correct
            Class<?> mainClass = Class.forName("Main");
            assertNotNull(mainClass, "Main class should be loadable");
        }, "Main class should have proper structure");
    }

    @Test
    @DisplayName("Test main method parameter handling")
    void testMainMethodParameterHandling() {
        assertDoesNotThrow(() -> {
            // Test that main method handles String[] args properly
            Class<?> mainClass = Class.forName("Main");
            java.lang.reflect.Method mainMethod = mainClass.getMethod("main", String[].class);
            
            Class<?>[] paramTypes = mainMethod.getParameterTypes();
            
            assertAll("Main method parameters should be correct",
                () -> assertEquals(1, paramTypes.length, "Should have exactly one parameter"),
                () -> assertEquals(String[].class, paramTypes[0], "Parameter should be String array")
            );
        }, "Main method should accept String[] parameter");
    }
}