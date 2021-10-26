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
import control.ClientCtr;

public class EditCustomerFrm extends JFrame implements ActionListener {

    private Customer customer;
    private JTextField txtId, txtName, txtIdcard, txtAddress, txtEmail, txtTel, txtNote;
    private JButton btnUpdate, btnReset;
    private ClientCtr myRemoteObject;

    public EditCustomerFrm(ClientCtr ro, Customer client) {
        super("Edit a customer");
        myRemoteObject = ro;
        this.customer = client;

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Edit a customer information");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

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
        content.setLayout(new GridLayout(8, 2));
        content.add(new JLabel("Customer ID:"));
        content.add(txtId);
        content.add(new JLabel("Name:"));
        content.add(txtName);
        content.add(new JLabel("Idcard:"));
        content.add(txtIdcard);
        content.add(new JLabel("Address:"));
        content.add(txtAddress);
        content.add(new JLabel("Email:"));
        content.add(txtEmail);
        content.add(new JLabel("Tel:"));
        content.add(txtTel);
        content.add(new JLabel("Note:"));
        content.add(txtNote);
        content.add(btnUpdate);
        content.add(btnReset);
        pnMain.add(content);
        btnUpdate.addActionListener(this);
        btnReset.addActionListener(this);

        initForm();
        this.setContentPane(pnMain);
        this.setSize(600, 300);
        this.setLocation(200, 10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initForm() {
        if (customer != null) {
            txtId.setText(customer.getId() + "");
            txtName.setText(customer.getName());
            txtIdcard.setText(customer.getIdCard());
            txtAddress.setText(customer.getAddress());
            txtEmail.setText(customer.getEmail());
            txtTel.setText(customer.getTel());
            txtNote.setText(customer.getNote());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnReset)) {
            initForm();
            return;
        }
        if (btnClicked.equals(btnUpdate)) {
            customer.setName(txtName.getText());
            customer.setIdCard(txtIdcard.getText());
            customer.setAddress(txtAddress.getText());
            customer.setEmail(txtEmail.getText());
            customer.setTel(txtTel.getText());
            customer.setNote(txtNote.getText());

            //call the remote function
            if (myRemoteObject.remoteEditCustomer(customer) == null) {
                JOptionPane.showMessageDialog(this, "Error in connecting to the RMI server!");
                this.dispose();
            } else if (myRemoteObject.remoteEditCustomer(customer).equals("ok")) {
                JOptionPane.showMessageDialog(this, "Update succesfully!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error when updating!");
            }
        }
    }
}
