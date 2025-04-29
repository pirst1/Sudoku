import javax.swing.text.*;

public class JTextFieldLimit extends PlainDocument {
    private int limit;

    public JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) return;
        if ((getLength() + str.length()) <= limit && str.matches("[1-9]?")) {
            super.insertString(offset, str, attr);
        }
    }
}