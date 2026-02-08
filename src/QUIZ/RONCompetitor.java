package QUIZ;

public class RONCompetitor implements Competitor, Identifiable, Scoreable {
    private String competitorID;
    private Name competitorName;
    private String competitorLevel;
    private String country;
    private int age;
    private String password;
    // This array represents 5 separate QUIZ ATTEMPTS
    private int[] scores;

    public RONCompetitor(String competitorID, Name competitorName, String competitorLevel, String country, int age, String password) {
        this.competitorID = competitorID;
        this.competitorName = competitorName;
        this.competitorLevel = competitorLevel;
        this.country = country;
        this.age = age;
        this.password = password;
        this.scores = new int[]{0, 0, 0, 0, 0}; // Holds up to 5 attempts
    }

    // --- NEW: Method to add a single quiz attempt score ---
    @Override
    public boolean addQuizAttemptScore(int score) {
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] == 0) { // Find first empty slot
                scores[i] = score;
                return true; // Successfully added
            }
        }
        return false; // No attempts left (Array full)
    }

    @Override
    public double getOverallScore() {
        if (scores == null) return 0.0;
        double sum = 0;
        int count = 0;

        // Calculate average of ATTEMPTS taken so far
        for (int s : scores) {
            if (s > 0) { // Only count attempts that have been taken
                sum += s;
                count++;
            }
        }
        if (count == 0) return 0.0;
        return sum / count;
    }

    // Getters and Setters
    @Override
    public String getCompetitorID() {
        return competitorID;
    }

    @Override
    public String getId() {
        return getCompetitorID();
    }

    public void setCompetitorID(String competitorID) {
        this.competitorID = competitorID;
    }

    @Override
    public Name getCompetitorName() {
        return competitorName;
    }
    
    @Override
    public String getCompetitorLevel() {
        return competitorLevel;
    }
    
    @Override
    public void setCompetitorLevel(String level) {
        this.competitorLevel = level;
    }
    public String getCountry() {
        return country;
    }
    public int getAge() {
        return age;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public int[] getScores() {
        return scores;
    }
    
    @Override
    public void setScores(int[] scores) {
        this.scores = scores;
    }

    @Override
    public String getFullDetails(){
        return ("Competitor ID "+ competitorID + ", named " + competitorName.getFirstName() + " " +
                competitorName.getMiddleName() + " " +  competitorName.getLastName() +
                ", country " + country) + "\n" +
                competitorName.getFirstName() + " is a Novice aged " + age +
                " and has an overall score of " + getOverallScore() + ".";
    }

    @Override
    public String getShortDetails(){
        return ("Competitor Number " + competitorID + ", " +
                competitorName.getInitials() + " has overall score of " +
                getOverallScore()) + ".";
    }
}