package view;

import com.github.sarxos.webcam.Webcam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.User;
import control.ClientCtr;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import model.ObjectWrapper;

public class VideoFrm extends JFrame implements ActionListener {

    private ClientCtr mySocket;
    BufferedImage br;
    ImageIcon ic;

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
        img_client.setBounds(new Rectangle(300, 107, 1000, 400));
        content.add(img_client);

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(1300, 700));
        this.setResizable(false);

        Webcam cam = Webcam.getDefault();
        
        cam.open();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                br = cam.getImage();
                ic = new ImageIcon(br);
                img_client.setIcon(ic);
                
                mySocket.sendData(new ObjectWrapper(ObjectWrapper.SENT_VIDEO, ic));
            }
        }, 0, 100);
        
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, this));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mySocket.setInCall(false);
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        if (data.getData().equals("accept")) {
            JOptionPane.showMessageDialog(this, "Friend joined call");
        } else if (data.getData().equals("busy")) {
            JOptionPane.showMessageDialog(this, "Friend is busy!");
            mySocket.setInCall(false);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Friend refused call");
            mySocket.setInCall(false);
            this.dispose();
        }
    }
}
