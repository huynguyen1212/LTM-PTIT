/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Little Coconut
 */
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientMainFrm extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnJoin ;

    public ClientMainFrm() {
        super("Video call UDP");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel lblTitle = new JLabel("Game Sudoku");
        lblTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lblTitle.setBounds(new Rectangle(250, 50, 300, 30));
        mainPanel.add(lblTitle, null);

        btnJoin = new JButton("Join game");
        btnJoin.setBounds(new Rectangle(250, 125, 139, 30));
        btnJoin.addActionListener(this);
        mainPanel.add(btnJoin, null);
 

        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(new Dimension(640, 300));
        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            JButton btn = (JButton) ae.getSource();
            if (btn.equals(btnJoin)) {
                Game lv = new Game();
                lv.setVisible(true);
                this.dispose(); 
            }  
        }
    }

    public static void main(String[] args) {
        ClientMainFrm view = new ClientMainFrm();
        view.setVisible(true);
    }
}
