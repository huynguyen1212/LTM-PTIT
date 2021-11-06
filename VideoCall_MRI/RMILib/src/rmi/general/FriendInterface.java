package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Friend;
import model.User;

public interface FriendInterface extends Remote {

    public ArrayList<User> getListFriends(int userId) throws RemoteException;

    public ArrayList<Friend> getListRequests(int userId) throws RemoteException;

    public boolean deleteFriends(Integer friendId) throws RemoteException;

    public boolean acceptRequest(Integer friendId) throws RemoteException;

    public boolean addFriend(int userId, int targetId) throws RemoteException;

    public Friend checkFriend(int userId, int checkId) throws RemoteException;
}
