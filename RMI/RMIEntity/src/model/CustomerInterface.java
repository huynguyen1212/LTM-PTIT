package model;
 
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
 
import model.Customer;
 
public interface CustomerInterface extends Remote{
    public ArrayList<Customer> searchCustomer(String key) throws RemoteException;
    public boolean editCustomer(Customer cus) throws RemoteException;
}
