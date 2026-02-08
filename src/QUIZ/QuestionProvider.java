package QUIZ;

/**
 * Interface defining the contract for question providers in the quiz system
 */
public interface QuestionProvider {
    String getQuestionText();
    String[] getOptions();
    String getDifficultyLevel();
    boolean isCorrect(int selectedIndex);
}