/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.PrivateCall;

/**
 *
 * @author Little Coconut
 */
public interface PrivateCallInterface extends Remote { 
    public PrivateCall createPrivateCall(PrivateCall privateCall) throws RemoteException;
}
