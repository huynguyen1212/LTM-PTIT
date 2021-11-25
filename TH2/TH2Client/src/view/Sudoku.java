
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sudoku extends JFrame implements ActionListener {

    private JPanel rowOfButtons; // row of buttons
    private String[] buttonNames = {"CHECK", "RESET", "EXIT"};
    Board b; // Game Board

    public Sudoku() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setBounds((int) width / 4, (int) height / 4, (int) width / 2, (int) height / 2);
        Container contentPane = this.getContentPane();

        JPanel j = new JPanel(new GridLayout(1, 5));
        rowOfButtons = makeButton(j, buttonNames, this);
        contentPane.add(rowOfButtons, BorderLayout.NORTH);

        b = new Board();
        contentPane.add(b.getPlayingBoardPanel(), BorderLayout.CENTER);

    }

    private JPanel makeButton(JPanel p, String[] buttonName, ActionListener target) {
        for (String s : buttonName) {
            JButton b = new JButton(s);
            p.add(b);
            b.addActionListener(target);
        }

        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        String command = e.getActionCommand();

        switch (command) {
            case "CHECK":
                break;
            case "RESET":
                b.clear(); // clear the board
                break;
            case "EXIT":
                System.exit(0);
                break;
        }

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Sudoku d = new Sudoku();
        d.setTitle("Sudoku");
        d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        d.setVisible(true);

    }

}
