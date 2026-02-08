package QUIZ;

public class Question implements QuestionProvider, Identifiable {
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

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    // Return all 4 options
    @Override
    public String[] getOptions() {
        return new String[]{optionA, optionB, optionC, optionD};
    }

    @Override
    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Checks if the selected index (0-3) matches the correct letter (A-D).
     * @param selectedIndex 0 for A, 1 for B, 2 for C, 3 for D
     */
    @Override
    public boolean isCorrect(int selectedIndex) {
        try {
            // Case 1: DB stores index as String "0", "1", "2", "3"
            int correctIndex = Integer.parseInt(correctOptionLetter);
            return selectedIndex == correctIndex;
        } catch (NumberFormatException e) {
            // Case 2: DB stores "A", "B", "C", "D"
            String selectedLetter = "";
            if (selectedIndex == 0) selectedLetter = "A";
            if (selectedIndex == 1) selectedLetter = "B";
            if (selectedIndex == 2) selectedLetter = "C";
            if (selectedIndex == 3) selectedLetter = "D";
            return correctOptionLetter.equalsIgnoreCase(selectedLetter);
        }
    }
}