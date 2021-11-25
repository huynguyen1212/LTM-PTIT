package view;

import control.ClientCtr;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.ObjectWrapper;
import model.User;

public class SignupFrm extends JFrame implements ActionListener {

    private JTextField txtUsername, txtName, txtAddress;
    private JPasswordField txtPassword;
    private JButton btnSignup, btnLogin;
    private ClientCtr mySocket;

    public SignupFrm(ClientCtr socket) {
        super("Video call app");
        mySocket = socket;
        JPanel content = new JPanel();
        content.setLayout(null);

        JLabel title = new JLabel("Signup");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(270, 50, 200, 30));
        content.add(title, null);

        JLabel label1 = (new JLabel("Username:"));
        label1.setBounds(new Rectangle(132, 107, 96, 27));
        content.add(label1);
        txtUsername = new JTextField(15);
        txtUsername.setBounds(new Rectangle(248, 106, 258, 30));
        content.add(txtUsername);

        JLabel label2 = new JLabel("Password:");
        label2.setBounds(new Rectangle(132, 156, 96, 27));
        content.add(label2);
        txtPassword = new JPasswordField(15);
        txtPassword.setBounds(new Rectangle(248, 156, 258, 30));
        txtPassword.setEchoChar('*');
        content.add(txtPassword);

        JLabel label3 = (new JLabel("Name:"));
        label3.setBounds(new Rectangle(132, 207, 96, 27));
        content.add(label3);
        txtName = new JTextField(15);
        txtName.setBounds(new Rectangle(248, 206, 258, 30));
        content.add(txtName);

        JLabel label4 = (new JLabel("Address:"));
        label4.setBounds(new Rectangle(132, 257, 96, 27));
        content.add(label4);
        txtAddress = new JTextField(15);
        txtAddress.setBounds(new Rectangle(248, 256, 258, 30));
        content.add(txtAddress);

        btnSignup = new JButton("Signup");
        btnSignup.setBounds(267, 310, 136, 31);
        content.add(btnSignup);
        btnSignup.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(625, 428));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SIGNUP_USER, this));
        
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

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnSignup)) {
            User user = new User();
            user.setUsername(txtUsername.getText());
            user.setPassword(String.valueOf(txtPassword.getPassword()));
            user.setName(txtName.getText());
            user.setAddress(txtAddress.getText());

            //sending data
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.SIGNUP_USER, user));

        } else if (btnClicked.equals(btnLogin)) {
            LoginFrm login = new LoginFrm(mySocket);
            login.setVisible(true);
            this.dispose();
        }
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        if (data.getData().equals("true")) {
            JOptionPane.showMessageDialog(this, "Add succesfully!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error when adding!");
        }
    }
}
