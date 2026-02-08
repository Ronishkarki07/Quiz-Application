package QUIZ;

/**
 * Demonstration class showing how to use the interfaces polymorphically
 */
public class InterfaceDemo {
    
    /**
     * Demonstrates polymorphic behavior with Competitor interface
     */
    public static void displayCompetitorInfo(Competitor competitor) {
        System.out.println("Competitor ID: " + competitor.getCompetitorID());
        System.out.println("Name: " + competitor.getCompetitorName().getFullName());
        System.out.println("Level: " + competitor.getCompetitorLevel());
        System.out.println("Overall Score: " + competitor.getOverallScore());
        System.out.println("Short Details: " + competitor.getShortDetails());
        System.out.println("---");
    }
    
    /**
     * Demonstrates polymorphic behavior with QuestionProvider interface
     */
    public static void displayQuestionInfo(QuestionProvider question) {
        System.out.println("Question: " + question.getQuestionText());
        System.out.println("Difficulty: " + question.getDifficultyLevel());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.println("---");
    }
    
    /**
     * Demonstrates polymorphic behavior with Identifiable interface
     */
    public static void displayId(Identifiable item) {
        System.out.println("Item ID: " + item.getId());
    }
    
    /**
     * Demonstrates polymorphic behavior with Scoreable interface
     */
    public static void displayScoreInfo(Scoreable scoreable) {
        System.out.println("Overall Score: " + scoreable.getOverallScore());
        int[] scores = scoreable.getScores();
        System.out.print("Individual Scores: ");
        for (int score : scores) {
            System.out.print(score + " ");
        }
        System.out.println();
    }
    
    /**
     * Demonstrates polymorphic behavior with PersonName interface
     */
    public static void displayNameInfo(PersonName name) {
        System.out.println("Full Name: " + name.getFullName());
        System.out.println("Initials: " + name.getInitials());
        System.out.println("First Name: " + name.getFirstName());
        System.out.println("Last Name: " + name.getLastName());
    }
}