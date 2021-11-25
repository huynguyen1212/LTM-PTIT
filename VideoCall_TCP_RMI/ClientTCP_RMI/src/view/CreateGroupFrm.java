/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ClientCtr;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.ObjectWrapper;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class CreateGroupFrm extends JFrame implements ActionListener {

    private JTextField txtGroupName, txtName, txtAddress;
    private JPasswordField txtPassword;
    private JButton btnCreate, btnLogin;
    private ClientCtr mySocket;

    public CreateGroupFrm(ClientCtr myRemoteObjectN) {
        super("Video call app");
        mySocket = myRemoteObjectN;
        JPanel content = new JPanel();
        content.setLayout(null);

        JLabel title = new JLabel("Create group");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(270, 50, 200, 30));
        content.add(title, null);

        JLabel label1 = (new JLabel("Group name:"));
        label1.setBounds(new Rectangle(132, 107, 96, 27));
        content.add(label1);
        txtGroupName = new JTextField(15);
        txtGroupName.setBounds(new Rectangle(248, 106, 258, 30));
        content.add(txtGroupName);

        btnCreate = new JButton("Create");
        btnCreate.setBounds(267, 160, 136, 31);
        content.add(btnCreate);
        btnCreate.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(650, 270));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP, this));

    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnCreate)) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CREATE_GROUP, txtGroupName.getText()));
        }
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        if (data.getData().equals("false")) {
            JOptionPane.showMessageDialog(this, "Error!");
        } else {
            JOptionPane.showMessageDialog(this, "Successful!");
            this.dispose();
        }
    }
}
