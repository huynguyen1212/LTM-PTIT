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
import control.ClientCtr;
import java.util.Random;

public class ClientMainFrm extends JFrame implements ActionListener {

    private JTextField txtServerHost, txtServerPort, txtClientHost, txtClientPort;
    private JButton btnLogin, btnSignup;
    private ClientCtr myControl;

    public ClientMainFrm() {
        super("RMI");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

//        JLabel lblTitle = new JLabel("Welcome to video call app");
//        lblTitle.setFont(new java.awt.Font("Dialog", 1, 20));
//        lblTitle.setBounds(new Rectangle(180, 50, 300, 30));
//        mainPanel.add(lblTitle, null);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(new Rectangle(100, 100, 139, 30));
        btnLogin.addActionListener(this);
        mainPanel.add(btnLogin, null);

        btnSignup = new JButton("Signup");
        btnSignup.setBounds(new Rectangle(250, 100, 139, 30));
        btnSignup.addActionListener(this);
        mainPanel.add(btnSignup, null);

        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(new Dimension(640, 300));
        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (myControl != null) {
//                    myControl.close();
                }
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            JButton btn = (JButton) ae.getSource();
            if (myControl == null) {
                myControl = new ClientCtr(this);
            }
//            }
            if (myControl.init()) {
                if (btn.equals(btnLogin)) {
                    LoginFrm lv = new LoginFrm(myControl);
                    lv.setVisible(true);
                    this.dispose();
                } else if (btn.equals(btnSignup)) {
                    SignupFrm sv = new SignupFrm(myControl);
                    sv.setVisible(true);
                }
            }
        }
    }

    public void setServerHost(String serverHost, String serverPort, String service) {
        txtServerHost.setText(serverHost);
        txtServerPort.setText(serverPort);
    }

    public static void main(String[] args) {
        ClientMainFrm view = new ClientMainFrm();
        view.setVisible(true);
    }

}
