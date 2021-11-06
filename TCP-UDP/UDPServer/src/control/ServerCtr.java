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
import dao.FriendDAO;
import dao.GroupDAO;
import dao.PrivateCallDAO;
import dao.UserDAO;
import java.util.ArrayList;
import model.Customer;
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
                        case ObjectWrapper.SIGNUP_USER:
                            User singupUser = (User) receivedData.getData();
                            UserDAO singupUserDao = new UserDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_SIGNUP_USER);
                            if (singupUserDao.addUser(singupUser) == true) {
                                resultData.setData("true");
                            } else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.LIST_FRIENDS:
                            FriendDAO listFriendsDao = new FriendDAO();
                            int lfuId = (int) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_LIST_FRIENDS);
                            resultData.setData(listFriendsDao.getListFriends(lfuId)); 
                            break;
                        case ObjectWrapper.LIST_REQUESTS:
                            FriendDAO listRequestsDao = new FriendDAO();
                            int lruId = (int) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_LIST_REQUESTS);
                            resultData.setData(listRequestsDao.getListRequests(lruId));
                            break;
                        case ObjectWrapper.DELETE_FRIEND:
                            Integer friendId = (Integer) receivedData.getData();
                            FriendDAO dfd = new FriendDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_DELETE_FRIEND);
                            if (dfd.deleteFriends(friendId)) {
                                resultData.setData("true");
                            } else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.ACCEPT_REQUEST:
                            Integer ari = (Integer) receivedData.getData();
                            FriendDAO ard = new FriendDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_ACCEPT_REQUEST);
                            if (ard.acceptRequest(ari)) {
                                resultData.setData("true");
                            } else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.REFUSE_REQUEST:
                            Integer rri = (Integer) receivedData.getData();
                            FriendDAO rrd = new FriendDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_REFUSE_REQUEST);
                            if (rrd.deleteFriends(rri)) {
                                resultData.setData("true");
                            } else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.SEARCH_USER:
                            UserDAO sud = new UserDAO();
                            SearchWrapper sus = (SearchWrapper) receivedData.getData();
                            ArrayList<User> result = sud.searchUser(sus.getKey(), sus.getFromId());
                            resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_USER);
                            resultData.setData(result);
                            break;
                        case ObjectWrapper.SEARCH_USER_DETAIL:
                            FriendDAO sudd = new FriendDAO();
                            Friend checkId = (Friend) receivedData.getData();
                            String isF = sudd.checkFriend(checkId.getSourceId(), checkId.getTargetId());
//                            String isF = sudd.checkFriend(idUser, checkId);
                            resultData.setPerformative(ObjectWrapper.REPLY_SEARCH_USER_DETAIL);
                             resultData.setData(isF); 
                            break;
                        case ObjectWrapper.ADD_FRIEND:
                            FriendDAO afv = new FriendDAO();
                            Friend afi = (Friend) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_ADD_FRIEND);
                            if (afv.addFriend(afi.getSourceId(), afi.getTargetId())) {
                                resultData.setData("true");
                            } else {
                                resultData.setData("false");
                            } 
                            break;
                        case ObjectWrapper.LIST_GROUPS:
                            GroupDAO listGroupsDao = new GroupDAO();
                            int lguId = (int) receivedData.getData();
                            resultData.setPerformative(ObjectWrapper.REPLY_LIST_GROUPS);
                            resultData.setData(listGroupsDao.getListGroups(lguId)); 
                            break;
                        case ObjectWrapper.DELETE_GROUP:
                            Integer groupId = (Integer) receivedData.getData();
                            GroupDAO dgd = new GroupDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_DELETE_GROUP);
                            if (dgd.deleteGroup(groupId)) {
                                resultData.setData("true");
                            } else {
                                resultData.setData("false");
                            }
                            break;
                        case ObjectWrapper.LEAVE_GROUP:
                            GroupMember groupMember = (GroupMember) receivedData.getData();
                            GroupDAO lgd = new GroupDAO();
                            resultData.setPerformative(ObjectWrapper.REPLY_LEAVE_GROUP);
                            if (lgd.leaveGroup(groupMember)) {
                                resultData.setData("true");
                            } else {
                                resultData.setData("false");
                            }
                            break;
                        
                        case ObjectWrapper.CREATE_PRIVATE_CALL:
                            PrivateCallDAO cpcd = new PrivateCallDAO();
                            PrivateCall cpc = (PrivateCall) receivedData.getData();
                            int resultCPC = cpcd.createPrivateCall(cpc);
                            resultData.setPerformative(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL);
                            resultData.setData(resultCPC);
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
