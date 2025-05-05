import java.util.Random;
import java.util.Arrays;


public class SudokuGenerator {
	public static int[][] generate() {
		return generateWithSolution(40)[0];  

    }
	
	public static int[][][] generateWithSolution(int blanks) {
	    int[][] full = new int[9][9];
	    fillDiagonal(full);
	    SudokuSolver.solve(full);

	    int[][] puzzle = copyBoard(full);
	    removeCells(puzzle, blanks);

	    return new int[][][] { puzzle, full }; 
	}

	private static int[][] copyBoard(int[][] src) {
	    int[][] copy = new int[9][9];
	    for (int i = 0; i < 9; i++)
	        copy[i] = Arrays.copyOf(src[i], 9);
	    return copy;
	}


    private static void fillDiagonal(int[][] board) {
        Random rand = new Random();
        for (int i = 0; i < 9; i += 3) {
            for (int r = 0; r < 3; r++)
                for (int c = 0; c < 3; c++) {
                    int num;
                    do {
                        num = rand.nextInt(9) + 1;
                    } while (!isSafe(board, i + r, i + c, num));
                    board[i + r][i + c] = num;
                }
        }
    }

    private static boolean isSafe(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++)
            if (board[row][i] == num || board[i][col] == num) return false;

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[startRow + r][startCol + c] == num) return false;

        return true;
    }

    private static void removeCells(int[][] board, int count) {
        Random rand = new Random();
        while (count > 0) {
            int r = rand.nextInt(9);
            int c = rand.nextInt(9);
            if (board[r][c] != 0) {
                board[r][c] = 0;
                count--;
            }
        }
    }
    
}
