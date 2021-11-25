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
//import com.github.sarxos.webcam.Webcam;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.User;
import control.ClientCtr;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import model.ObjectWrapper;

public class VideoFrm extends JFrame implements ActionListener { 
    private ClientCtr mySocket;

    public VideoFrm() {
    }

    public VideoFrm(ClientCtr socket) {
        super("Video call app");
        mySocket = socket;
        JPanel content = new JPanel();
        content.setLayout(null);

        
        
        JLabel title = new JLabel("Call");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(270, 50, 200, 30));
        content.add(title, null);
        
        JLabel img_client = (new JLabel());
        img_client.setBounds(new Rectangle(132,107,300,300));
        content.add(img_client); 
        
//        ImageIcon ic;
//        BufferedImage br;
//        Webcam cam = Webcam.getDefault();
//        cam.open();
//        while (true) {
//            br = cam.getImage();
//            ic = new ImageIcon(br);
////            mySocket.sendData(ic);
////            out.flush();
//            img_client.setIcon(ic);
//        }
 
        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(625, 328));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, this));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (mySocket != null) {
                    mySocket.setInCall(false);  
                } 
            }
        });
        
//        ImageIcon ic;
//        BufferedImage br;
//        Webcam cam = Webcam.getDefault();
//        cam.open();
//        while (true) {
//            br = cam.getImage();
//            ic = new ImageIcon(br);
////            mySocket.sendData(ic);
////            out.flush();
//            img_client.setIcon(ic);
//        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
//        if (btnClicked.equals(btnLogin)) {
//            //pack the entity
//            User user = new User();
//            user.setUsername(txtUsername.getText()); 
//            user.setPassword(String.valueOf(txtPassword.getPassword()));
//
//            //sending data
//            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LOGIN_USER, user)); 
//
//        }
    }

    public void receivedDataProcessing(ObjectWrapper data) {

        if (data.getData().equals("accept")) {
            JOptionPane.showMessageDialog(this, "Fiend joined call");
        } else { 
             JOptionPane.showMessageDialog(this, "Friend refused call");
            this.dispose(); 
        }
    }
}
