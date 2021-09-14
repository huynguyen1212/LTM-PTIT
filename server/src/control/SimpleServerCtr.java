package control;
 
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
 
import dao.UserDAO;
import model.IPAddress;
import model.User;
import control.ServerCtr.ServerListening;
import view.ServerMainFrm;
 
public class SimpleServerCtr {
    private ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private IPAddress myAddress = new IPAddress("localhost",8888);  //default server host and port
     
    public SimpleServerCtr(ServerMainFrm view){
        this.view = view;
        openServer();   
    }
     
    public SimpleServerCtr(ServerMainFrm view, int serverPort){
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
            myListening.stop();
            myServer.close();
            view.showMessage("TCP server is stopped!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
     
    /**
     * The class to listen the connections from client, avoiding the blocking of accept connection
     *
     */
    class ServerListening extends Thread{// without it, the server side would be blocked until a connection appears
         
        public ServerListening() {
            super();
        }
         
        public void run() {
            view.showMessage("server is listening... ");
            try {
                Socket clientSocket = myServer.accept();
                view.showMessage("A client connected to the server!");
                while(true) { // without it, the server could serve only once
                    ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
 
                    Object o = ois.readObject();
                    if(o instanceof User){
                        User user = (User)o;
                        UserDAO ud = new UserDAO();
                        if(ud.checkLogin(user)){
                            oos.writeObject("ok");
                        }
                        else
                            oos.writeObject("false");                   
                    }
                }
            }catch (EOFException | SocketException e) {             
                //e.printStackTrace();
                view.showMessage("The client disconnected to the server!");
                this.stop();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}