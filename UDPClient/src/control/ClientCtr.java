package control;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
 
import model.IPAddress;
import model.ObjectWrapper;
import view.ClientMainFrm;
 
 
public class ClientCtr {
    private ClientMainFrm view;
    private DatagramSocket myClient;    
    private ArrayList<ObjectWrapper> myFunction;
    private IPAddress serverAddress = new IPAddress("localhost", 5555); //default server address
    private IPAddress myAddress = new IPAddress("localhost", 6666); //default client address
     
    public ClientCtr(ClientMainFrm view){
        this.view = view;
    }
     
    public ClientCtr(ClientMainFrm view, int clientPort){
        this.view = view;
        myAddress.setPort(clientPort);
    }
     
    public ClientCtr(ClientMainFrm view, IPAddress serverAddr){
        this.view = view;
        serverAddress = serverAddr;
    }
     
    public ClientCtr(ClientMainFrm view, IPAddress serverAddr, int clientPort){
        this.view = view;
        serverAddress = serverAddr;
        myAddress.setPort(clientPort);
    }
     
     
    public boolean open(){
        try {
            myClient = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.setServerandClientInfo(serverAddress, myAddress);
            view.showMessage("UDP client is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to open the datagram socket!");
            return false;
        }
        return true;
    }
     
    public boolean close(){
        try {
            myClient.close();
        }catch(Exception e) {
            e.printStackTrace();
            view.showMessage("Error to close the datagram socket!");
            return false;
        }
        return true;
    }
     
    public boolean sendData(ObjectWrapper data){
        try {
            //prepare the buffer and write the data to send into the buffer
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            oos.flush();            
             
            //create data package and send
            byte[] sendData = baos.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverAddress.getHost()), serverAddress.getPort());
            myClient.send(sendPacket);
             
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error in sending data package");
            return false;
        }
        return true;
    }
     
    public ObjectWrapper receiveData(){
        ObjectWrapper result = null;
        try {   
            //prepare the buffer and fetch the received data into the buffer
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new  DatagramPacket(receiveData, receiveData.length);
            myClient.receive(receivePacket);
             
            //read incoming data from the buffer 
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            result = (ObjectWrapper)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error in receiving data package");
        }
        return result;
    }
    
        public ArrayList<ObjectWrapper> getActiveFunction() {
        return myFunction;
    }
}

