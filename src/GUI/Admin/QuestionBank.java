package GUI.Admin;

import DatabaseConfig.DatabaseConnection;
import QUIZ.Question;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionBank {
    private ArrayList<Question> allQuestions;

    public QuestionBank() {
        allQuestions = new ArrayList<>();
        loadQuestionsFromDB();
    }

    private void loadQuestionsFromDB() {
        allQuestions.clear();
        String sql = "SELECT * FROM Questions"; // Matches your table name

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Fetching columns matching your screenshot exactly
                int id = rs.getInt("q_id");
                String text = rs.getString("question_text");
                String optA = rs.getString("option_a");
                String optB = rs.getString("option_b");
                String optC = rs.getString("option_c");
                String optD = rs.getString("option_d");
                String correct = rs.getString("correct_ans"); // e.g., "A" or "B"
                String level = rs.getString("difficulty"); // e.g., "Beginner"

                allQuestions.add(new Question(id, text, optA, optB, optC, optD, correct, level));
            }
        } catch (Exception e) {
            System.out.println("Error loading questions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Question> getQuestionsForLevel(String level) {
        // Reload to ensure we have the latest added questions from Admin Panel
        loadQuestionsFromDB();

        ArrayList<Question> filtered = new ArrayList<>();

        // Filter by Level
        for (Question q : allQuestions) {
            if (q.getDifficultyLevel().equalsIgnoreCase(level)) {
                filtered.add(q);
            }
        }

        // Shuffle
        Collections.shuffle(filtered);

        // Return max 5
        if (filtered.size() > 5) {
            return new ArrayList<>(filtered.subList(0, 5));
        }
        return filtered;
    }
}