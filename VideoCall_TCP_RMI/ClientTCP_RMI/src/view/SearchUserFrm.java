package view;

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
import model.ObjectWrapper;
import control.ClientCtr;
import javax.swing.JOptionPane;

public class SearchUserFrm extends JFrame implements ActionListener {

    private ArrayList<User> listUser;
    private JTextField txtKey;
    private JButton btnSearch;
    private JTable tblResult;
    private ClientCtr mySocket;

    public SearchUserFrm(ClientCtr socket) {
        super("Video call app");
        mySocket = socket;
        listUser = new ArrayList<User>();

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

                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                    //search and delete all existing previous view
                    ObjectWrapper existed = null;
                    for (ObjectWrapper func : mySocket.getActiveFunction()) {
                        if (func.getData() instanceof SearchUserDetailFrm) {
                            ((SearchUserDetailFrm) func.getData()).dispose();
                            existed = func;
                        }
                    }
                    if (existed != null) {
                        mySocket.getActiveFunction().remove(existed);
                    }

                    //create new instance
                    (new SearchUserDetailFrm(mySocket, listUser.get(row))).setVisible(true);
                    mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_USER_DETAIL, listUser.get(row).getId()));
//                    dispose();
                }
            }
        });

        pn2.add(scrollPane);
        pnMain.add(pn2);
        this.add(pnMain);
        this.setSize(600, 300);
        this.setLocation(200, 10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER, this));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnSearch)) {
            if ((txtKey.getText() == null) || (txtKey.getText().length() == 0)) {
                return;
            }
            //send data to the server
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_USER, txtKey.getText().trim()));
        }
    }

    /**
     * Treatment of search result received from the server
     *
     * @param data
     */
    public void receivedDataProcessing(ObjectWrapper data) {
        if (data.getData() instanceof ArrayList<?>) {
            listUser = (ArrayList<User>) data.getData();

            String[] columnNames = {"Username", "Status"};
            String[][] value = new String[listUser.size()][columnNames.length];
            for (int i = 0; i < listUser.size(); i++) {
//                value[i][0] = listUser.get(i).getName();
                value[i][0] = listUser.get(i).getUsername();
                if (listUser.get(i).getIsOnline() == 0) {
                    value[i][1] = "Offline";
                } else {
                    value[i][1] = "Online";
                }
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
//            tblResult.setModel(null);
            String[] columnNames = {"Username", "Status"};
            String[][] value = new String[0][columnNames.length];
            DefaultTableModel tableModel = new DefaultTableModel(value, columnNames);
            tblResult.setModel(tableModel);
        }
    }
}
