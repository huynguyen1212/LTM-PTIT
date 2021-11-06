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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.User;
import control.ClientCtr;
import javax.swing.JOptionPane;
import model.Friend;

public class SearchUserFrm extends JFrame implements ActionListener {

    private ArrayList<User> listUser;
    private JTextField txtKey;
    private JButton btnSearch;
    private JTable tblResult;
    private ClientCtr mySocket;

    public SearchUserFrm(ClientCtr socket) {
        super("Video call app");
        mySocket = socket;
//        listUser = new ArrayList<User>();

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Search user");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1, BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width - 5, 20);
        pn1.add(new JLabel("Username: "));
        txtKey = new JTextField();
        pn1.add(txtKey);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        pn1.add(btnSearch);
        pnMain.add(pn1);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2, BoxLayout.Y_AXIS));
        tblResult = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 250));

        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button
                Friend dataToSend = new Friend();
                dataToSend.setSourceId(mySocket.getUserId());
                dataToSend.setTargetId(listUser.get(row).getId());
//                mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_USER_DETAIL, dataToSend));
//                receiving
//                ObjectWrapper data = mySocket.receiveData();
//                if (data.getPerformative() == ObjectWrapper.REPLY_SEARCH_USER_DETAIL) {
//                    Friend friend = (Friend) data.getData(); 
                Friend friend = (Friend) mySocket.checkFriend(listUser.get(row).getId());
                if (friend == null) {

                } else {
                    (new SearchUserDetailFrm(mySocket, listUser.get(row), friend)).setVisible(true);
                    dispose();
                }

//                    
//                } 
            }
        });

        pn2.add(scrollPane);
        pnMain.add(pn2);
        this.add(pnMain);
        this.setSize(600, 300);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnSearch)) {
            if ((txtKey.getText() == null) || (txtKey.getText().length() == 0)) {
                return;
            }

            listUser = (ArrayList<User>) mySocket.searchUser(txtKey.getText());

            if (listUser == null) {
                JOptionPane.showMessageDialog(this, "Error connecting to RMI server!");
                dispose();
            } else {
                String[] columnNames = {"Username"};
                String[][] value = new String[listUser.size()][columnNames.length];
                for (int i = 0; i < listUser.size(); i++) {
//                value[i][0] = listUser.get(i).getName();
                    value[i][0] = listUser.get(i).getUsername();
                }
                DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //unable to edit cells
                        return false;
                    }
                };
                tblResult.setModel(tableModel);
            }
        }
    }

}
