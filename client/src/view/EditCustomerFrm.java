package view;
 
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
import model.Customer;
import model.ObjectWrapper;
import control.ClientCtr;
 
public class EditCustomerFrm extends JFrame implements ActionListener{
    private Customer client;
    private JTextField txtId, txtName, txtIdcard, txtAddress, txtEmail, txtTel, txtNote;
    private JButton btnUpdate, btnReset;
    private ClientCtr mySocket;
     
     
    public EditCustomerFrm(ClientCtr socket, Customer client){
        super("Edit a customer");
        mySocket = socket;
        this.client = client;
         
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);       
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
         
        JLabel lblHome = new JLabel("Edit a customer information");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);  
        lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0,20)));
         
        txtId = new JTextField(15);
        txtId.setEditable(false);
        txtName = new JTextField(15);
        txtIdcard = new JTextField(15);
        txtAddress = new JTextField(15);
        txtEmail = new JTextField(15);
        txtTel = new JTextField(15);
        txtNote = new JTextField(15);
        btnUpdate = new JButton("Update");
        btnReset = new JButton("Reset");
         
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(8,2));
        content.add(new JLabel("Customer ID:"));    content.add(txtId);
        content.add(new JLabel("Name:"));   content.add(txtName);
        content.add(new JLabel("Idcard:"));     content.add(txtIdcard);
        content.add(new JLabel("Address:"));    content.add(txtAddress);
        content.add(new JLabel("Email:"));  content.add(txtEmail);
        content.add(new JLabel("Tel:"));    content.add(txtTel);
        content.add(new JLabel("Note:"));   content.add(txtNote);
        content.add(btnUpdate);     content.add(btnReset);
        pnMain.add(content);          
        btnUpdate.addActionListener(this);
        btnReset.addActionListener(this);
         
        initForm();     
        this.setContentPane(pnMain);
        this.setSize(600,300);              
        this.setLocation(200,10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_CUSTOMER, this));
    }
     
    private void initForm(){
        if(client != null){
            txtId.setText(client.getId()+"");
            txtName.setText(client.getName());
            txtIdcard.setText(client.getIdCard());
            txtAddress.setText(client.getAddress());
            txtEmail.setText(client.getEmail());
            txtTel.setText(client.getTel());
            txtNote.setText(client.getNote());
        }
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton)e.getSource();
        if(btnClicked.equals(btnReset)){
            initForm();
            return;
        }
        if(btnClicked.equals(btnUpdate)){
            client.setName(txtName.getText());
            client.setIdCard(txtIdcard.getText());
            client.setAddress(txtAddress.getText());
            client.setEmail(txtEmail.getText());
            client.setTel(txtTel.getText());
            client.setNote(txtNote.getText());
             
            //send data to the server
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.EDIT_CUSTOMER, client));
        }
    }
     
    /**
     * Treatment of search result received from the server
     * @param data
     */
    public void receivedDataProcessing(ObjectWrapper data) {
        if(data.getData().equals("ok")) {
            JOptionPane.showMessageDialog(this, "Update succesfully!");
            /*for(DataTO func: mySocket.getActiveFunction())
                if(func.getData().equals(this))
                    mySocket.getActiveFunction().remove(func);*/
            this.dispose();
        }
        else {
            JOptionPane.showMessageDialog(this, "Error when updating!");
        }
    }
}