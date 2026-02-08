

import GUI.QuizMainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
    
        System.out.println("Starting Quiz Application...");
        // Launch the Quiz Application GUI
        SwingUtilities.invokeLater(() -> {
            // TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have
            // set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut
            // actionId="ToggleLineBreakpoint"/>.
            new QuizMainFrame().setVisible(true);
        });
    }
}