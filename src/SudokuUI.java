import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuUI extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private SudokuController controller;


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

        JPanel controlPanel = new JPanel();
        controlPanel.add(newBtn);
        controlPanel.add(checkBtn);
        controlPanel.add(clearBtn);

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        newBtn.addActionListener(e -> controller.generatePuzzle());
        checkBtn.addActionListener(e -> controller.checkBoard());
        clearBtn.addActionListener(e -> controller.clearInputs());
        
        
    }
    
    public void setController(SudokuController controller) {
        this.controller = controller;
    }
    
    public void displayBoard(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int val = board[r][c];
                JTextField cell = cells[r][c];
                if (val != 0) {
                    cell.setText(String.valueOf(val));
                    cell.setEditable(false);
                    cell.setBackground(Color.LIGHT_GRAY);
                } else {
                    cell.setText("");
                    cell.setEditable(true);
                    cell.setBackground(Color.WHITE);
                }
            }
        }
    }

    public void clearEditableCells() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (cells[r][c].isEditable()) {
                    cells[r][c].setText("");
                    cells[r][c].setBackground(Color.WHITE);
                }
            }
        }
    }

    public int[][] getUserInput() {
        int[][] board = new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String text = cells[r][c].getText();
                board[r][c] = text.matches("[1-9]") ? Integer.parseInt(text) : 0;
            }
        }
        return board;
    }

    public void markCellInvalid(int r, int c) {
        cells[r][c].setBackground(Color.PINK);
    }

    public void markCellValid(int r, int c) {
        if (cells[r][c].isEditable())
            cells[r][c].setBackground(Color.WHITE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }



    
}
