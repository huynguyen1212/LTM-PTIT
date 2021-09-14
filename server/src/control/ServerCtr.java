package control;
 
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
 
import dao.CustomerDAO;
import dao.UserDAO;
import model.Customer;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import view.ServerMainFrm;
 
public class ServerCtr {
    private ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private IPAddress myAddress = new IPAddress("localhost",8888);  //default server host and port
     
    public ServerCtr(ServerMainFrm view){
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        openServer();       
    }
     
    public ServerCtr(ServerMainFrm view, int serverPort){
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        myAddress.setPort(serverPort);
        openServer();       
    }
     
     
    private void openServer(){
        try {
            myServer = new ServerSocket(myAddress.getPort());
            myListening = new ServerListening();
            myListening.start();
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress);
            //System.out.println("server started!");
            view.showMessage("TCP server is running at the port " + myAddress.getPort() +"...");
        }catch(Exception e) {
            e.printStackTrace();;
        }
    }
     
    public void stopServer() {
        try {
            for(ServerProcessing sp:myProcess)
                sp.stop();
            myListening.stop();
            myServer.close();
            view.showMessage("TCP server is stopped!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
     
    public void publicClientNumber() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER, myProcess.size());
        for(ServerProcessing sp : myProcess) {
            sp.sendData(data);
        }
    }
     
    /**
     * The class to listen the connections from client, avoiding the blocking of accept connection
     *
     */
    class ServerListening extends Thread{
         
        public ServerListening() {
            super();
        }
         
        public void run() {
            view.showMessage("server is listening... ");
            try {
                while(true) {
                    Socket clientSocket = myServer.accept();
                    ServerProcessing sp = new ServerProcessing(clientSocket);
                    sp.start();
                    myProcess.add(sp);
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                    publicClientNumber();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     
    /**
     * The class to treat the requirement from client
     *
     */
    class ServerProcessing extends Thread{
        private Socket mySocket;
        //private ObjectInputStream ois;
        //private ObjectOutputStream oos;
         
        public ServerProcessing(Socket s) {
            super();
            mySocket = s;
        }
         
        public void sendData(Object obj) {
            try {
                ObjectOutputStream oos= new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(obj);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
         
        public void run() { 
            try {
                while(true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    ObjectOutputStream oos= new ObjectOutputStream(mySocket.getOutputStream());
                    Object o = ois.readObject();
                    if(o instanceof ObjectWrapper){
                        ObjectWrapper data = (ObjectWrapper)o;
 
                        switch(data.getPerformative()) {
                        case ObjectWrapper.LOGIN_USER:
                            User user = (User)data.getData();
                            UserDAO ud = new UserDAO();
                            if(ud.checkLogin(user)){
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER,"ok"));
                            }
                            else
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER,"false")); 
                            break;
                        case ObjectWrapper.EDIT_CUSTOMER:
                            Customer cl = (Customer)data.getData();
                            CustomerDAO cd = new CustomerDAO();
                            boolean ok = cd.editCustomer(cl);
                            if(ok){
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_CUSTOMER,"ok"));
                            }
                            else
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_CUSTOMER,"false"));
                            break;
                        case ObjectWrapper.SEARCH_CUSTOMER_BY_NAME:
                            String key = (String)data.getData();
                            cd = new CustomerDAO();
                            ArrayList<Customer> result = cd.searchCustomer(key);
                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_CUSTOMER,result)); 
                            break;
                        }
 
                    }
                    //ois.reset();
                    //oos.reset();
                }
            }catch (EOFException | SocketException e) {             
                //e.printStackTrace();
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size());
                publicClientNumber();
                try {
                    mySocket.close();
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                this.stop();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}