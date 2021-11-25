/**
 * GameServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webservices;

public class GameServiceLocator extends org.apache.axis.client.Service implements webservices.GameService {

    public GameServiceLocator() {
    }


    public GameServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GameServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Game
    private java.lang.String Game_address = "http://localhost:8080/wsGame/services/Game";

    public java.lang.String getGameAddress() {
        return Game_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GameWSDDServiceName = "Game";

    public java.lang.String getGameWSDDServiceName() {
        return GameWSDDServiceName;
    }

    public void setGameWSDDServiceName(java.lang.String name) {
        GameWSDDServiceName = name;
    }

    public webservices.Game getGame() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Game_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGame(endpoint);
    }

    public webservices.Game getGame(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            webservices.GameSoapBindingStub _stub = new webservices.GameSoapBindingStub(portAddress, this);
            _stub.setPortName(getGameWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGameEndpointAddress(java.lang.String address) {
        Game_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (webservices.Game.class.isAssignableFrom(serviceEndpointInterface)) {
                webservices.GameSoapBindingStub _stub = new webservices.GameSoapBindingStub(new java.net.URL(Game_address), this);
                _stub.setPortName(getGameWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Game".equals(inputPortName)) {
            return getGame();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservices", "GameService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservices", "Game"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Game".equals(portName)) {
            setGameEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
