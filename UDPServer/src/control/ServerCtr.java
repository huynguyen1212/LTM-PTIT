package control;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
 
import jdbc.dao.CustomerDAO;
import jdbc.dao.CustomerDAO;
import model.Customer;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import view.ServerMainFrm;
 
 
public class ServerCtr {
    private ServerMainFrm view;
    private DatagramSocket myServer;    
    private IPAddress myAddress = new IPAddress("localhost", 5555); //default server address
    private UDPListening myListening;
     
    public ServerCtr(ServerMainFrm view){
        this.view = view;       
    }
     
    public ServerCtr(ServerMainFrm view, int port){
        this.view = view;
        myAddress.setPort(port);
    }
     
     
    public boolean open(){
        try {
            myServer = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfo(myAddress);
            myListening = new UDPListening();
            myListening.start();
            view.showMessage("UDP server is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to open the datagram socket!");
            return false;
        }
        return true;
    }
     
    public boolean close(){
        try {
            myListening.stop();
            myServer.close();
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to close the datagram socket!");
            return false;
        }
        return true;
    }
     
    class UDPListening extends Thread{
        public UDPListening() {
             
        }
         
        public void run() {
            while(true) {               
                try {   
                    //prepare the buffer and fetch the received data into the buffer
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new  DatagramPacket(receiveData, receiveData.length);
                    myServer.receive(receivePacket);
                     
                    //read incoming data from the buffer 
                    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    ObjectWrapper receivedData = (ObjectWrapper)ois.readObject();
                     
                    //processing
                    ObjectWrapper resultData = new ObjectWrapper();
                    switch(receivedData.getPerformative()) {
                    case ObjectWrapper.LOGIN_USER:              // login
                        User user = (User)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_LOGIN_USER);
//                        if(new CustomerDAO().checkLogin(user))
//                            resultData.setData("ok");
//                        else
//                            resultData.setData("false");
                        break;
                    case ObjectWrapper.SEARCH_CUSTOMER_BY_NAME: // search customer by name
                        String key = (String)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_CUSTOMER);
                        resultData.setData(new CustomerDAO().searchCustomer(key));
                        break;
                    case ObjectWrapper.EDIT_CUSTOMER:           // edit customer
                        Customer cus = (Customer)receivedData.getData();
                        resultData.setPerformative(ObjectWrapper.REPLY_EDIT_CUSTOMER);
                        if(new CustomerDAO().editCustomer(cus))
                            resultData.setData("ok");
                        else
                            resultData.setData("false");
                        break;
                    }
                     
                     
                    //prepare the buffer and write the data to send into the buffer
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(resultData);
                    oos.flush();            
                     
                    //create data package and send
                    byte[] sendData = baos.toByteArray();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    myServer.send(sendPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessage("Error when processing an incoming package");
                }    
            }
        }
    }
}