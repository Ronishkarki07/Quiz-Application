package QUIZ;

import java.util.Arrays;

public class RONCompetitor {
    private String competitorID;
    private Name competitorName;
    private String competitorLevel;
    private String country;
    private int age;
    private String password;
    private int[] scores;


    public RONCompetitor(String competitorID, Name competitorName, String competitorLevel, String country, int age, String password) {
        this.competitorID = competitorID;
        this.competitorName = competitorName;
        this.competitorLevel = competitorLevel;
        this.country = country;
        this.age = age;
        this.password = password;
        this.scores = new int[5];
    }


    public String getCompetitorID() {
        return competitorID;
    }
    public void setCompetitorID(String competitorID) {
        this.competitorID = competitorID;
    }

    public Name getCompetitorName() {
        return competitorName;
    }
    public void setCompetitorName(Name competitorName) {
        this.competitorName = competitorName;
    }

    public String getCompetitorLevel() {
        return competitorLevel;
    }
    public void setCompetitorLevel(String competitorLevel) {
        this.competitorLevel = competitorLevel;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getScores() {
        return scores;
    }
    public void setScores(int[] scores) {
        if(scores.length == 5) {
            this.scores = scores;
        }
    }

    public double getOverallScore() {
        if (scores == null || scores.length == 0) return 0.0;

        double sum = 0;
        for (int s : scores) {
            sum += s;
        }
        double average = sum / scores.length;

        // Logic: Weighted average based on difficulty level
        switch (competitorLevel.toLowerCase()) {
            case "intermediate":
                return average * 1.5;
            case "advanced":
                return average * 2.0;
            default: // "beginner" or others
                return average * 1.0;
        }
    }

    public String getFullDetails(){
        String scoreString = Arrays.toString(scores).replace("[", "").replace("]", "");
        return ("Competitor ID "+ competitorID + ", named " + competitorName.getFirstName() + " " +
                competitorName.getMiddleName() + " " +  competitorName.getLastName() +
                ", country " + country) + "\n" +
                competitorName.getFirstName() + " is a Novice aged " + age +
                " and has an overall score of " + getOverallScore() + ".";
    }

    public String getShortDetails(){
        return ("Competitor Number " + competitorID + ", " +
                competitorName.getInitials() + " has overall score of " +
                getOverallScore()) + ".";
    }


}
