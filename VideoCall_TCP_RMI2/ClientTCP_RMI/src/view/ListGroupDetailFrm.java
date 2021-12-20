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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class ListGroupDetailFrm extends JFrame implements ActionListener {

    private JButton btnAddMember, btnListRequest;
    private JTable tblResult;
    private ClientCtr mySocket;
    private Group group;
    private ArrayList<User> listMembers;

    public ListGroupDetailFrm(ClientCtr socket, Group groupN) {
        super("Video call app");
        mySocket = socket;
        group = groupN; 
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(null); 

        JLabel title = new JLabel("Group: " + group.getName());
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(200, 30, 200, 30));
        pnMain.add(title, null); 
        tblResult = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblResult);
        scrollPane.setBounds(50, 130, 500, 200);
        tblResult.setFillsViewportHeight(false); 
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_MEMBER_IN_GROUPS, this));

//        //--------------- 
//        String[] columnNames = {"Group member"};
//        String[][] value = new String[listMembers.size()][columnNames.length];
//        for (int i = 0; i < listMembers.size(); i++) {
//            value[i][0] = listMembers.get(i).getName();
////            value[i][1] = listGroups.get(i).getSourceUsername();
////            value[i][2] = listGroups.get(i).getStartedAt() + "";
//        }
//        DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                //unable to edit cells
//                return false;
//            }
//        };
//        tblResult.setModel(tableModel);
//        //------------------
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
//                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button
//
//                // *Checking the row or column is valid or not
//                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
//
////                    dispose();
//                }
            }
        });
 

        btnListRequest = new JButton("List requests");
        btnListRequest.setBounds(50, 80, 141, 31);
        pnMain.add(btnListRequest);
        btnListRequest.addActionListener(this);

        btnAddMember = new JButton("Add Member");
        btnAddMember.setBounds(200, 80, 141, 31);
        pnMain.add(btnAddMember);
        btnAddMember.addActionListener(this);

        if (mySocket.getInfo().getId() == groupN.getCreatedBy().getId()) {
            pnMain.add(btnListRequest);
            pnMain.add(btnAddMember);
        } else {
            pnMain.remove(btnListRequest);
            pnMain.remove(btnAddMember);
        }

        pnMain.add(scrollPane);

        this.add(pnMain);
        this.setSize(600, 500);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnListRequest)) {
            ListRequestJoinGroup addmemberview = new ListRequestJoinGroup(mySocket);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_REQUEST_JOIN_GROUP, group));
            addmemberview.setVisible(true);
            dispose();

        } else if (btnClicked.equals(btnAddMember)) {
            AddMemberFrm addmemberview = new AddMemberFrm(mySocket, group);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_FRIEND_TO_ADD_GROUP, null));
            addmemberview.setVisible(true);
            dispose();
        }
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        if (data.getData() instanceof ArrayList<?>) {
            listMembers = (ArrayList<User>) data.getData();
            String[] columnNames = {"Name", "Username", "Time"};
            String[][] value = new String[listMembers.size()][columnNames.length];
            for (int i = 0; i < listMembers.size(); i++) {
                value[i][0] = listMembers.get(i).getUsername();
            }
            DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    //unable to edit cells
                    return false;
                }
            };
            tblResult.setModel(tableModel);
        } else {
            JOptionPane.showMessageDialog(this, "Error when receiving!");
        }
    }
}
