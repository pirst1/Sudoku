import javax.swing.SwingUtilities;

public class SudokuApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SudokuUI().setVisible(true);
        });
    }
}