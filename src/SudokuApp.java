import javax.swing.SwingUtilities;

public class SudokuApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuModel model = new SudokuModel();
            SudokuUI view = new SudokuUI();
            new SudokuController(model, view);
            view.setVisible(true);
        });
    }
}
