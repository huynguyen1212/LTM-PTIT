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
import model.Group;
import model.ObjectWrapper;
import model.User;

public class ListGroupDetailFrm2 extends JFrame implements ActionListener {

    private JButton btnDelete, btnAccept;
    private ClientCtr mySocket;
    private Group group;

    public ListGroupDetailFrm2(ClientCtr socket, Group groupN) {
        super("Video call app");
        mySocket = socket;
        group = groupN;
        JPanel content = new JPanel();
        content.setLayout(null);

        JLabel title = new JLabel("DETAIL FRIEND");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(250, 50, 200, 30));
        content.add(title, null);

        JLabel label1 = (new JLabel("Name:"));
        label1.setBounds(new Rectangle(190, 107, 96, 27));
        content.add(label1);
        JLabel label11 = (new JLabel(group.getName()));
        label11.setBounds(new Rectangle(310, 106, 258, 30));
        content.add(label11);

        JLabel label2 = new JLabel("Creater:");
        label2.setBounds(new Rectangle(190, 156, 96, 27));
        content.add(label2);
        JLabel label22 = new JLabel(group.getCreatedBy()+ "");
        label22.setBounds(new Rectangle(310, 156, 258, 30));
        content.add(label22);

        JLabel label3 = (new JLabel("Time:"));
        label3.setBounds(new Rectangle(190, 207, 96, 27));
        content.add(label3);
        JLabel label33 = (new JLabel(group.getCreatedAt()+ ""));
        label33.setBounds(new Rectangle(310, 206, 258, 30));
        content.add(label33);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(170, 270, 141, 31);
        content.add(btnDelete);
        btnDelete.addActionListener(this);

        btnAccept = new JButton("Leave");
        btnAccept.setBounds(351, 270, 141, 31);
        content.add(btnAccept);
        btnAccept.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(625, 428));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_GROUP, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_GROUP, this));
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnDelete)) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.DELETE_GROUP, group.getId()));
        } else if (btnClicked.equals(btnAccept)) {
            Object o = new Object();
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LEAVE_GROUP, group.getId()));
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
