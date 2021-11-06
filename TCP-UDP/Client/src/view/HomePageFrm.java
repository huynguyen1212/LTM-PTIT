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
import model.ObjectWrapper;
import model.PrivateCall;

/**
 *
 * @author Little Coconut
 */
public class HomePageFrm extends JFrame implements ActionListener {

//    private JTextField txtId, txtName, txtIdcard, txtAddress, txtEmail, txtTel, txtNote;
    private JButton btnListFriends, btnListGroups, btnListRequests;
    private ClientCtr mySocket;

    public HomePageFrm(ClientCtr socket) {
        super("Video call app");
        mySocket = socket;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(null);

        JLabel title = new JLabel("Home page");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(260, 50, 200, 30));
        pnMain.add(title, null);

        btnListFriends = new JButton("List friends");
        btnListFriends.setBounds(210, 112, 206, 33);
        pnMain.add(btnListFriends);
        btnListFriends.addActionListener(this);

        btnListGroups = new JButton("Search user");
        btnListGroups.setBounds(210, 233, 206, 33);
        pnMain.add(btnListGroups);
        btnListGroups.addActionListener(this);

        btnListRequests = new JButton("List requests");
        btnListRequests.setBounds(210, 172, 206, 33);
        pnMain.add(btnListRequests);
        btnListRequests.addActionListener(this);

        this.setContentPane(pnMain);
        this.setSize(625, 360);
        this.setLocation(200, 10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.INCOMING_CALL, this)); 
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();

        if (btnClicked.equals(btnListFriends)) {
            (new ListFriendsFrm(mySocket)).setVisible(true);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_FRIENDS, null));
        } else if (btnClicked.equals(btnListGroups)) {
            (new SearchUserFrm(mySocket)).setVisible(true);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_USER, null));
        } else if (btnClicked.equals(btnListRequests)) {
            (new ListRequestsFrm(mySocket)).setVisible(true);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_REQUESTS, null));
        }
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        JOptionPane.showMessageDialog(this, "Have call");
//        if(data.getPerformative() == ObjectWrapper.INCOMING_CALL){
        PrivateCall privateCall = (PrivateCall) data.getData();

        int output = JOptionPane.showConfirmDialog(this,
                "Incoming call from xxx. Accept?", "Incoming call",
                JOptionPane.YES_NO_OPTION);
        if (output == JOptionPane.YES_OPTION) {
            System.out.println("Accept call");
        } else {
//                } else if (output == JOptionPane.NO_OPTION) {
            System.out.println("Refuse call");
        }

//        }
//        else if (data.getData().equals("ok")) {
//            JOptionPane.showMessageDialog(this, "Update succesfully!"); 
//            this.dispose();
//        } else {
//            JOptionPane.showMessageDialog(this, "Error when updating!");
//        }
    }
}
