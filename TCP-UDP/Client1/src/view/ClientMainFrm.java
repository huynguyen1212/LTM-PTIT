///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Little Mango
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.IPAddress;
import model.ObjectWrapper;
import control.ClientCtr;

public class ClientMainFrm extends JFrame implements ActionListener {
 
    private JTextField txtServerHost;
    private JTextField txtServerPort;
    private JButton btnLogin;
    private JButton btnSignup;
    private JTextArea mainText;
    private ClientCtr myControl;

    public ClientMainFrm() {
        super("TCP client view");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

//        JLabel lblTitle = new JLabel("Welcome to ...");
//        lblTitle.setFont(new java.awt.Font("Dialog", 1, 20));
//        lblTitle.setBounds(new Rectangle(180, 50, 300, 30));
//        mainPanel.add(lblTitle, null);
 
        btnLogin = new JButton("Login");
        btnLogin.setBounds(new Rectangle(132, 125, 139, 30));
        btnLogin.addActionListener(this);
        mainPanel.add(btnLogin, null);
 
        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(new Dimension(640, 300));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (myControl != null) {
                    myControl.closeConnection();
                }
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            myControl = new ClientCtr(this);
            JButton btn = (JButton) ae.getSource();
            if (myControl.openConnection()) {
                if (btn.equals(btnLogin)) {
                    for (ObjectWrapper func : myControl.getActiveFunction()) {
                        if (func.getData() instanceof LoginFrm) {
                            ((LoginFrm) func.getData()).setVisible(true);
                            return;
                        }
                    }
                    LoginFrm login = new LoginFrm(myControl);
                    login.setVisible(true);
                    this.dispose();
                }
            }
        }
    }
    public void showMessage(String s) {
        mainText.append("\n" + s);
        mainText.setCaretPosition(mainText.getDocument().getLength());
    }

    public void resetClient() {
        if (myControl != null) {
            myControl.closeConnection();
            myControl.getActiveFunction().clear();
            myControl = null;
        }
        btnSignup.setEnabled(false);
        btnLogin.setEnabled(true);
    }

    public static void main(String[] args) {
        ClientMainFrm view = new ClientMainFrm();
        view.setVisible(true);
    }
}
