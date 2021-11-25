
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Board extends JPanel implements ActionListener {

    private JPanel Board;
    private JTextField[][] board;

    public Board() {
        Board = new JPanel(new GridLayout(9, 9)); // JPanel gridlayout of 9x9
        Board.setBackground(Color.BLACK);
        board = new JTextField[9][9]; // JTextField array of 9x9

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new JTextField();
                board[i][j].setEditable(true);
                board[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                Font font = new Font("Arial", Font.PLAIN, 20);
                board[i][j].setFont(font);
                board[i][j].setHorizontalAlignment(JTextField.CENTER);
                Board.add(board[i][j]);
            }
        }

    }

    public synchronized JPanel getPlayingBoardPanel() {
        return this.Board;
    }

    public synchronized JTextField[][] getboardArray() {
        return this.board;
    }

    public synchronized void setCellBackground(Color c, int x, int y) {
        board[x][y].setBackground(c);
    }

    public synchronized void clear() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setText("");
                board[i][j].setBackground(Color.WHITE);
            }
        }

    }

    public synchronized boolean inConsistency() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].getBackground() == Color.RED) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void setEditability(boolean editable) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setEditable(editable);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        //Graphics2D g2 = (Graphics2D) g;
        // g2.setStroke(new BasicStroke(10));
        //g2.drawLine(0, 0, 20, 20);

    }

}
