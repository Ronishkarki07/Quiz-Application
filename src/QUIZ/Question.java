package QUIZ;

public class Question {
    private int id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOptionLetter; // Stores "A", "B", "C", or "D"
    private String difficultyLevel;

    public Question(int id, String questionText, String optionA, String optionB, String optionC, String optionD, String correctOptionLetter, String difficultyLevel) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOptionLetter = correctOptionLetter;
        this.difficultyLevel = difficultyLevel;
    }

    public String getQuestionText() { return questionText; }

    // Return all 4 options
    public String[] getOptions() {
        return new String[]{optionA, optionB, optionC, optionD};
    }

    public String getDifficultyLevel() { return difficultyLevel; }

    /**
     * Checks if the selected index (0-3) matches the correct letter (A-D).
     * @param selectedIndex 0 for A, 1 for B, 2 for C, 3 for D
     */
    public boolean isCorrect(int selectedIndex) {
        String selectedLetter = "";
        switch (selectedIndex) {
            case 0: selectedLetter = "A"; break;
            case 1: selectedLetter = "B"; break;
            case 2: selectedLetter = "C"; break;
            case 3: selectedLetter = "D"; break;
        }
        return selectedLetter.equalsIgnoreCase(correctOptionLetter);
    }
}