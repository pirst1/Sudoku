import javax.swing.*;
import java.awt.*;
import java.util.*;



public class SudokuUI extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private int[][] puzzle;
    private int[][] solution;


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
                
                int top = (r % 3 == 0) ? 3 : 1;
                int left = (c % 3 == 0) ? 3 : 1;
                int bottom = (r == 8) ? 3 : 1;
                int right = (c == 8) ? 3 : 1;

                field.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                gridPanel.add(field);
            }

        // Buttons
        JButton easyBtn = new JButton("New Game (Easy)");
        JButton hardBtn = new JButton("New Game (Difficult)");
        JButton checkBtn = new JButton("Check");
        JButton clearBtn = new JButton("Clear");
        JButton hintBtn = new JButton("Hint");
        
        hintBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hintBtn.addActionListener(e -> giveHint());

        
        JButton[] buttons = { easyBtn, hardBtn, checkBtn, clearBtn };
        for (JButton btn : buttons) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        

        easyBtn.addActionListener(e -> generatePuzzle(40));
        hardBtn.addActionListener(e -> generatePuzzle(55));
        checkBtn.addActionListener(e -> validateBoard());
        clearBtn.addActionListener(e -> clearInputs());

        JPanel controlPanel = new JPanel();
        controlPanel.add(easyBtn);
        controlPanel.add(hardBtn); 
        controlPanel.add(checkBtn);
        controlPanel.add(clearBtn);
        controlPanel.add(hintBtn);


        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        generatePuzzle(40);
    }

    private void generatePuzzle(int blanks) {
        clearAll();
        int[][][] generated = SudokuGenerator.generateWithSolution(blanks);
        puzzle = generated[0];
        solution = generated[1];

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
            	if (puzzle[r][c] != 0) {
            	    cells[r][c].setText(String.valueOf(puzzle[r][c]));
            	    cells[r][c].setEditable(false);
            	    cells[r][c].setForeground(Color.BLACK);         
            	    cells[r][c].setBackground(Color.LIGHT_GRAY);
            	} else {
            	    cells[r][c].setEditable(true);
            	    cells[r][c].setText("");                        
            	    cells[r][c].setForeground(new Color(30, 144, 255)); 
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
                    tempBoard[r][c] = 0;
                    if (!isValid(tempBoard, r, c, num)) {
                        cells[r][c].setBackground(Color.PINK);
                        valid = false;
                    } else {
                        if (cells[r][c].isEditable())
                            cells[r][c].setBackground(Color.WHITE);
                        else
                            cells[r][c].setBackground(Color.LIGHT_GRAY);
                    }
                    tempBoard[r][c] = num;
                } else {
                    if (cells[r][c].isEditable())
                        cells[r][c].setBackground(Color.WHITE);
                    else
                        cells[r][c].setBackground(Color.LIGHT_GRAY);
                }
            }

        boolean complete = true;
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (cells[r][c].getText().isEmpty()) {
                    complete = false;
                    break;
                }

        if (valid && complete) {
            for (int r = 0; r < 9; r++)
                for (int c = 0; c < 9; c++)
                    cells[r][c].setBackground(new Color(200, 255, 200));
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle!");
        } else if (valid) {
            JOptionPane.showMessageDialog(this, "Board is valid so far. Keep going!");
        } else {
            JOptionPane.showMessageDialog(this, "There are rule violations!");
        }
    }

    
    private void giveHint() {
    	java.util.List<Point> emptyCells = new ArrayList<>();

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (cells[r][c].isEditable() && cells[r][c].getText().isEmpty())
                    emptyCells.add(new Point(r, c));

        if (emptyCells.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No available hints.");
            return;
        }

        Collections.shuffle(emptyCells);
        Point p = emptyCells.get(0);
        int r = p.x, c = p.y;

        int correct = solution[r][c];
        cells[r][c].setText(String.valueOf(correct));
        cells[r][c].setForeground(new Color(30, 144, 255)); 
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
