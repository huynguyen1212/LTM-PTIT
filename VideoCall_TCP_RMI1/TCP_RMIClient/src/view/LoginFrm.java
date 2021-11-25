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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.User;
import control.ClientCtr;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import model.ObjectWrapper;

public class LoginFrm extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private ClientCtr mySocket;

    public LoginFrm() {
    }

    public LoginFrm(ClientCtr socket) {
        super("Video call app");
        mySocket = socket;
        JPanel content = new JPanel();
        content.setLayout(null);
        
        JLabel title = new JLabel("Login");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(270, 50, 200, 30)); 
        content.add(title,null);
        
        JLabel label1 = (new JLabel("Username:"));
        label1.setBounds(new Rectangle(132,107,96,27));
        content.add(label1);
        txtUsername = new JTextField(15);
        txtUsername.setBounds(new Rectangle(248, 106, 258, 30));
        content.add(txtUsername);

        JLabel label2 = new JLabel("Password:");
        label2.setBounds(new Rectangle(132,156,96,27));
        content.add(label2);
        txtPassword = new JPasswordField(15);
        txtPassword.setBounds(new Rectangle(248, 156, 258, 30));
        txtPassword.setEchoChar('*');
        content.add(txtPassword);
        
        btnLogin = new JButton("Login");
        btnLogin.setBounds(265, 226,136,31);
        content.add(btnLogin);
        btnLogin.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(625, 328));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, this));
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (mySocket != null) {
                    mySocket.sendData(new ObjectWrapper(ObjectWrapper.USER_OUT, null));
                    mySocket.closeConnection();
                }
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnLogin)) {
            //pack the entity
            User user = new User();
            user.setUsername(txtUsername.getText()); 
            user.setPassword(String.valueOf(txtPassword.getPassword()));

            //sending data
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LOGIN_USER, user)); 

        }
    }

    public void receivedDataProcessing(ObjectWrapper data) { 
        
        if (data.getData().equals("false")) {
            JOptionPane.showMessageDialog(this, "Login fail!");
        } else { 
            HomePageFrm home = new HomePageFrm(mySocket);
            home.setVisible(true);
            this.dispose(); 
        }
    }
}
