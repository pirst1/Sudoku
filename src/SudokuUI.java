import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuUI extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private int[][] puzzle;

    public SudokuUI() {
        setTitle("Sudoku Game");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sudoku grid
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        Font font = new Font("SansSerif", Font.BOLD, 20);
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(font);
                field.setDocument(new JTextFieldLimit(1));
                cells[r][c] = field;
                gridPanel.add(field);
            }

        // Buttons
        JButton newBtn = new JButton("New Game");
        JButton checkBtn = new JButton("Check");
        JButton clearBtn = new JButton("Clear");

        newBtn.addActionListener(e -> generatePuzzle());
        checkBtn.addActionListener(e -> validateBoard());
        clearBtn.addActionListener(e -> clearInputs());

        JPanel controlPanel = new JPanel();
        controlPanel.add(newBtn);
        controlPanel.add(checkBtn);
        controlPanel.add(clearBtn);

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        generatePuzzle();
    }

    private void generatePuzzle() {
        clearAll();
        puzzle = SudokuGenerator.generate();
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                if (puzzle[r][c] != 0) {
                    cells[r][c].setText(String.valueOf(puzzle[r][c]));
                    cells[r][c].setEditable(false);
                    cells[r][c].setBackground(Color.LIGHT_GRAY);
                } else {
                    cells[r][c].setEditable(true);
                    cells[r][c].setBackground(Color.WHITE);
                }
            }
    }

    private void clearInputs() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (cells[r][c].isEditable()) {
                    cells[r][c].setText("");
                    cells[r][c].setBackground(Color.WHITE);
                }
    }

    private void clearAll() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                cells[r][c].setText("");
                cells[r][c].setEditable(true);
                cells[r][c].setBackground(Color.WHITE);
            }
    }

    private void validateBoard() {
        boolean valid = true;
        int[][] tempBoard = new int[9][9];

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                String value = cells[r][c].getText();
                if (value.matches("[1-9]"))
                    tempBoard[r][c] = Integer.parseInt(value);
                else
                    tempBoard[r][c] = 0;
            }

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                int num = tempBoard[r][c];
                if (num != 0) {
                    tempBoard[r][c] = 0; // temporarily remove for validation
                    if (!isValid(tempBoard, r, c, num)) {
                        cells[r][c].setBackground(Color.PINK);
                        valid = false;
                    } else if (cells[r][c].isEditable()) {
                        cells[r][c].setBackground(Color.WHITE);
                    }
                    tempBoard[r][c] = num; // restore value
                }
            }

        String message = valid ? "Board is valid!" : "There are rule violations!";
        JOptionPane.showMessageDialog(this, message);
    }

    private boolean isValid(int[][] board, int row, int col, int val) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == val || board[i][col] == val) return false;
        }

        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[boxRow + r][boxCol + c] == val) return false;

        return true;
    }
}
