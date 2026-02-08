package QUIZ;

/**
 * Interface for objects that can have and calculate scores
 */
public interface Scoreable {
    double getOverallScore();
    int[] getScores();
    void setScores(int[] scores);
}