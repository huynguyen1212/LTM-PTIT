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
 
import model.Customer;
import model.ObjectWrapper;
import control.ClientCtr;
 
public class SearchCustomerFrm extends JFrame implements ActionListener{
    private ArrayList<Customer> listClient;
    private JTextField txtKey;
    private JButton btnSearch;
    private JTable tblResult;
    private ClientCtr mySocket;
     
    public SearchCustomerFrm(ClientCtr socket){
        super("Search customer to edit");
        mySocket = socket;
        listClient = new ArrayList<Customer>();
         
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);       
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
         
        JLabel lblHome = new JLabel("Search a customer to edit");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);  
        lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0,20)));
         
        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1,BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width-5, 20);
        pn1.add(new JLabel("Client name: "));
        txtKey = new JTextField();
        pn1.add(txtKey);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        pn1.add(btnSearch);
        pnMain.add(pn1);
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
 
        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2,BoxLayout.Y_AXIS));     
        tblResult = new JTable();
        JScrollPane scrollPane= new  JScrollPane(tblResult);
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
                    for(ObjectWrapper func: mySocket.getActiveFunction())
                        if(func.getData() instanceof EditCustomerFrm) {
                            ((EditCustomerFrm)func.getData()).dispose();
                            existed = func;
                        }
                    if(existed != null)
                        mySocket.getActiveFunction().remove(existed);
                     
                    //create new instance
                    (new EditCustomerFrm(mySocket, listClient.get(row))).setVisible(true);
                    dispose();
                }
            }
        });
 
        pn2.add(scrollPane);
        pnMain.add(pn2);    
        this.add(pnMain);
        this.setSize(600,300);              
        this.setLocation(200,10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_CUSTOMER, this));
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton)e.getSource();
        if(btnClicked.equals(btnSearch)){
            if((txtKey.getText() == null)||(txtKey.getText().length() == 0))
                return;
            //send data to the server
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_CUSTOMER_BY_NAME, txtKey.getText().trim()));
        }
    }
     
    /**
     * Treatment of search result received from the server
     * @param data
     */
    public void receivedDataProcessing(ObjectWrapper data) {
        if(data.getData() instanceof ArrayList<?>) {
            listClient = (ArrayList<Customer>)data.getData();
 
            String[] columnNames = {"Id", "Name", "idCard", "Address", "Email", "Tel", "Note"};
            String[][] value = new String[listClient.size()][columnNames.length];
            for(int i=0; i<listClient.size(); i++){
                value[i][0] = listClient.get(i).getId() +"";
                value[i][1] = listClient.get(i).getName();
                value[i][2] = listClient.get(i).getIdCard();
                value[i][3] = listClient.get(i).getAddress();
                value[i][4] = listClient.get(i).getEmail();
                value[i][5] = listClient.get(i).getTel();
                value[i][6] = listClient.get(i).getNote();
            }
            DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                   //unable to edit cells
                   return false;
                }
            };
            tblResult.setModel(tableModel);
        }else {
            tblResult.setModel(null);
        }
    }
}