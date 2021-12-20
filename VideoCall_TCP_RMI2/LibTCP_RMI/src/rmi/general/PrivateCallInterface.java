package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.PrivateCall;

public interface PrivateCallInterface extends Remote {

    public PrivateCall createPrivateCall(PrivateCall privateCall) throws RemoteException;
}
