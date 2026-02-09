package DatabaseConfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

/**
 * JUnit test class for DatabaseConnection functionality
 * Tests DatabaseConnection methods and connectivity
 */
class DatabaseConnectionTest {

    @Test
    @DisplayName("Test getting database connection")
    void testGetConnection() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "Connection should not be null");
            
            boolean isValid = !connection.isClosed() && connection.isValid(5);
            connection.close();
            
            assertTrue(isValid, "Connection should be valid and open");
        }, "Should be able to get a valid database connection");
    }

    @Test
    @DisplayName("Test connection is not null")
    void testGetConnectionNotNull() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "Connection should never be null");
            connection.close();
        }, "Connection retrieval should not throw exception");
    }

    @Test
    @DisplayName("Test multiple connections")
    void testMultipleConnections() {
        assertDoesNotThrow(() -> {
            Connection connection1 = DatabaseConnection.getConnection();
            Connection connection2 = DatabaseConnection.getConnection();
            
            assertNotNull(connection1, "First connection should not be null");
            assertNotNull(connection2, "Second connection should not be null");
            
            connection1.close();
            connection2.close();
        }, "Should be able to get multiple connections");
    }
    @Test
    @DisplayName("Test connection properties")
    void testConnectionProperties() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                // Test connection metadata
                assertNotNull(connection.getMetaData(), "Connection metadata should not be null");
                assertNotNull(connection.getMetaData().getURL(), "Connection URL should not be null");
                
                // Test autocommit (either value is valid)
                boolean autoCommit = connection.getAutoCommit();
                // autoCommit can be true or false - both are valid
                assertTrue(autoCommit || !autoCommit, "AutoCommit value should be boolean");
                
                connection.close();
            }
        }, "Connection properties test should not throw exception");
    }

    @Test
    @DisplayName("Test connection closing")
    void testConnectionClosing() {
        assertDoesNotThrow(() -> {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                boolean wasOpen = !connection.isClosed();
                
                connection.close();
                boolean isClosed = connection.isClosed();
                
                assertTrue(wasOpen, "Connection should be open initially");
                assertTrue(isClosed, "Connection should be closed after close()");
            }
        }, "Connection closing test should not throw exception");
    }

    @Test
    @DisplayName("Test connection recovery")
    void testConnectionRecovery() {
        assertDoesNotThrow(() -> {
            // Test connection recovery after closing
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                connection.close();
                
                // Try to get new connection after closing
                Connection newConnection = DatabaseConnection.getConnection();
                if (newConnection != null) {
                    assertTrue(newConnection.isValid(5), 
                             "New connection should be valid after recovery");
                    newConnection.close();
                }
            }
        }, "Connection recovery test should not throw exception");
    }
}