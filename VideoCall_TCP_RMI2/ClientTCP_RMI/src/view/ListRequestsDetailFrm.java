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
import model.Friend;
import model.ObjectWrapper;
import model.User;

public class ListRequestsDetailFrm extends JFrame implements ActionListener {

    private JButton btnDelete, btnAccept;
    private ClientCtr mySocket;
    private Friend friend;

    public ListRequestsDetailFrm(ClientCtr socket, Friend friendN) {
        super("Video call app");
        mySocket = socket;
        friend = friendN;
        JPanel content = new JPanel();
        content.setLayout(null);

        JLabel title = new JLabel("DETAIL FRIEND");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(250, 50, 200, 30));
        content.add(title, null);

        JLabel label1 = (new JLabel("Name:"));
        label1.setBounds(new Rectangle(190, 107, 96, 27));
        content.add(label1);
        JLabel label11 = (new JLabel(friend.getSource().getName()));
        label11.setBounds(new Rectangle(310, 106, 258, 30));
        content.add(label11);

        JLabel label2 = new JLabel("Username:");
        label2.setBounds(new Rectangle(190, 156, 96, 27));
        content.add(label2);
        JLabel label22 = new JLabel(friend.getSource().getUsername());
        label22.setBounds(new Rectangle(310, 156, 258, 30));
        content.add(label22);

        JLabel label3 = (new JLabel("Time:"));
        label3.setBounds(new Rectangle(190, 207, 96, 27));
        content.add(label3);
        JLabel label33 = (new JLabel(friend.getStartedAt() + ""));
        label33.setBounds(new Rectangle(310, 206, 258, 30));
        content.add(label33);

        btnDelete = new JButton("Refuse");
        btnDelete.setBounds(170, 270, 141, 31);
        content.add(btnDelete);
        btnDelete.addActionListener(this);

        btnAccept = new JButton("Accept ");
        btnAccept.setBounds(351, 270, 141, 31);
        content.add(btnAccept);
        btnAccept.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(625, 428));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_REFUSE_REQUEST, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_REQUEST, this));
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnDelete)) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.REFUSE_REQUEST, friend.getId()));
        } else if (btnClicked.equals(btnAccept)) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.ACCEPT_REQUEST, friend.getId()));
        }
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        if (data.getData().equals("false")) {
            JOptionPane.showMessageDialog(this, "Error!");
        } else {
            JOptionPane.showMessageDialog(this, "Successful!");
            this.dispose();
        }
    }
}
