/**
 * GameService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservices;

public interface GameService extends javax.xml.rpc.Service {
    public java.lang.String getGameAddress();

    public webservices.Game getGame() throws javax.xml.rpc.ServiceException;

    public webservices.Game getGame(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
