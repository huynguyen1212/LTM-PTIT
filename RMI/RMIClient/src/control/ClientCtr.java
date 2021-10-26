package control;
 
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
 
import model.Customer;
import model.IPAddress;
import model.User;
import view.ClientMainFrm;
import model.CustomerInterface;
import model.UserInterface;
 
 
public class ClientCtr {
    private ClientMainFrm view;
    private CustomerInterface customerRO;
    private IPAddress serverAddress = new IPAddress("localhost", 9999); //default server address
    private String rmiService = "rmiServer";                            //default server service key
     
    public ClientCtr(ClientMainFrm view){
        this.view = view;
    }
         
    public ClientCtr(ClientMainFrm view, String serverHost, int serverPort, String service){
        this.view = view;
        serverAddress.setHost(serverHost);
        serverAddress.setPort(serverPort);
        rmiService = service;
    }   
     
    public boolean init(){
        try{
            // get the registry
            Registry registry = LocateRegistry.getRegistry(serverAddress.getHost(), serverAddress.getPort());
            // lookup the remote objects
            customerRO = (CustomerInterface)(registry.lookup(rmiService));
             
            view.setServerHost(serverAddress.getHost(), serverAddress.getPort()+"", rmiService);
            view.showMessage("Found the remote objects at the host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        }catch(Exception e){
            e.printStackTrace();
            view.showMessage("Error to lookup the remote objects!");
            return false;
        }
        return true;
    }
         
    public ArrayList<Customer> remoteSearchCustomer(String key){
        try {
            return customerRO.searchCustomer(key);
        }catch(RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
     
    public String remoteEditCustomer(Customer cus) {
        try {
            if(customerRO.editCustomer(cus))
                return "ok";
            else
                return "failed";
        }catch(RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}