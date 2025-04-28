public class SudokuSolver {
    public static boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
        return true;
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++)
            if (board[row][i] == num || board[i][col] == num) return false;

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[startRow + r][startCol + c] == num) return false;

        return true;
    }
}
