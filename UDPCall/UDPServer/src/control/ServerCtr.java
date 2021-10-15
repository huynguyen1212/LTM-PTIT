package control;

import dto.FriendDTO;
import dto.RequestDTO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import jdbc.dao.FriendDAO;

import jdbc.dao.UserDAO;
import model.Friend;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import view.ServerMainFrm;

public class ServerCtr {

    private ServerMainFrm view;
    private DatagramSocket myServer;
    private IPAddress myAddress = new IPAddress("localhost", 5555); //default server address
    private UDPListening myListening;
    private UserDAO userDao = new UserDAO();
    private FriendDAO friendDao = new FriendDAO();

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

                    User userData = null;
                    User receivedUser = null;

                    ArrayList<User> users = null;
                    ArrayList<Friend> friends = null;
                    ArrayList<FriendDTO> friendDTOList = null;

                    Long id = null;

                    boolean check = false;

                    //processing
                    ObjectWrapper resultData = new ObjectWrapper();
                    switch (receivedData.getPerformative()) {
                        case ObjectWrapper.LOGIN_USER:              // login
                            receivedUser = (User) receivedData.getData();

                            userData = (User) userDao.login(receivedUser);
                            resultData.setPerformative(ObjectWrapper.REPLY_LOGIN_USER);
                            resultData.setData(userData);
                            break;

                        case ObjectWrapper.SIGNUP_USER:              // login
                            receivedUser = (User) receivedData.getData();

                            check = userDao.signup(receivedUser);
                            resultData.setPerformative(ObjectWrapper.REPLY_SIGNUP_USER);
                            resultData.setData(check ? "ok" : "false");
                            break;

                        case ObjectWrapper.GET_LIST_FRIEND:              // login
                            id = (Long) receivedData.getData();

                            users = friendDao.getFriends(id);
                            resultData.setPerformative(ObjectWrapper.REPLY_GET_LIST_FRIEND);
                            resultData.setData(users);
                            break;

                        case ObjectWrapper.GET_LIST_USER:
                            friendDTOList = userDao.getUsers((User) receivedData.getData());
                            if (friendDTOList.size() >= 0) {
                                resultData.setPerformative(ObjectWrapper.REPLY_GET_LIST_USER);
                                resultData.setData(friendDTOList);
                            }
                            break;

                        case ObjectWrapper.ADD_FRIEND:
                            Long userId2 = friendDao.addFriend((ArrayList<Long>) receivedData.getData());

                            resultData.setPerformative(ObjectWrapper.REPLY_ADD_FRIEND);
                            resultData.setData(userId2);
                            break;

                        case ObjectWrapper.GET_REQUESTS:
                            ArrayList<RequestDTO> requests = friendDao.getRequests((Long) receivedData.getData());
                            resultData.setPerformative(ObjectWrapper.REPLY_GET_REQUESTS);
                            resultData.setData(requests);
                            break;

                        case ObjectWrapper.CONFIRM_FRIEND:
                            RequestDTO requestDTO = friendDao.confirmFriend((RequestDTO) receivedData.getData());
                            resultData.setPerformative(ObjectWrapper.REPLY_CONFIRM_FRIEND);
                            resultData.setData(requestDTO);
                            break;

                        case ObjectWrapper.CANCEL_ADD_FRIEND:
                            check = friendDao.cancelAddFriend((Long) receivedData.getData());
                            resultData.setPerformative(ObjectWrapper.REPLY_CANCEL_ADD_FRIEND);
                            resultData.setData(check ? "ok" : "false");
                            break;

                        case ObjectWrapper.DECLINE_FRIEND:
                            check = friendDao.declineFriend((Long) receivedData.getData());
                            resultData.setPerformative(ObjectWrapper.REPLY_DECLINE_FRIEND);
                            resultData.setData(check ? "ok" : "false");
                            break;

                        case ObjectWrapper.TRIGGER_STATUS:
                            check = userDao.triggerStatus((User) receivedData.getData());
//                            oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_TRIGGER_STATUS, booleanRes));
//                            if (booleanRes) {
//                                broadcastClientStatus(this.getUser().getId(), 1);
//                            }
//                            resultData.setPerformative(ObjectWrapper.REPLY_TRIGGER_STATUS);
//                            resultData.setData( ? "ok" : "false");
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
