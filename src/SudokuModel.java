public class SudokuModel {
    private int[][] board;

    public void generateNewPuzzle() {
        board = SudokuGenerator.generate();
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean isValid(int row, int col, int val) {
        return SudokuSolver.isValid(board, row, col, val);
    }

    public boolean solve() {
        return SudokuSolver.solve(board);
    }
}
