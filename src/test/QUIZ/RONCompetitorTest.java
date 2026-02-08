package test.QUIZ;

import QUIZ.Name;
import QUIZ.RONCompetitor;
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
    }

    @Test
    public void testAddQuizAttemptScore_ArrayFull() {
        // Fill up the array
        assertTrue(competitor.addQuizAttemptScore(85));
        assertTrue(competitor.addQuizAttemptScore(90));
        assertTrue(competitor.addQuizAttemptScore(78));
        assertTrue(competitor.addQuizAttemptScore(88));
        assertTrue(competitor.addQuizAttemptScore(95));
        
        // Try to add one more (should fail)
        assertFalse(competitor.addQuizAttemptScore(100));
    }

    @Test
    public void testGetOverallScore_NoScores() {
        // New competitor should have overall score of 0
        assertEquals(0, competitor.getOverallScore());
    }

    @Test
    public void testGetOverallScore_WithScores() {
        competitor.addQuizAttemptScore(80);
        competitor.addQuizAttemptScore(90);
        competitor.addQuizAttemptScore(70);
        
        // Should calculate average: (80+90+70)/3 = 80
        assertEquals(80, competitor.getOverallScore());
    }

    @Test
    public void testGetFullDetails() {
        String fullDetails = competitor.getFullDetails();
        assertNotNull(fullDetails);
        assertTrue(fullDetails.contains("COMP001"));
        assertTrue(fullDetails.contains("John"));
        assertTrue(fullDetails.contains("Smith"));
    }

    @Test
    public void testGetShortDetails() {
        String shortDetails = competitor.getShortDetails();
        assertNotNull(shortDetails);
        assertTrue(shortDetails.contains("COMP001"));
    }

    @Test
    public void testSetScores() {
        int[] newScores = {90, 85, 95, 88, 92};
        competitor.setScores(newScores);
        
        int[] retrievedScores = competitor.getScores();
        assertArrayEquals(newScores, retrievedScores);
    }
}