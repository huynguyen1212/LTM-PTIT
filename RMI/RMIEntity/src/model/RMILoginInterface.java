package model;
 import java.rmi.Remote;
 import java.rmi.RemoteException;
 import model.User;
 public interface RMILoginInterface extends Remote{
     public String checkLogin(User user) throws RemoteException;
 }