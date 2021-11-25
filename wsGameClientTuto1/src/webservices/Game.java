/**
 * Game.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservices;

public interface Game extends java.rmi.Remote {
    public boolean checkValid(model.Sudoku sudo) throws java.rmi.RemoteException;
    public boolean checkWin(model.Sudoku sudo) throws java.rmi.RemoteException;
}
