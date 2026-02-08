package QUIZ;

/**
 * Interface defining the contract for competitor behavior in the quiz system
 */
public interface Competitor {
    String getCompetitorID();
    Name getCompetitorName();
    String getCompetitorLevel();
    void setCompetitorLevel(String level);
    double getOverallScore();
    boolean addQuizAttemptScore(int score);
    int[] getScores();
    void setScores(int[] scores);
    String getFullDetails();
    String getShortDetails();
}