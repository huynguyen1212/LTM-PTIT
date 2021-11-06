package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.User;

public interface UserInterface extends Remote {

    public int login(User user) throws RemoteException;

    public boolean signup(User user) throws RemoteException;

    public ArrayList<User> searchUser(String key, int userId) throws RemoteException;
}
