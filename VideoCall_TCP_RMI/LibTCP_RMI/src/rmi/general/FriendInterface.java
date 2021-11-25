/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Friend;
import model.User;

/**
 *
 * @author Little Coconut
 */
public interface FriendInterface extends Remote{
    public ArrayList<User> getListFriends(int userId) throws RemoteException;
    public ArrayList<Friend> getListRequests(int userId ) throws RemoteException;
    public boolean deleteFriends(Integer friendId) throws RemoteException;
    public boolean acceptRequest(Integer friendId) throws RemoteException;
    public boolean addFriend(int userId, int targetId) throws RemoteException;
    public Friend checkFriend(int userId, int checkId) throws RemoteException; 
}
