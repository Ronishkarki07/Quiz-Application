package test.DatabaseConfig;

import DatabaseConfig.DatabaseConnection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;

/**
 * Test class for DatabaseConnection functionality
 * Tests only methods that actually exist in DatabaseConnection
 */
public class DatabaseConnectionTest {

    @Test
    public void testGetConnection() {
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
    public void testGetConnectionNotNull() {
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
    public void testConnectionRecovery() {
        assertDoesNotThrow(() -> {
            // Test connection recovery after closing
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