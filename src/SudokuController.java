public class SudokuController {
    private SudokuModel model;
    private SudokuUI view;

    public SudokuController(SudokuModel model, SudokuUI view) {
        this.model = model;
        this.view = view;
        view.setController(this);

        generatePuzzle();
    }

    public void generatePuzzle() {
        model.generateNewPuzzle();
        view.displayBoard(model.getBoard());
    }

    public void clearInputs() {
        view.clearEditableCells();
    }

    public void checkBoard() {
        int[][] board = view.getUserInput();
        boolean valid = true;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int val = board[r][c];
                if (val != 0) {
                    board[r][c] = 0;
                    if (!model.isValid(r, c, val)) {
                        view.markCellInvalid(r, c);
                        valid = false;
                    } else {
                        view.markCellValid(r, c);
                    }
                    board[r][c] = val;
                }
            }
        }

        view.showMessage(valid ? "Board is valid!" : "There are rule violations!");
    }
}
