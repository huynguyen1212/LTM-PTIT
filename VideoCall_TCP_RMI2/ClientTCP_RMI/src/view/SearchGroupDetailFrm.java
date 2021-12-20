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
import model.Group;
import model.GroupMember;
import model.ObjectWrapper;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class SearchGroupDetailFrm extends JFrame implements ActionListener {

    private JButton btnJoin;
    private ClientCtr mySocket;
    private Group group;
    private JPanel content;

    public SearchGroupDetailFrm(ClientCtr socket, Group groupN) {
        super("Video call app");
        mySocket = socket;
        group = groupN;
        content = new JPanel();
        content.setLayout(null);

        JLabel title = new JLabel("Join group "+group.getName()+ "?");
        title.setFont(new java.awt.Font("Dialog", 1, 18));
        title.setBounds(new Rectangle(70, 30, 200, 30));
        content.add(title, null);

//        JLabel label1 = (new JLabel("Name:"));
//        label1.setBounds(new Rectangle(190, 107, 96, 27));
//        content.add(label1);
//        JLabel label11 = (new JLabel(group.getName()));
//        label11.setBounds(new Rectangle(310, 106, 258, 30));
//        content.add(label11);
        btnJoin = new JButton("Join group");
        btnJoin.setBounds(90, 100, 120, 20);
        content.add(btnJoin);
        btnJoin.setEnabled(true);
        btnJoin.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(300, 170));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECKMEMBER_IN_GROUP, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_GROUP, this));

    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnJoin)) {
            GroupMember gm = new GroupMember();
            gm.setUser(mySocket.getInfo());
            gm.setGroup(group);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.JOIN_GROUP, gm));
            this.dispose();
        }
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        System.out.println(data.getData());
        if (data.getPerformative() == ObjectWrapper.REPLY_CHECKMEMBER_IN_GROUP) {
//            if (data.getData().equals("true")) {
//                JOptionPane.showMessageDialog(this, "You've already joined this group!");
//                btnJoin.setEnabled(false);
//                dispose();
//            }
        } else {
            if (data.getData().equals("true")) {
                JOptionPane.showMessageDialog(this, "Successfull");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "You've already joined this group!!");
            }
        }
    }
}
