package rmi.server.control;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import jdbc.dao.FriendDAO;
import jdbc.dao.UserDAO;
import model.Friend;
import model.IPAddress;
import model.User;
import rmi.general.FriendInterface;
import rmi.general.GroupCallInterface;
import rmi.general.GroupInterface;
import rmi.general.GroupMemberInterface;
import rmi.general.PrivateCallInterface;
import rmi.general.UserInterface;
import rmi.server.view.ServerMainFrm;

public class ServerCtr extends UnicastRemoteObject
        implements UserInterface, GroupCallInterface, GroupInterface,
        GroupMemberInterface, PrivateCallInterface, FriendInterface {

    private IPAddress myAddress = new IPAddress("localhost", 9999);     // default server host/port
    private Registry registry;
    private ServerMainFrm view;
    private String rmiService = "rmiServer";    // default rmi service key

    public ServerCtr(ServerMainFrm view) throws RemoteException {
        this.view = view;
    }

    public ServerCtr(ServerMainFrm view, int port, String service) throws RemoteException {
        this.view = view;
        myAddress.setPort(port);
        this.rmiService = service;
    }

    public void start() throws RemoteException {
        try {
            try {
                registry = LocateRegistry.createRegistry(myAddress.getPort());
            } catch (ExportException e) {//the Registry exists, get it
                registry = LocateRegistry.getRegistry(myAddress.getPort());
            }
            registry.rebind(rmiService, this);
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfo(myAddress, rmiService);
            view.showMessage("The RIM has registered the service key: " + rmiService + ", at the port: " + myAddress.getPort());
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws RemoteException {
        // unbind the service
        try {
            if (registry != null) {
                registry.unbind(rmiService);
                UnicastRemoteObject.unexportObject(this, true);
            }
            view.showMessage("The RIM has unbinded the service key: " + rmiService + ", at the port: " + myAddress.getPort());
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int login(User user) throws RemoteException {
        return new UserDAO().checkLogin(user);
    }

    @Override
    public boolean signup(User user) throws RemoteException {
        return new UserDAO().addUser(user);
    }

    @Override
    public ArrayList<User> searchUser(String key, int userId) throws RemoteException {
        System.out.println("search user " + key);
        return new UserDAO().searchUser(key, userId);
    }

    @Override
    public ArrayList<User> getListFriends(int userId) throws RemoteException {
        return new FriendDAO().getListFriends(userId);
    }

    @Override
    public ArrayList<Friend> getListRequests(int userId) throws RemoteException {
        System.out.println("Receive");
        return new FriendDAO().getListRequests(userId);
    }

    @Override
    public boolean deleteFriends(Integer friendId) throws RemoteException {
        return new FriendDAO().deleteFriends(friendId);
    }

    @Override
    public boolean acceptRequest(Integer friendId) throws RemoteException {
        return new FriendDAO().acceptRequest(friendId);
    }

    @Override
    public boolean addFriend(int userId, int targetId) throws RemoteException {
        return new FriendDAO().addFriend(userId, targetId);
    }

    @Override
    public Friend checkFriend(int userId, int checkId) throws RemoteException {
        return new FriendDAO().checkFriend(userId, checkId);
    }
}
