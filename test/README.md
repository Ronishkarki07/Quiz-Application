# QuizApp Test Suite

This directory contains comprehensive test cases for the QuizApp project.

## Test Structure

```
test/
├── AllTestsSuite.java          # Main test suite that runs all tests
├── QuizAppIntegrationTest.java # Integration tests for component interaction
├── DatabaseConfig/             # Database-related tests
│   ├── DatabaseConnectionTest.java
│   └── CompetitorListTest.java
├── GUI/                       # GUI component tests
│   └── Admin/
│       └── QuestionBankTest.java
└── QUIZ/                      # Core business logic tests
    ├── RONCompetitorTest.java
    ├── NameTest.java
    ├── QuestionTest.java
    └── ManagerTest.java
```

## Test Coverage

### Unit Tests
- **RONCompetitorTest**: Tests competitor functionality, scoring, and data validation
- **NameTest**: Tests name handling, formatting, and validation
- **QuestionTest**: Tests question creation, answer validation, and display
- **ManagerTest**: Tests system management functionality
- **DatabaseConnectionTest**: Tests database connectivity and operations
- **CompetitorListTest**: Tests competitor management and persistence
- **QuestionBankTest**: Tests question retrieval and filtering

### Integration Tests
- **QuizAppIntegrationTest**: Tests interaction between different system components

## Prerequisites

1. **JUnit 5**: Ensure JUnit 5 is added to your project dependencies
2. **Database Setup**: Tests assume a working database connection
3. **Test Data**: Some tests may require test data in the database

## Running Tests

### IDE (IntelliJ IDEA/Eclipse)
1. Right-click on the `test` directory
2. Select "Run All Tests" or "Run Tests in 'test'"

### Command Line (using Maven)
```bash
mvn test
```

### Command Line (using Gradle)
```bash
gradle test
```

### Running Specific Test Classes
```bash
# Run all tests in a specific package
mvn test -Dtest="QUIZ.*"

# Run a specific test class
mvn test -Dtest="RONCompetitorTest"

# Run a specific test method
mvn test -Dtest="RONCompetitorTest#testAddQuizAttemptScore_Success"
```

## Test Configuration

### Database Tests
- Tests assume a test database is available
- For production testing, consider using an in-memory database or test database
- Some tests may fail if database is not accessible (this is expected)

### Mock Data
- Tests create their own test data in `@BeforeEach` methods
- Tests clean up after themselves in `@AfterEach` methods

## Test Results

Tests validate:
- ✅ Object creation and initialization
- ✅ Business logic correctness
- ✅ Data validation and bounds checking
- ✅ Database connectivity and operations
- ✅ Error handling and edge cases
- ✅ Interface contract compliance
- ✅ Component integration

## Adding New Tests

When adding new functionality to QuizApp:

1. Create corresponding test classes in the appropriate package
2. Follow the naming convention: `[ClassName]Test.java`
3. Use `@Test` annotations for test methods
4. Use `@BeforeEach` for setup and `@AfterEach` for cleanup
5. Add new test classes to `AllTestsSuite.java`

## Test Dependencies

Add these dependencies to your `pom.xml` (Maven) or `build.gradle` (Gradle):

### Maven
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-suite</artifactId>
    <version>1.9.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>4.6.1</version>
    <scope>test</scope>
</dependency>
```

### Gradle
```gradle
testImplementation 'org.junit.jupiter:junit-jupiter:5.9.0'
testImplementation 'org.junit.platform:junit-platform-suite:1.9.0'
testImplementation 'org.mockito:mockito-core:4.6.1'
```

## Notes

- Some tests may require manual database setup
- Integration tests assume all components are properly configured
- Tests are designed to be independent and can run in any order
- Mock objects are used where appropriate to isolate unit tests