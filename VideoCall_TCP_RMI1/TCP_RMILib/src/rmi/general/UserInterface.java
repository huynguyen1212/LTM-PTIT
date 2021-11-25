/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.User;

/**
 *
 * @author Little Coconut
 */
public interface UserInterface extends Remote {

    public int login(User user) throws RemoteException;

    public boolean signup(User user) throws RemoteException;

    public ArrayList<User> searchUser(String key, int userId) throws RemoteException;
}
