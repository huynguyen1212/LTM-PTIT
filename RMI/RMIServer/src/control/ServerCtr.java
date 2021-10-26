package control;
 
 
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
 
import jdbc.dao.CustomerDAO;
import jdbc.dao.UserDAO;
import model.Customer;
import model.IPAddress;
import model.User;
import model.CustomerInterface;
import model.UserInterface;
import view.ServerMainFrm;
 
 
public class ServerCtr extends UnicastRemoteObject implements CustomerInterface{
    private IPAddress myAddress = new IPAddress("localhost", 9999);     // default server host/port
    private Registry registry;
    private ServerMainFrm view;
    private String rmiService = "rmiServer";    // default rmi service key
     
    public ServerCtr(ServerMainFrm view) throws RemoteException{
        this.view = view;   
    }
     
    public ServerCtr(ServerMainFrm view, int port, String service) throws RemoteException{
        this.view = view;   
        myAddress.setPort(port);
        this.rmiService = service;
    }
     
    public void start() throws RemoteException{
        // registry this to the localhost
        try{
            try {
                //create new one
                registry = LocateRegistry.createRegistry(myAddress.getPort());
            }catch(ExportException e) {//the Registry exists, get it
                registry = LocateRegistry.getRegistry(myAddress.getPort());
            }
            registry.rebind(rmiService, this);
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfo(myAddress, rmiService);
            view.showMessage("The RIM has registered the service key: " + rmiService + ", at the port: " + myAddress.getPort());
        }catch(RemoteException e){
            throw e;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
     
    public void stop() throws RemoteException{
        // unbind the service
        try{
            if(registry != null) {
                registry.unbind(rmiService);
                UnicastRemoteObject.unexportObject(this,true);
            }
            view.showMessage("The RIM has unbinded the service key: " + rmiService + ", at the port: " + myAddress.getPort());
        }catch(RemoteException e){
            throw e;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
     
     
    @Override
    public ArrayList<Customer> searchCustomer(String key) throws RemoteException {
        return new CustomerDAO().searchCustomer(key);
    }
 
    @Override
    public boolean editCustomer(Customer cus) throws RemoteException {
        return new CustomerDAO().editCustomer(cus);
    }
}