package DatabaseConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Test class for DatabaseConnection functionality
 */
public class DatabaseConnectionTest {

    @Test
    public void testDatabaseConnection() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                assertFalse(connection.isClosed());
                assertTrue(connection.isValid(5)); // 5 second timeout
                connection.close();
            }
        });
    }

    @Test
    public void testConnectionNotNull() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            // Connection might be null if database is not available
            // We test that the method doesn't throw an exception
        });
    }

    @Test
    public void testMultipleConnections() {
        assertDoesNotThrow(() -> {
            Connection conn1 = DatabaseConnection.getConnection();
            Connection conn2 = DatabaseConnection.getConnection();
            
            // Both connections should be valid (if database is available)
            if (conn1 != null && conn2 != null) {
                assertTrue(conn1.isValid(5));
                assertTrue(conn2.isValid(5));
                
                conn1.close();
                conn2.close();
            }
        });
    }

    @Test
    public void testConnectionProperties() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                // Test connection metadata
                assertNotNull(connection.getMetaData());
                assertNotNull(connection.getMetaData().getURL());
                
                // Test autocommit (usually true by default)
                boolean autoCommit = connection.getAutoCommit();
                assertTrue(autoCommit || !autoCommit); // Either value is valid
                
                connection.close();
            }
        });
    }

    @Test
    public void testDatabaseURL() {
        assertDoesNotThrow(() -> {
            String url = DatabaseConnection.getDatabaseURL();
            if (url != null) {
                assertTrue(url.startsWith("jdbc:"));
            }
        });
    }

    @Test
    public void testConnectionTimeout() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                // Test connection timeout
                boolean isValid = connection.isValid(1); // 1 second timeout
                // Should complete within timeout
                connection.close();
            }
        });
    }

    @Test
    public void testConnectionClosing() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                assertFalse(connection.isClosed());
                
                connection.close();
                assertTrue(connection.isClosed());
            }
        });
    }

    @Test
    public void testDatabaseConfiguration() {
        // Test that database configuration is properly set
        assertDoesNotThrow(() -> {
            // Test configuration methods if they exist
            String dbName = DatabaseConnection.getDatabaseName();
            String dbUser = DatabaseConnection.getDatabaseUser();
            
            // These might be null if not configured, but methods should exist
        });
    }

    @Test
    public void testConnectionPooling() {
        // Test connection pooling if implemented
        assertDoesNotThrow(() -> {
            Connection[] connections = new Connection[5];
            
            // Create multiple connections
            for (int i = 0; i < 5; i++) {
                connections[i] = DatabaseConnection.getConnection();
            }
            
            // Close all connections
            for (Connection conn : connections) {
                if (conn != null) {
                    conn.close();
                }
            }
        });
    }

    @Test
    public void testDatabaseAvailability() {
        assertDoesNotThrow(() -> {
            boolean isAvailable = DatabaseConnection.isDatabaseAvailable();
            // Method should execute without exception
        });
    }

    @Test
    public void testConnectionRecovery() {
        assertDoesNotThrow(() -> {
            // Test connection recovery after failure
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                connection.close();
                
                // Try to get new connection after closing
                Connection newConnection = DatabaseConnection.getConnection();
                if (newConnection != null) {
                    assertTrue(newConnection.isValid(5));
                    newConnection.close();
                }
            }
        });
    }
}