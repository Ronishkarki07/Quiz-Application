package QUIZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RONCompetitor functionality
 */
public class RONCompetitorTest {

    private RONCompetitor competitor;
    private Name testName;

    @BeforeEach
    public void setUp() {
        testName = new Name("John", "Middle", "Smith");
        competitor = new RONCompetitor("COMP001", testName, "Beginner", "USA", 25, "password123");
    }

    @Test
    public void testConstructor() {
        assertEquals("COMP001", competitor.getCompetitorID());
        assertEquals(testName, competitor.getCompetitorName());
        assertEquals("Beginner", competitor.getCompetitorLevel());
        assertEquals("USA", competitor.getCountry());
        assertEquals(25, competitor.getAge());
        assertNotNull(competitor.getScores());
        assertEquals(5, competitor.getScores().length);
    }

    @Test
    public void testAddQuizAttemptScore_Success() {
        assertTrue(competitor.addQuizAttemptScore(85));
        assertTrue(competitor.addQuizAttemptScore(90));
        assertTrue(competitor.addQuizAttemptScore(78));
        
        int[] scores = competitor.getScores();
        assertEquals(85, scores[0]);
        assertEquals(90, scores[1]);
        assertEquals(78, scores[2]);
        assertEquals(0, scores[3]); // Still empty
        assertEquals(0, scores[4]); // Still empty
    }

    @Test
    public void testAddQuizAttemptScore_MaxAttemptsReached() {
        // Fill all 5 attempts
        for (int i = 0; i < 5; i++) {
            assertTrue(competitor.addQuizAttemptScore(80 + i));
        }
        
        // Trying to add 6th attempt should fail
        assertFalse(competitor.addQuizAttemptScore(100));
    }

    @Test
    public void testGetOverallScore_NoAttempts() {
        assertEquals(0.0, competitor.getOverallScore());
    }

    @Test
    public void testGetOverallScore_WithAttempts() {
        competitor.addQuizAttemptScore(80);
        competitor.addQuizAttemptScore(90);
        competitor.addQuizAttemptScore(70);
        
        double expectedAverage = (80.0 + 90.0 + 70.0) / 3.0;
        assertEquals(expectedAverage, competitor.getOverallScore(), 0.01);
    }

    @Test
    public void testSetCompetitorLevel() {
        competitor.setCompetitorLevel("Advanced");
        assertEquals("Advanced", competitor.getCompetitorLevel());
    }

    @Test
    public void testGetFullDetails() {
        String details = competitor.getFullDetails();
        assertNotNull(details);
        assertTrue(details.contains("COMP001"));
        assertTrue(details.contains("John"));
        assertTrue(details.contains("USA"));
    }

    @Test
    public void testGetShortDetails() {
        String shortDetails = competitor.getShortDetails();
        assertNotNull(shortDetails);
        assertTrue(shortDetails.length() < competitor.getFullDetails().length());
    }

    @Test
    public void testSetScores() {
        int[] newScores = {95, 88, 92, 0, 0};
        competitor.setScores(newScores);
        
        assertArrayEquals(newScores, competitor.getScores());
    }

    @Test
    public void testPasswordValidation() {
        assertTrue(competitor.isPasswordValid("password123"));
        assertFalse(competitor.isPasswordValid("wrongpassword"));
        assertFalse(competitor.isPasswordValid(""));
        assertFalse(competitor.isPasswordValid(null));
    }

    @Test
    public void testAgeValidation() {
        assertEquals(25, competitor.getAge());
        
        // Test age setter if it exists
        competitor.setAge(30);
        assertEquals(30, competitor.getAge());
    }

    @Test  
    public void testCountryProperty() {
        assertEquals("USA", competitor.getCountry());
        
        competitor.setCountry("Canada");
        assertEquals("Canada", competitor.getCountry());
    }
}