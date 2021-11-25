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
//        listMembers = listData;

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel title = new JLabel("LIST GROUPS");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(270, 250, 200, 30));
        pnMain.add(title, null);

        JLabel name = new JLabel("Group: " + groupN.getName());
        name.setBounds(170, 310, 141, 31);
        pnMain.add(name);

        JPanel pn2 = new JPanel();
//        pn2.setLayout(new BoxLayout(pn2, BoxLayout.Y_AXIS));
        tblResult = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 150));
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

        pn2.add(scrollPane);
//        pnMain.add(pn2);

        btnListRequest = new JButton("List requests");
        btnListRequest.setBounds(170, 310, 141, 31);
        pnMain.add(btnListRequest);
        btnListRequest.addActionListener(this);

        btnAddMember = new JButton("Add Member");
        btnAddMember.setBounds(351, 310, 141, 31);
        pnMain.add(btnAddMember);
        btnAddMember.addActionListener(this);

        if (mySocket.getUserId() == groupN.getCreatedBy()) {
            pnMain.add(btnListRequest);
            pnMain.add(btnAddMember);
        } else {
            pnMain.remove(btnListRequest);
            pnMain.remove(btnAddMember);
        }

        pnMain.add(pn2);

        this.add(pnMain);
        this.setSize(600, 450);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        super("Video call app");
//        mySocket = socket;
//        group = groupN;
//        listMembers = list;
//        System.out.println(listMembers);
//        JPanel content = new JPanel();
//        content.setLayout(null);
//
//        JLabel title = new JLabel("DETAIL GROUP");
//        title.setFont(new java.awt.Font("Dialog", 1, 20));
//        title.setBounds(new Rectangle(250, 50, 200, 30));
//        content.add(title, null);
//        JLabel label1 = (new JLabel("Name:"));
//        label1.setBounds(new Rectangle(190, 107, 96, 27));
//        content.add(label1);
//        JLabel label11 = (new JLabel(group.getName()));
//        label11.setBounds(new Rectangle(310, 106, 258, 30));
//        content.add(label11); 
//        ------
//        JPanel pn2 = new JPanel();
//        pn2.setLayout(new BoxLayout(pn2, BoxLayout.Y_AXIS));
//        tblResult = new JTable();
//        JScrollPane scrollPane = new JScrollPane(tblResult);
//        tblResult.setFillsViewportHeight(false);
//        scrollPane.setPreferredSize(new Dimension(200, 150));
//        String[] columnNames = {"Member"};
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
//        pn2.add(scrollPane);
//        content.add(pn2); 
//------
//        btnDelete = new JButton("Delete Group");
//        btnDelete.setBounds(170, 310, 141, 31);
//        content.add(btnDelete);
//        btnDelete.addActionListener(this);
//
//        btnAddMember = new JButton("Add Member");
//        btnAddMember.setBounds(351, 310, 141, 31);
//        content.add(btnAddMember);
//        btnAddMember.addActionListener(this);
//
//        btnLeave = new JButton("Leave Group");
//        btnLeave.setBounds(260, 310, 141, 31);
//        content.add(btnLeave);
//        btnLeave.addActionListener(this);
//
//        if (mySocket.getUserId() == group.getCreatedBy()) {
//            content.remove(btnLeave);
//            content.add(btnDelete);
//            content.add(btnAddMember);
//        } else {
//            content.add(btnLeave);
//            content.remove(btnDelete);
//            content.remove(btnAddMember);
//        }
//        this.setContentPane(content);
//        this.pack();
//        this.setSize(new Dimension(625, 428));
//        this.setLocation(200, 10);
//        this.setResizable(false);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        pnMain.add(pn2);
//        this.add(pnMain);
//        this.setSize(600, 300);
//        this.setLocation(200, 10);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnListRequest)) {
//            mySocket.sendData(new ObjectWrapper(ObjectWrapper.DELETE_GROUP, group.getId()));
//            //receive
//            ObjectWrapper data = mySocket.receiveData();
//            if (data.getPerformative() == ObjectWrapper.REPLY_DELETE_GROUP) {
//                String result = (String) data.getData();
//                if (result.equals("false")) {
//                    JOptionPane.showMessageDialog(this, "Error!");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Successful!");
//                    this.dispose();
//                }
//
//            }
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_REQUEST_JOIN_GROUP, group.getId()));
            ListRequestJoinGroup addmemberview = new ListRequestJoinGroup(mySocket);
            addmemberview.setVisible(true);

        } else if (btnClicked.equals(btnAddMember)) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_FRIEND_TO_ADD_GROUP, null));
            AddMemberFrm addmemberview = new AddMemberFrm(mySocket, group);
            addmemberview.setVisible(true);
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
