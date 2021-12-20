/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ClientCtr;
import java.awt.Dimension;
import java.awt.Rectangle;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Friend;
import model.Group;
import model.ObjectWrapper;
import model.User;

/**
 *
 * @author Little Coconut
 */
public class ListGroupFrm extends JFrame implements ActionListener {
    
    private ArrayList<Group> listGroups;
    private JButton createGroup;
    private JTable tblResult;
    private ClientCtr mySocket;
    
    public ListGroupFrm(ClientCtr socket) {
        super("Video call app");
        mySocket = socket;
        
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
//        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.setLayout(null);
//        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel title = new JLabel("LIST GROUPS");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(230, 30, 200, 30));
        pnMain.add(title, null);
        
        createGroup = new JButton("Create Group");
        createGroup.setBounds(50, 80, 141, 31);
        pnMain.add(createGroup);
        createGroup.addActionListener(this);
        
//        JPanel pn2 = new JPanel();
//        pn2.setBounds(150, 50, 400, 300);
//        pn2.setLayout(new BoxLayout(pn2, BoxLayout.Y_AXIS));
        tblResult = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblResult);
        scrollPane.setBounds(50, 130, 500, 200);
        tblResult.setFillsViewportHeight(false);
//        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 150));
        
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button

                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    //search and delete all existing previous view
//                    ObjectWrapper existed = null;
//                    for(ObjectWrapper func: mySocket.getActiveFunction())
//                        if(func.getData() instanceof ListRequestsDetailFrm) {
//                            ((ListRequestsDetailFrm)func.getData()).dispose();
//                            existed = func;
//                        }
//                    if(existed != null)
//                        mySocket.getActiveFunction().remove(existed);

                    //create new instance
//                    ArrayList<User> listMembers = mySocket.getMemberInGroup(listGroups.get(row).getId());
                    (new ListGroupDetailFrm(mySocket, listGroups.get(row))).setVisible(true);
                    mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_MEMBER_IN_GROUPS, listGroups.get(row)));
//                    dispose();
                }
            }
        });
        
//        pn2.add(scrollPane);
        pnMain.add(scrollPane);
        this.add(pnMain);
        this.setSize(600, 400);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LIST_GROUPS, this));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(createGroup)) {
            (new CreateGroupFrm(mySocket)).setVisible(true);
            this.dispose();
        }
        
    }
    
    public void receivedDataProcessing(ObjectWrapper data) {        
        if (data.getData() instanceof ArrayList<?>) {
            listGroups = (ArrayList<Group>) data.getData();            
            String[] columnNames = {"Group name"};
            String[][] value = new String[listGroups.size()][columnNames.length];
            for (int i = 0; i < listGroups.size(); i++) {
                value[i][0] = listGroups.get(i).getName();
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
