package QUIZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Name functionality
 * Tests Name class methods using JUnit 5 framework
 */
public class NameTest {
    
    private Name name;

    @BeforeEach
    void setUp() {
        name = new Name("John", "Michael", "Smith");
    }

    @Test
    @DisplayName("Test Name constructor with all parameters")
    void testConstructor_AllParameters() {
        assertAll("Constructor should initialize all name components correctly",
            () -> assertEquals("John", name.getFirstName(), "First name should match constructor parameter"),
            () -> assertEquals("Michael", name.getMiddleName(), "Middle name should match constructor parameter"),
            () -> assertEquals("Smith", name.getLastName(), "Last name should match constructor parameter")
        );
    }

    @Test
    @DisplayName("Test Name constructor without middle name")
    void testConstructor_NoMiddleName() {
        Name nameNoMiddle = new Name("Jane", "", "Doe");
        
        assertAll("Constructor should handle empty middle name correctly",
            () -> assertEquals("Jane", nameNoMiddle.getFirstName(), "First name should match"),
            () -> assertEquals("", nameNoMiddle.getMiddleName(), "Middle name should be empty string"),
            () -> assertEquals("Doe", nameNoMiddle.getLastName(), "Last name should match")
        );
    }

    @Test
    @DisplayName("Test full name generation with all components")
    void testGetFullName() {
        String fullName = name.getFullName();
        
        assertAll("Full name should contain all name components",
            () -> assertNotNull(fullName, "Full name should not be null"),
            () -> assertTrue(fullName.contains("John"), "Full name should contain first name"),
            () -> assertTrue(fullName.contains("Michael"), "Full name should contain middle name"),
            () -> assertTrue(fullName.contains("Smith"), "Full name should contain last name"),
            () -> assertEquals("John Michael Smith", fullName, "Full name should be properly formatted")
        );
    }

    @Test
    @DisplayName("Test full name generation without middle name")
    void testGetFullName_NoMiddleName() {
        Name nameNoMiddle = new Name("Jane", "", "Doe");
        String fullName = nameNoMiddle.getFullName();
        
        assertEquals("Jane Doe", fullName.trim(), "Full name should handle empty middle name correctly");
    }

    @Test
    @DisplayName("Test initials generation")
    void testGetInitials() {
        String initials = name.getInitials();
        
        assertAll("Initials should contain first letters of all name components",
            () -> assertNotNull(initials, "Initials should not be null"),
            () -> assertTrue(initials.contains("J"), "Initials should contain J from John"),
            () -> assertTrue(initials.contains("M"), "Initials should contain M from Michael"),
            () -> assertTrue(initials.contains("S"), "Initials should contain S from Smith")
        );
    }

    @Test
    @DisplayName("Test setting first name")
    void testSetFirstName() {
        name.setFirstName("Robert");
        assertEquals("Robert", name.getFirstName(), "First name should be updated via setter");
    }

    @Test
    @DisplayName("Test setting middle name")
    void testSetMiddleName() {
        name.setMiddleName("James");
        assertEquals("James", name.getMiddleName(), "Middle name should be updated via setter");
    }

    @Test
    @DisplayName("Test setting last name")
    void testSetLastName() {
        name.setLastName("Johnson");
        assertEquals("Johnson", name.getLastName(), "Last name should be updated via setter");
    }

    @Test
    @DisplayName("Test null and empty value handling")
    void testNullAndEmptyValues() {
        assertAll("Should handle null and empty values gracefully",
            () -> assertDoesNotThrow(() -> new Name(null, null, null), 
                                   "Constructor should handle null values without throwing exceptions"),
            () -> assertDoesNotThrow(() -> new Name("", "", ""), 
                                   "Constructor should handle empty strings without throwing exceptions")
        );
        
        // Test with empty strings
        Name emptyName = new Name("", "", "");
        assertAll("Empty name should be properly initialized",
            () -> assertEquals("", emptyName.getFirstName(), "First name should be empty string"),
            () -> assertEquals("", emptyName.getMiddleName(), "Middle name should be empty string"),
            () -> assertEquals("", emptyName.getLastName(), "Last name should be empty string")
        );
    }
}