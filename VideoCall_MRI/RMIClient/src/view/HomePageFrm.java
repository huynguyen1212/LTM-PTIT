/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ClientCtr;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Friend;
import model.Group;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class HomePageFrm extends JFrame implements ActionListener {

//    private JTextField txtId, txtName, txtIdcard, txtAddress, txtEmail, txtTel, txtNote;
    private JButton btnListFriends, btnSearchUser, btnListRequests, btnListGroups;
    private ClientCtr mySocket;

    public HomePageFrm(ClientCtr socket) {
        super("RMI");
        mySocket = socket;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(null);

        JLabel title = new JLabel("Home");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(60, 100, 200, 30));
        pnMain.add(title, null);

        btnListFriends = new JButton("List friends");
        btnListFriends.setBounds(100, 172, 150, 33);
        pnMain.add(btnListFriends);
        btnListFriends.addActionListener(this);

        btnSearchUser = new JButton("Search user");
        btnSearchUser.setBounds(300, 172, 150, 33);
        pnMain.add(btnSearchUser);
        btnSearchUser.addActionListener(this);

        btnListRequests = new JButton("List requests");
        btnListRequests.setBounds(100, 233, 150, 33);
        pnMain.add(btnListRequests);
        btnListRequests.addActionListener(this);

        btnListGroups = new JButton("List groups");
        btnListGroups.setBounds(300, 233, 150, 33);
        pnMain.add(btnListGroups);
        btnListGroups.addActionListener(this);

        this.setContentPane(pnMain);
        this.setSize(625, 450);
        this.setLocation(200, 10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                if (mySocket != null) {
//                    mySocket.sendData(new ObjectWrapper(ObjectWrapper.USER_OUT, null));
//                    mySocket.close();
//                }
//                System.exit(0);
//            }
//        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();

        if (btnClicked.equals(btnListFriends)) {
            ArrayList<User> listFriends = (ArrayList<User>) mySocket.getListFriends();
            if (listFriends == null) {
                JOptionPane.showMessageDialog(this, "Error connecting to RMI server!");
                dispose();
            } else {
                ListFriendsFrm lfv = new ListFriendsFrm(mySocket, listFriends);
                lfv.setVisible(true);
            }

        } else if (btnClicked.equals(btnListRequests)) {
            ArrayList<Friend> listRequests = (ArrayList<Friend>) mySocket.getListRequests();
            if (listRequests == null) {
                JOptionPane.showMessageDialog(this, "Error connecting to RMI server!");
                dispose();
            } else {
                ListRequestsFrm lfv = new ListRequestsFrm(mySocket, listRequests);
                lfv.setVisible(true);
            }
        } else if (btnClicked.equals(btnSearchUser)) {
            (new SearchUserFrm(mySocket)).setVisible(true);
        } else if (btnClicked.equals(btnListGroups)) {
//            ListGroupsFrm view = new ListGroupsFrm(mySocket);
//            view.setVisible(true);
//            dispose();d
//            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_GROUPS, mySocket.getUserId()));
//            //receiving
//            ObjectWrapper data = mySocket.receiveData();
//            if (data.getPerformative() == ObjectWrapper.REPLY_LIST_GROUPS) {
//                ArrayList<Group> lg = (ArrayList<Group>) data.getData();
//                (new ListGroupFrm(mySocket, lg)).setVisible(true); 
//            }
        }
    }
}
