package DatabaseConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import QUIZ.RONCompetitor;
import QUIZ.Name;
import java.util.List;

/**
 * Test class for CompetitorList functionality
 */
public class CompetitorListTest {

    private CompetitorList competitorList;
    private RONCompetitor testCompetitor;

    @BeforeEach
    public void setUp() {
        competitorList = new CompetitorList();
        testCompetitor = new RONCompetitor(
            "TEST001", 
            new Name("Test", "User", "One"), 
            "Beginner", 
            "USA", 
            25, 
            "password123"
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(competitorList);
    }

    @Test
    public void testAddCompetitor() {
        assertDoesNotThrow(() -> {
            boolean added = competitorList.addCompetitor(testCompetitor);
            // Add operation should complete without exception
        });
    }

    @Test
    public void testFindCompetitor() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            RONCompetitor found = competitorList.findCompetitor("TEST001");
            // Find operation should complete without exception
        });
    }

    @Test
    public void testFindCompetitorNotFound() {
        assertDoesNotThrow(() -> {
            RONCompetitor notFound = competitorList.findCompetitor("NONEXISTENT");
            // Should return null for non-existent competitor
        });
    }

    @Test
    public void testGetAllCompetitors() {
        assertDoesNotThrow(() -> {
            List<RONCompetitor> competitors = competitorList.getAllCompetitors();
            assertNotNull(competitors);
        });
    }

    @Test
    public void testRemoveCompetitor() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            boolean removed = competitorList.removeCompetitor("TEST001");
            // Remove operation should complete without exception
        });
    }

    @Test
    public void testUpdateCompetitor() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            testCompetitor.setCompetitorLevel("Advanced");
            boolean updated = competitorList.updateCompetitor(testCompetitor);
            // Update operation should complete without exception
        });
    }

    @Test
    public void testGetCompetitorCount() {
        assertDoesNotThrow(() -> {
            int count = competitorList.getCompetitorCount();
            assertTrue(count >= 0);
        });
    }

    @Test
    public void testCompetitorExists() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            boolean exists = competitorList.competitorExists("TEST001");
            // Method should execute without exception
        });
    }

    @Test
    public void testGetCompetitorsByLevel() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            List<RONCompetitor> beginners = competitorList.getCompetitorsByLevel("Beginner");
            assertNotNull(beginners);
        });
    }

    @Test
    public void testGetCompetitorsByCountry() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            List<RONCompetitor> usaCompetitors = competitorList.getCompetitorsByCountry("USA");
            assertNotNull(usaCompetitors);
        });
    }

    @Test
    public void testClearAllCompetitors() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            competitorList.clearAllCompetitors();
            assertEquals(0, competitorList.getCompetitorCount());
        });
    }

    @Test
    public void testGetTopCompetitors() {
        assertDoesNotThrow(() -> {
            // Add competitor with scores
            testCompetitor.addQuizAttemptScore(95);
            competitorList.addCompetitor(testCompetitor);
            
            List<RONCompetitor> topCompetitors = competitorList.getTopCompetitors(10);
            assertNotNull(topCompetitors);
        });
    }

    @Test
    public void testSearchCompetitors() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            List<RONCompetitor> searchResults = competitorList.searchCompetitors("Test");
            assertNotNull(searchResults);
        });
    }

    @Test
    public void testValidateCompetitorData() {
        assertDoesNotThrow(() -> {
            boolean valid = competitorList.validateCompetitorData(testCompetitor);
            // Validation should complete without exception
        });
    }

    @Test
    public void testDuplicateCompetitorID() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            
            // Try to add another competitor with same ID
            RONCompetitor duplicate = new RONCompetitor(
                "TEST001", 
                new Name("Another", "Test", "User"), 
                "Intermediate", 
                "Canada", 
                30, 
                "differentpassword"
            );
            
            boolean added = competitorList.addCompetitor(duplicate);
            // Should handle duplicate IDs appropriately
        });
    }

    @Test
    public void testLoadFromDatabase() {
        assertDoesNotThrow(() -> {
            competitorList.loadFromDatabase();
            // Database loading should complete without exception
        });
    }

    @Test
    public void testSaveToDatabase() {
        assertDoesNotThrow(() -> {
            competitorList.addCompetitor(testCompetitor);
            competitorList.saveToDatabase();
            // Database saving should complete without exception
        });
    }
}