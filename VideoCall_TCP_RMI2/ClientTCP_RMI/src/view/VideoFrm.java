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
import javax.swing.SwingUtilities;
import model.ObjectWrapper;

public class VideoFrm extends JFrame implements ActionListener {

    private ClientCtr mySocket;
    BufferedImage br;
    ImageIcon ic;
    JLabel img_client2 = (new JLabel());
    Webcam cam = Webcam.getDefault();

    public VideoFrm() {
        super("Video call app");
        JPanel content = new JPanel();
        content.setLayout(null);

        JLabel title = new JLabel("Call");
        title.setFont(new java.awt.Font("Dialog", 1, 20));
        title.setBounds(new Rectangle(270, 50, 200, 30));
        content.add(title, null);

        JLabel img_client = (new JLabel());
        img_client.setBounds(new Rectangle(300, 107, 500, 500));
        content.add(img_client);

        img_client2.setBounds(new Rectangle(500, 107, 500, 500));
        content.add(img_client2);

        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(1300, 700));
        this.setResizable(false);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.updateComponentTreeUI(VideoFrm.this);
            }
        }, 0, 100);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mySocket.isInCall() == true) {
                    mySocket.setInCall(false);
                }
                dispose();
            }
        });
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
        img_client.setBounds(new Rectangle(300, 107, 500, 500));
        content.add(img_client);

//        img_client2.setBounds(new Rectangle(500, 107, 500, 500));
//        content.add(img_client2);
        this.setContentPane(content);
        this.pack();
        this.setSize(new Dimension(1300, 700));

        this.setResizable(false);

        cam.open();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                br = cam.getImage();
                ic = new ImageIcon(br);
                img_client.setIcon(ic);

//                mySocket.sendData(new ObjectWrapper(ObjectWrapper.SENT_VIDEO, ic));
            }
        }, 0, 100);
//        actionSendImg();
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.RECIEVE_VIDEO, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, this));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mySocket.setInCall(false);
                cam.close();
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
    }

    public void actionSendImg() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("logggg tesst");
                mySocket.sendData(new ObjectWrapper(ObjectWrapper.SENT_VIDEO, ic));
            }
        }, 0, 100);
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        System.out.println("lllll");
        if (data.getData().equals("accept")) {
            System.out.println("accept in video");
            JOptionPane.showMessageDialog(this, "Friend joined call");
        } else if (data.getData().equals("busy")) {
            System.out.println("busy in video");
            JOptionPane.showMessageDialog(this, "Friend is busy!");
            mySocket.setInCall(false);
            this.dispose();
        } else if (data.getData().equals("busy_ok")) {
            System.out.println("busy_ok in video");
            JOptionPane.showMessageDialog(this, "Inform busy success");
            mySocket.setInCall(false);
            this.dispose();
        } else if (data.getData().equals("accep_ok")) {
            System.out.println("accep_ok in video");
            (new VideoFrm(mySocket)).setVisible(true);
        } else {
            System.out.println("refused in video");
            JOptionPane.showMessageDialog(this, "Friend refused call");
            mySocket.setInCall(false);
            this.dispose();
        }
    }

    public void receivedDataProcessingInClient() {
        (new VideoFrm()).setVisible(true);
    }

    public void receivedDataProcessingForImg2(ObjectWrapper data) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ic = (ImageIcon) data.getData();
                img_client2.setIcon(ic);
            }
        }, 0, 100);
    }
}
