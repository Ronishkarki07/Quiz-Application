package QUIZ;

import QUIZ.Name;

/**
 * Test class for Name functionality (standalone test without JUnit)
 * Run this class directly to test Name methods
 */
public class NameTest {
    
    private static Name name;
    private static int testsPassed = 0;
    private static int testsTotal = 0;

    public static void main(String[] args) {
        System.out.println("=== Running Name Tests ===");
        
        setUp();
        
        testConstructor_AllParameters();
        testConstructor_NoMiddleName();
        testGetFullName();
        testGetFullName_NoMiddleName();
        testGetInitials();
        testSetFirstName();
        testSetMiddleName();
        testSetLastName();
        testNullAndEmptyValues();
        
        System.out.println("\n=== Test Results ===");
        System.out.println("Passed: " + testsPassed + "/" + testsTotal);
        
        if (testsPassed == testsTotal) {
            System.out.println("PASS: All tests passed!");
        } else {
            System.out.println("FAIL: Some tests failed.");
        }
    }

    private static void setUp() {
        name = new Name("John", "Michael", "Smith");
    }

    private static void testConstructor_AllParameters() {
        testsTotal++;
        try {
            boolean passed = "John".equals(name.getFirstName()) &&
                           "Michael".equals(name.getMiddleName()) &&
                           "Smith".equals(name.getLastName());
            
            if (passed) {
                testsPassed++;
                System.out.println("PASS: testConstructor_AllParameters passed");
            } else {
                System.out.println("FAIL: testConstructor_AllParameters failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testConstructor_AllParameters failed with exception: " + e.getMessage());
        }
    }

    private static void testConstructor_NoMiddleName() {
        testsTotal++;
        try {
            Name nameNoMiddle = new Name("Jane", "", "Doe");
            
            boolean passed = "Jane".equals(nameNoMiddle.getFirstName()) &&
                           "".equals(nameNoMiddle.getMiddleName()) &&
                           "Doe".equals(nameNoMiddle.getLastName());
            
            if (passed) {
                testsPassed++;
                System.out.println("PASS: testConstructor_NoMiddleName passed");
            } else {
                System.out.println("FAIL: testConstructor_NoMiddleName failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testConstructor_NoMiddleName failed with exception: " + e.getMessage());
        }
    }

    private static void testGetFullName() {
        testsTotal++;
        try {
            String fullName = name.getFullName();
            boolean passed = fullName != null &&
                           fullName.contains("John") &&
                           fullName.contains("Michael") &&
                           fullName.contains("Smith") &&
                           "John Michael Smith".equals(fullName);
            
            if (passed) {
                testsPassed++;
                System.out.println("PASS: testGetFullName passed");
            } else {
                System.out.println("FAIL: testGetFullName failed - got: " + fullName);
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetFullName failed with exception: " + e.getMessage());
        }
    }

    private static void testGetFullName_NoMiddleName() {
        testsTotal++;
        try {
            Name nameNoMiddle = new Name("Jane", "", "Doe");
            String fullName = nameNoMiddle.getFullName();
            
            if ("Jane Doe".equals(fullName.trim())) {
                testsPassed++;
                System.out.println("PASS: testGetFullName_NoMiddleName passed");
            } else {
                System.out.println("FAIL: testGetFullName_NoMiddleName failed - got: " + fullName);
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetFullName_NoMiddleName failed with exception: " + e.getMessage());
        }
    }

    private static void testGetInitials() {
        testsTotal++;
        try {
            String initials = name.getInitials();
            boolean passed = initials != null &&
                           initials.contains("J") &&
                           initials.contains("M") &&
                           initials.contains("S");
            
            if (passed) {
                testsPassed++;
                System.out.println("PASS: testGetInitials passed");
            } else {
                System.out.println("FAIL: testGetInitials failed - got: " + initials);
            }
        } catch (Exception e) {
            System.out.println("FAIL: testGetInitials failed with exception: " + e.getMessage());
        }
    }

    private static void testSetFirstName() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            name.setFirstName("Robert");
            
            if ("Robert".equals(name.getFirstName())) {
                testsPassed++;
                System.out.println("PASS: testSetFirstName passed");
            } else {
                System.out.println("FAIL: testSetFirstName failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testSetFirstName failed with exception: " + e.getMessage());
        }
    }

    private static void testSetMiddleName() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            name.setMiddleName("James");
            
            if ("James".equals(name.getMiddleName())) {
                testsPassed++;
                System.out.println("PASS: testSetMiddleName passed");
            } else {
                System.out.println("FAIL: testSetMiddleName failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testSetMiddleName failed with exception: " + e.getMessage());
        }
    }

    private static void testSetLastName() {
        testsTotal++;
        try {
            setUp(); // Reset for clean test
            name.setLastName("Johnson");
            
            if ("Johnson".equals(name.getLastName())) {
                testsPassed++;
                System.out.println("PASS: testSetLastName passed");
            } else {
                System.out.println("FAIL: testSetLastName failed");
            }
        } catch (Exception e) {
            System.out.println("FAIL: testSetLastName failed with exception: " + e.getMessage());
        }
    }

    private static void testNullAndEmptyValues() {
        testsTotal++;
        try {
            // Test with null values (should handle gracefully)
            Name nullName = new Name(null, null, null);
            
            // Test with empty strings
            Name emptyName = new Name("", "", "");
            
            // If we got here without exceptions, test passed
            testsPassed++;
            System.out.println("PASS: testNullAndEmptyValues passed");
        } catch (Exception e) {
            System.out.println("FAIL: testNullAndEmptyValues failed with exception: " + e.getMessage());
        }
    }
}