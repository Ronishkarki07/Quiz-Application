package QUIZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Name functionality
 */
public class NameTest {

    private Name name;

    @BeforeEach
    public void setUp() {
        name = new Name("John", "Michael", "Smith");
    }

    @Test
    public void testConstructor_AllParameters() {
        assertEquals("John", name.getFirstName());
        assertEquals("Michael", name.getMiddleName());
        assertEquals("Smith", name.getLastName());
    }

    @Test
    public void testConstructor_NoMiddleName() {
        Name nameNoMiddle = new Name("Jane", "", "Doe");
        assertEquals("Jane", nameNoMiddle.getFirstName());
        assertEquals("", nameNoMiddle.getMiddleName());
        assertEquals("Doe", nameNoMiddle.getLastName());
    }

    @Test
    public void testGetFullName() {
        String fullName = name.getFullName();
        assertTrue(fullName.contains("John"));
        assertTrue(fullName.contains("Michael"));
        assertTrue(fullName.contains("Smith"));
        assertEquals("John Michael Smith", fullName);
    }

    @Test
    public void testGetFullName_NoMiddleName() {
        Name nameNoMiddle = new Name("Jane", "", "Doe");
        assertEquals("Jane Doe", nameNoMiddle.getFullName().trim());
    }

    @Test
    public void testGetInitials() {
        String initials = name.getInitials();
        assertTrue(initials.contains("J"));
        assertTrue(initials.contains("M"));
        assertTrue(initials.contains("S"));
    }

    @Test
    public void testSetFirstName() {
        name.setFirstName("Robert");
        assertEquals("Robert", name.getFirstName());
    }

    @Test
    public void testSetMiddleName() {
        name.setMiddleName("James");
        assertEquals("James", name.getMiddleName());
    }

    @Test
    public void testSetLastName() {
        name.setLastName("Johnson");
        assertEquals("Johnson", name.getLastName());
    }

    @Test
    public void testToString() {
        String nameString = name.toString();
        assertNotNull(nameString);
        assertTrue(nameString.contains("John"));
    }

    @Test
    public void testEquals() {
        Name name2 = new Name("John", "Michael", "Smith");
        assertTrue(name.equals(name2));
        
        Name name3 = new Name("Jane", "Michael", "Smith");
        assertFalse(name.equals(name3));
    }

    @Test
    public void testNullAndEmptyValues() {
        // Test with null values (should handle gracefully)
        assertDoesNotThrow(() -> {
            Name nullName = new Name(null, null, null);
        });
        
        // Test with empty strings
        assertDoesNotThrow(() -> {
            Name emptyName = new Name("", "", "");
        });
    }
}