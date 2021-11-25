/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException; 
import java.util.ArrayList;
import model.Group;
import model.User;

/**
 *
 * @author Little Coconut
 */
public interface GroupInterface extends Remote{
    public ArrayList<Group> getListGroups(int userId) throws RemoteException;
    public boolean createGroup(String groupName,int userId) throws RemoteException;
    public boolean leaveGroup(String groupName,int userId) throws RemoteException;
    public ArrayList<User> getListUserInGroup(int groupId) throws RemoteException;
//    public boolean login(User user) throws RemoteException;
//    public boolean signup(User user) throws RemoteException;
}
