/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ClientCtr;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import model.ObjectWrapper;
import model.PrivateCall;

/**
 *
 * @author Little Coconut
 */
public class HomePageFrm extends JFrame implements ActionListener {

    private JButton btnCreateUser, btnCreateBook, btnCreateReader;
    private ClientCtr mySocket;

    public HomePageFrm(ClientCtr socket) {
        super("");
        mySocket = socket;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(null);

        JLabel title = new JLabel("Home page");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(260, 50, 200, 30));
        pnMain.add(title, null);

        btnCreateUser = new JButton("Thêm nhân viên");
        btnCreateUser.setBounds(210, 112, 206, 33);
        pnMain.add(btnCreateUser);
        btnCreateUser.addActionListener(this);

        btnCreateBook = new JButton("Thêm truyện");
        btnCreateBook.setBounds(210, 233, 206, 33);
        pnMain.add(btnCreateBook);
        btnCreateBook.addActionListener(this);

        btnCreateReader = new JButton("Thêm đọc giả");
        btnCreateReader.setBounds(210, 172, 206, 33);
        pnMain.add(btnCreateReader);
        btnCreateReader.addActionListener(this);

        this.setContentPane(pnMain);
        this.setSize(625, 360);
        this.setLocation(200, 10);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.INCOMING_CALL, this)); 
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnCreateUser)) {
            (new CreateUserFrm(mySocket)).setVisible(true);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CREATE_USER, null));
        } else if (btnClicked.equals(btnCreateBook)) {
            (new CreateBookFrm(mySocket)).setVisible(true);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CREATE_BOOK, null));
        } else if (btnClicked.equals(btnCreateReader)) {
            (new CreateReaderFrm(mySocket)).setVisible(true);
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CREATE_READER, null));
        }

    }

    public void receivedDataProcessing(ObjectWrapper data) {
        PrivateCall privateCall = (PrivateCall) data.getData();

        int output = JOptionPane.showConfirmDialog(this,
                "Incoming call from xxx. Accept?", "Incoming call",
                JOptionPane.YES_NO_OPTION);
        if (output == JOptionPane.YES_OPTION) {
            System.out.println("Accept call");
        } else {
//                } else if (output == JOptionPane.NO_OPTION) {
            System.out.println("Refuse call");
        }

//        }
//        else if (data.getData().equals("ok")) {
//            JOptionPane.showMessageDialog(this, "Update succesfully!"); 
//            this.dispose();
//        } else {
//            JOptionPane.showMessageDialog(this, "Error when updating!");
//        }
    }
}
