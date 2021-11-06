/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Little Coconut
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import dao.CustomerDAO;
import dao.UserDAO;
import java.util.ArrayList;
import model.Reader;
import model.Friend;
import model.GroupMember;
import model.IPAddress;
import model.ObjectWrapper;
import model.PrivateCall;
import model.SearchWrapper;
import model.User;
import view.ServerMainFrm;

public class ServerCtr {

    private ServerMainFrm view;
    private DatagramSocket myServer;
    private IPAddress myAddress = new IPAddress("localhost", 5555); //default server address
    private UDPListening myListening;

    public ServerCtr(ServerMainFrm view) {
        this.view = view;
    }

    public ServerCtr(ServerMainFrm view, int port) {
        this.view = view;
        myAddress.setPort(port);
    }

    public boolean open() {
        try {
            myServer = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfo(myAddress);
            myListening = new UDPListening();
            myListening.start();
            view.showMessage("UDP server is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error to open the datagram socket!");
            return false;
        }
        return true;
    }

    public boolean close() {
        try {
            myListening.stop();
            myServer.close();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error to close the datagram socket!");
            return false;
        }
        return true;
    }

    class UDPListening extends Thread {

        public UDPListening() {

        }

        public void run() {
            while (true) {
                try {
                    //prepare the buffer and fetch the received data into the buffer
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    myServer.receive(receivePacket);

                    //read incoming data from the buffer 
                    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    ObjectWrapper receivedData = (ObjectWrapper) ois.readObject();

                    //processing
                    ObjectWrapper resultData = new ObjectWrapper();
                    switch (receivedData.getPerformative()) {
                        case ObjectWrapper.LOGIN_USER:
                            User user = (User) receivedData.getData();
                            UserDAO ud = new UserDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_LOGIN_USER);
                            resultData.setData(ud.checkLogin(user)); 
                            break;
                        case ObjectWrapper.CREATE_USER:
                            User createUser = (User) receivedData.getData();
                            System.out.println(createUser.getFullname());
                            UserDAO singupUserDao = new UserDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_CREATE_USER);
                            if (singupUserDao.addUser(createUser) == 1) {
                                resultData.setData("true");
                            } else {
                                resultData.setData("false");
                            }
                            break;
                    }

                    //prepare the buffer and write the data to send into the buffer
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(resultData);
                    oos.flush();

                    //create data package and send
                    byte[] sendData = baos.toByteArray();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    myServer.send(sendPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessage("Error when processing an incoming package");
                }
            }
        }
    }
}
