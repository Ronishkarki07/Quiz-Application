package QUIZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for RONCompetitor functionality
 * Tests RONCompetitor class methods using JUnit 5 framework
 */
class RONCompetitorTest {

    private RONCompetitor competitor;
    private Name testName;

    @BeforeEach
    void setUp() {
        testName = new Name("John", "Middle", "Smith");
        competitor = new RONCompetitor("COMP001", testName, "Beginner", "USA", 25, "password123");
    }

    @Test
    @DisplayName("Test RONCompetitor constructor initializes all fields correctly")
    void testConstructor() {
        assertAll("RONCompetitor constructor should initialize all fields correctly",
            () -> assertEquals("COMP001", competitor.getCompetitorID(), "Competitor ID should match"),
            () -> assertEquals(testName, competitor.getCompetitorName(), "Name should match"),
            () -> assertEquals("Beginner", competitor.getCompetitorLevel(), "Level should match"),
            () -> assertEquals("USA", competitor.getCountry(), "Country should match"),
            () -> assertEquals(25, competitor.getAge(), "Age should match"),
            () -> assertNotNull(competitor.getScores(), "Scores array should not be null"),
            () -> assertEquals(5, competitor.getScores().length, "Scores array should have length 5")
        );
    }

    @Test
    @DisplayName("Test adding quiz attempt scores successfully")
    void testAddQuizAttemptScore_Success() {
        assertTrue(competitor.addQuizAttemptScore(85), "First score should be added successfully");
        assertTrue(competitor.addQuizAttemptScore(90), "Second score should be added successfully");
        assertTrue(competitor.addQuizAttemptScore(78), "Third score should be added successfully");
        
        int[] scores = competitor.getScores();
        assertAll("Scores should be stored correctly",
            () -> assertEquals(85, scores[0], "First score should match"),
            () -> assertEquals(90, scores[1], "Second score should match"),
            () -> assertEquals(78, scores[2], "Third score should match")
        );
    }

    @Test
    @DisplayName("Test adding scores when array is full")
    void testAddQuizAttemptScore_ArrayFull() {
        // Fill up the array (5 attempts maximum)
        assertTrue(competitor.addQuizAttemptScore(85), "Should add first score");
        assertTrue(competitor.addQuizAttemptScore(90), "Should add second score");
        assertTrue(competitor.addQuizAttemptScore(78), "Should add third score");
        assertTrue(competitor.addQuizAttemptScore(88), "Should add fourth score");
        assertTrue(competitor.addQuizAttemptScore(95), "Should add fifth score");
        
        // Try to add one more (should fail)
        assertFalse(competitor.addQuizAttemptScore(100), "Should not add sixth score when array is full");
    }

    @Test
    @DisplayName("Test overall score calculation with no quiz attempts")
    void testGetOverallScore_NoScores() {
        assertEquals(0, competitor.getOverallScore(), "Overall score should be 0 when no quiz attempts exist");
    }

    @Test
    @DisplayName("Test overall score calculation with multiple quiz attempts")
    void testGetOverallScore_WithScores() {
        competitor.addQuizAttemptScore(80);
        competitor.addQuizAttemptScore(90);
        competitor.addQuizAttemptScore(70);
        
        // Should calculate average: (80+90+70)/3 = 80
        assertEquals(80, competitor.getOverallScore(), "Overall score should be the average of all attempts");
    }

    @Test
    @DisplayName("Test getting full competitor details")
    void testGetFullDetails() {
        String fullDetails = competitor.getFullDetails();
        
        assertAll("Full details should contain all required information",
            () -> assertNotNull(fullDetails, "Full details should not be null"),
            () -> assertTrue(fullDetails.contains("COMP001"), "Should contain competitor ID"),
            () -> assertTrue(fullDetails.contains("John"), "Should contain first name"),
            () -> assertTrue(fullDetails.contains("Smith"), "Should contain last name")
        );
    }

    @Test
    @DisplayName("Test getting short competitor details")
    void testGetShortDetails() {
        String shortDetails = competitor.getShortDetails();
        
        assertAll("Short details should contain essential information",
            () -> assertNotNull(shortDetails, "Short details should not be null"),
            () -> assertTrue(shortDetails.contains("COMP001"), "Should contain competitor ID")
        );
    }

    @Test
    @DisplayName("Test setting competitor scores array")
    void testSetScores() {
        int[] newScores = {90, 85, 95, 88, 92};
        competitor.setScores(newScores);
        
        int[] retrievedScores = competitor.getScores();
        assertArrayEquals(newScores, retrievedScores, "Set scores should match retrieved scores");
    }
}