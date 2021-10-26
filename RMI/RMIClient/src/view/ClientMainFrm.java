package view;
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
import control.ClientCtr;
 
 
public class ClientMainFrm extends JFrame implements ActionListener{
    private JMenuBar mnbMain;
    private JMenu mnUser, mnClient;
    private JMenuItem mniLogin, mniEditClient;  
    private JTextField txtServerHost, txtServerPort, txtService;
    private JButton btnStart;   
    private JTextArea mainText;
    private ClientCtr myControl;
     
    public ClientMainFrm(){
        super("RMI client view");
         
         
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
         
        mnbMain = new JMenuBar();
        mnUser = new JMenu("User");
        mniLogin = new JMenuItem("Login");
        mniLogin.addActionListener(this);
        mnUser.add(mniLogin);
        mnbMain.add(mnUser);
         
        mnClient = new JMenu("Customer");
        mniEditClient = new JMenuItem("Edit customer");
        mniEditClient.addActionListener(this);
        mnClient.add(mniEditClient);
        mnbMain.add(mnClient);
        this.setJMenuBar(mnbMain);
        mniLogin.setEnabled(false);
        mniEditClient.setEnabled(false);
         
         
        JLabel lblTitle = new JLabel("Client RMI");
        lblTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lblTitle.setBounds(new Rectangle(150, 20, 200, 30));
        mainPanel.add(lblTitle, null);
         
        JLabel lblHost = new JLabel("Server host:");
        lblHost.setBounds(new Rectangle(10, 70, 150, 25));
        mainPanel.add(lblHost, null);
         
        txtServerHost = new JTextField(50);
        txtServerHost.setBounds(new Rectangle(100, 70, 150, 25));
        mainPanel.add(txtServerHost,null);
         
        JLabel lblPort = new JLabel("Server port:");
        lblPort.setBounds(new Rectangle(10, 100, 150, 25));
        mainPanel.add(lblPort, null);
         
        txtServerPort = new JTextField(50);
        txtServerPort.setBounds(new Rectangle(100, 100, 150, 25));
        mainPanel.add(txtServerPort,null);
         
        JLabel lblClientHost = new JLabel("Server service key:");
        lblClientHost.setBounds(new Rectangle(10, 130, 150, 25));
        mainPanel.add(lblClientHost, null);
         
        txtService = new JTextField(50);
        txtService.setBounds(new Rectangle(100, 130, 150, 25));
        mainPanel.add(txtService,null); 
         
        btnStart = new JButton("Lookup/Relookup");
        btnStart.setBounds(new Rectangle(10, 180, 150, 25));
        btnStart.addActionListener(this);
        mainPanel.add(btnStart,null);
                 
        JScrollPane jScrollPane1 = new JScrollPane();
        mainText = new JTextArea("");
        jScrollPane1.setBounds(new Rectangle(10, 230, 610, 240));
        mainPanel.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mainText, null);
         
        this.setContentPane(mainPanel);
        this.pack();    
        this.setSize(new Dimension(640, 480));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);      
        this.addWindowListener( new WindowAdapter(){
           public void windowClosing(WindowEvent e) {             
             System.exit(0);
             }
          });
    }
 
     
    @Override
    public void actionPerformed(ActionEvent ae) {
        // TODO Auto-generated method stub
        if(ae.getSource() instanceof JButton) {
            JButton btn = (JButton)ae.getSource();
            if(btn.equals(btnStart)) {// connect button
                if(!txtServerHost.getText().isEmpty() && (txtServerHost.getText().trim().length() > 0) &&
                        !txtServerPort.getText().isEmpty() && (txtServerPort.getText().trim().length() > 0) && 
                        !txtService.getText().isEmpty() && (txtService.getText().trim().length() > 0)) {//custom server port
                        int serverPort = Integer.parseInt(txtServerPort.getText().trim());
                        myControl = new ClientCtr(this, txtServerHost.getText().trim(), serverPort, txtService.getText().trim());
                }else {//default server host and port
                        myControl = new ClientCtr(this);
                }
                if(myControl.init()) {
                    mniLogin.setEnabled(true);
                    mniEditClient.setEnabled(true);
                }else {
                    mniLogin.setEnabled(false);
                    mniEditClient.setEnabled(false);
                }
            }
        }else if(ae.getSource() instanceof JMenuItem) {
            JMenuItem mni = (JMenuItem)ae.getSource();
            if(mni.equals(mniLogin)) {// login function
                 
            }else if(mni.equals(mniEditClient)) {// Search client to edit
                SearchCustomerFrm scv = new SearchCustomerFrm(myControl);
                scv.setVisible(true);
            }
        }
    }
     
    public void showMessage(String s){
          mainText.append("\n"+s);
          mainText.setCaretPosition(mainText.getDocument().getLength());
    }
     
    public void setServerHost(String serverHost, String serverPort, String service) {
        txtServerHost.setText(serverHost);
        txtServerPort.setText(serverPort);
        txtService.setText(service);
    }   
     
    public static void main(String[] args) {
        ClientMainFrm view = new ClientMainFrm();
        view.setVisible(true);
    }
}