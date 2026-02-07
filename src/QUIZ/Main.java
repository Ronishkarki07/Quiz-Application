package QUIZ;

public class Main {
    public static void main(String[] args) {

        Name name = new Name("Sandip", "Bhujel");
        Name name1 = new Name("Ronish", "Bahadur", "Karki");

        RONCompetitor com = new RONCompetitor("01", name, "Beginner", "South Africa", 20, "Hello");
        RONCompetitor com1 = new RONCompetitor("02", name1, "Advance", "Nepal", 21, "Hello");

        System.out.println(com.getFullDetails());
        System.out.println(com1.getShortDetails());
//       System.out.println(name1.getFullName());
        System.out.println(com1.getOverallScore());
    }

}
