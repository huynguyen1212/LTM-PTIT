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
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Friend;
import model.ObjectWrapper;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class SearchUserDetailFrm extends JFrame implements ActionListener {
    private JButton btnDelete, btnCall, btnAdd;
    private ClientCtr mySocket;
    private User friend;
    private JPanel content;

    public SearchUserDetailFrm(ClientCtr socket, User friendN) {
        super("Video call app");
        mySocket = socket;
        friend = friendN;
        content = new JPanel();
        content.setLayout(null);

        JLabel title = new JLabel("SEARCH DETAIL");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(250, 50, 200, 30));
        content.add(title, null);

        JLabel label1 = (new JLabel("Name:"));
        label1.setBounds(new Rectangle(190, 107, 96, 27));
        content.add(label1);
        JLabel label11 = (new JLabel(friend.getName()));
        label11.setBounds(new Rectangle(310, 106, 258, 30));
        content.add(label11);

        JLabel label2 = new JLabel("Address:");
        label2.setBounds(new Rectangle(190, 156, 96, 27));
        content.add(label2);
        JLabel label22 = new JLabel(friend.getAddress());
        label22.setBounds(new Rectangle(310, 156, 258, 30)); 
        content.add(label22);

        JLabel label3 = (new JLabel("Username:"));
        label3.setBounds(new Rectangle(190, 207, 96, 27));
        content.add(label3);
        JLabel label33 = (new JLabel(friend.getUsername()));
        label33.setBounds(new Rectangle(310, 206, 258, 30));
        content.add(label33);

        JLabel label4 = (new JLabel("Status:"));
        label4.setBounds(new Rectangle(190, 257, 96, 27));
        content.add(label4);
        JLabel labelStatus = (new JLabel(""));
        labelStatus.setBounds(new Rectangle(310, 256, 258, 30));
        content.add(labelStatus);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(170, 310, 141, 31);
//        content.add(btnDelete);
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(this);
        
        btnCall = new JButton("Call ");
        btnCall.setBounds(351, 310, 141, 31);
        btnCall.setEnabled(false);
//        content.add(btnCall);
        btnCall.addActionListener(this); 
        
         btnAdd = new JButton("Add friend");
        btnAdd.setBounds(270, 310, 141, 31);
        content.add(btnAdd);
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(this); 

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(625, 450));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER_DETAIL, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND, this));
        
        if(friend.getIsOnline()==1){
            labelStatus.setText("Online");
        } else labelStatus.setText("Offline");
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnDelete)) {
//            mySocket.sendData(new ObjectWrapper(ObjectWrapper.DELETE_FRIEND, friend.getId()));
            this.dispose();
        } else if (btnClicked.equals(btnCall)) {
//            LoginFrm login = new LoginFrm(mySocket);
//            login.setVisible(true);
            this.dispose();
        } else if(btnClicked.equals(btnAdd)){
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.ADD_FRIEND, friend.getId()));
            this.dispose();
        }
    } 
    public void receivedDataProcessing(ObjectWrapper data) { //check friend
//        JOptionPane.showMessageDialog(this, data.getData());
        if (data.getData().equals("friend")) {
            btnDelete.setEnabled(true);
            btnCall.setEnabled(true);
            btnAdd.setEnabled(false);
        } else if (data.getData().equals("true")){ 
            btnDelete.setEnabled(false);
            btnCall.setEnabled(false);
            btnAdd.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Successfull");
        }  else if(data.getData().equals("waiting")){             
            btnDelete.setEnabled(false);
            btnCall.setEnabled(false);
            btnAdd.setEnabled(false);
        }else {             
            btnDelete.setEnabled(false);
            btnCall.setEnabled(false);
            btnAdd.setEnabled(true);
        }
    }
}
