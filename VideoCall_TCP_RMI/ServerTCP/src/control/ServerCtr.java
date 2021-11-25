package control;

import com.sun.xml.internal.ws.api.pipe.Fiber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Friend;
import model.Group;
import model.IPAddress;
import model.ObjectWrapper;
import model.PrivateCall;
import model.SearchWrapper;
import model.User;
import rmi.general.FriendInterface;
import rmi.general.GroupCallInterface;
import rmi.general.GroupInterface;
import rmi.general.GroupMemberInterface;
import rmi.general.PrivateCallInterface;
import rmi.general.UserInterface;
import view.ServerMainFrm;

public class ServerCtr {

    private ServerMainFrm view;
//    private int userId;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private ArrayList<Integer> listUserId = new ArrayList<Integer>();
    private IPAddress myAddress = new IPAddress("localhost", 8888);  //default server host and port

//    -----rmi
    private UserInterface userRO;
    private FriendInterface friendRO;
    private GroupCallInterface groupCallRO;
    private GroupInterface groupRO;
    private GroupMemberInterface groupMemberRO;
    private PrivateCallInterface privateCallRO;
    private IPAddress serverAddress = new IPAddress("localhost", 9999); //default server address
    private String rmiService = "rmiServer";

    public ServerCtr(ServerMainFrm view) {
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        openServer();
    }

    public ServerCtr(ServerMainFrm view, int serverPort) {
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        myAddress.setPort(serverPort);
        openServer();
    }

//    -----RMI
    public ServerCtr(ServerMainFrm view, String serverHost, int serverPort, String service) {
        this.view = view;
        serverAddress.setHost(serverHost);
        serverAddress.setPort(serverPort);
        rmiService = service;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
    // RMI
    public boolean init() {
        try {
            // get the registry
            Registry registry = LocateRegistry.getRegistry(serverAddress.getHost(), serverAddress.getPort());
            // lookup the remote objects
            userRO = (UserInterface) (registry.lookup(rmiService));
            friendRO = (FriendInterface) (registry.lookup(rmiService));
            groupCallRO = (GroupCallInterface) (registry.lookup(rmiService));
            groupRO = (GroupInterface) (registry.lookup(rmiService));
            groupMemberRO = (GroupMemberInterface) (registry.lookup(rmiService));
            privateCallRO = (PrivateCallInterface) (registry.lookup(rmiService));

            System.out.println("Found the remote objects at the host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error to lookup the remote objects!");
            return false;
        }
        return true;
    }

    private void openServer() {
        try {
            myServer = new ServerSocket(myAddress.getPort());
            myListening = new ServerListening();
            myListening.start();
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress);
            view.showMessage("TCP server is running at the port " + myAddress.getPort() + "...");
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    public void stopServer() {
        try {
            for (ServerProcessing sp : myProcess) {
                sp.stop();
            }
            myListening.stop();
            myServer.close();
            view.showMessage("TCP server is stopped!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publicClientNumber() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_INFORM_ACTIVE_USER, listUserId);
        for (ServerProcessing sp : myProcess) {
            sp.sendData(data);
        }
    }

    public void informDenyCall(PrivateCall privateCall) {
        String res = "deny";
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, res);
        for (ServerProcessing sp : myProcess) {
            if (sp.getIdUser() == privateCall.getSourceId()) {
                sp.setOnCall(false);
                sp.sendData(data);
                System.out.println("tcp server inform deny call to account " + privateCall.getSourceId());
                break;

            }
        }

    }

    public void informAcceptCall(PrivateCall privateCall) {
        String res = "accept";
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, res);
        for (ServerProcessing sp : myProcess) {
            if (sp.getIdUser() == privateCall.getSourceId()) {
                sp.setOnCall(true);
                sp.sendData(data);
                System.out.println("tcp server inform accept call to account " + privateCall.getSourceId());

                break;

            }
        }

    }

    public void informBusyCall(PrivateCall privateCall) {
        String res = "busy";
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, res);
        for (ServerProcessing sp : myProcess) {
            if (sp.getIdUser() == privateCall.getSourceId()) {
                sp.setOnCall(false);
                sp.sendData(data);
                System.out.println("tcp server inform accept call to account " + privateCall.getSourceId());

                break;

            }
        }

    }

    public void informPrivateCall(PrivateCall privateCall) {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.INCOMING_CALL, privateCall);
        for (ServerProcessing sp : myProcess) {
            if (sp.getIdUser() == privateCall.getTargetId() && sp.isOnCall() == true) {
//                if (sp.isOnCall() == true) {
                informBusyCall(privateCall);
//                    break;
//                } else {
//                    sp.setOnCall(true);
//                    sp.sendData(data);
//                    System.out.println("inform!");
//                    break;
//                }

            } else if (sp.getIdUser() == privateCall.getTargetId()) {
                sp.setOnCall(true);
                sp.sendData(data);
                System.out.println("inform!");
                break;
            }
        }

    }

    /**
     * The class to listen the connections from client, avoiding the blocking of
     * accept connection
     *
     */
    class ServerListening extends Thread {

        public ServerListening() {
            super();
        }

        public void run() {
            view.showMessage("server is listening... ");
            try {
                while (true) {
                    Socket clientSocket = myServer.accept();
                    ServerProcessing sp = new ServerProcessing(clientSocket);
                    sp.start();
                    myProcess.add(sp);
//                    DatagramSocket clientUDP = myClient;
//                    ServerProcessing sp = new ServerProcessing(clientSocket, clientUDP);
//                    sp.start();
//                    myProcess.add(sp);
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                    publicClientNumber();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The class to treat the requirement from client
     *
     */
    class ServerProcessing extends Thread {

        private Socket mySocket;
        private Integer idUser;
        private boolean onCall = false;
        private DatagramSocket myClient;
        private IPAddress serverAddress = new IPAddress("localhost", 5555);

        public Integer getIdUser() {
            return idUser;
        }

        public boolean isOnCall() {
            return onCall;
        }

        public void setOnCall(boolean onCall) {
            this.onCall = onCall;
        }

        public ServerProcessing(Socket s) {
            super();
            mySocket = s;
        }

        public void sendData(Object obj) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
                    Object o = ois.readObject();
                    if (o instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) o;

                        switch (data.getPerformative()) {
                            case ObjectWrapper.DENY_CALL:
                                setOnCall(false);
                                System.out.println("tcp server receive deny call!");
                                PrivateCall denyCallData = (PrivateCall) data.getData();
                                informDenyCall(denyCallData);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.DENY_CALL, "ok"));
//                                int loginRes = (int) login(user);
//                                if (loginRes == -1) {
//                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "false"));
//                                } else {
//                                    idUser = loginRes;
//                                    listUserId.add(idUser);
//                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "true"));
//                                    publicClientNumber();
//                                }
                                break;
                            case ObjectWrapper.ACCEPT_CALL:
                                setOnCall(true);
                                System.out.println("tcp server receive accept call!");
                                PrivateCall acceptCallData = (PrivateCall) data.getData();
                                informAcceptCall(acceptCallData);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.ACCEPT_CALL, "ok"));
//                                int loginRes = (int) login(user);
//                                if (loginRes == -1) {
//                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "false"));
//                                } else {
//                                    idUser = loginRes;
//                                    listUserId.add(idUser);
//                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "true"));
//                                    publicClientNumber();
//                                }
                                break;
                            case ObjectWrapper.LOGIN_USER:
                                User user = (User) data.getData();
                                int loginRes = (int) login(user);
                                if (loginRes == -1) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "false"));
                                } else {
                                    idUser = loginRes;
                                    listUserId.add(idUser);
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "true"));
                                    publicClientNumber();
                                }
                                break;
                            case ObjectWrapper.SIGNUP_USER:
                                User singupUser = (User) data.getData();
                                String signupRes = signup(singupUser);
                                if (signupRes.equals("ok")) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SIGNUP_USER, "true"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SIGNUP_USER, "false"));
                                }
                                break;

                            case ObjectWrapper.LIST_FRIENDS:
                                ArrayList<User> listFriendsRes = getListFriends(idUser);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LIST_FRIENDS, listFriendsRes));

                                break;
                            case ObjectWrapper.LIST_REQUESTS:
                                ArrayList<Friend> listRequestsRes = getListRequests(idUser);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LIST_REQUESTS, listRequestsRes));
                                break;

                            case ObjectWrapper.LIST_GROUPS:
                                ArrayList<Group> listGroupsRes = getListGroups(idUser);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LIST_GROUPS, listGroupsRes));
                                break;

                            case ObjectWrapper.DELETE_GROUP:
                                Integer dgi = (Integer) data.getData();
                                String deleteGroup = deleteGroup(dgi);
                                if (deleteGroup.equals("ok")) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_GROUP, "true"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_GROUP, "false"));
                                }
                                break;

                            case ObjectWrapper.LEAVE_GROUP:
                                Integer lgi = (Integer) data.getData();
                                Integer ui = (Integer) data.getData();
                                String leaveGroup = leaveGroup(lgi, ui);
                                if (leaveGroup.equals("ok")) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_GROUP, "true"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_GROUP, "false"));
                                }
                                break;

                            case ObjectWrapper.DELETE_FRIEND:
                                Integer dfi = (Integer) data.getData();
                                String deleteFriendRes = deleteFriends(dfi);
                                if (deleteFriendRes.equals("ok")) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_FRIEND, "true"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_FRIEND, "false"));
                                }
                                break;

                            case ObjectWrapper.ACCEPT_REQUEST:
                                Integer ari = (Integer) data.getData();
                                String addFriendRes = acceptRequest(ari);
                                if (addFriendRes.equals("ok")) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_REQUEST, "true"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_REQUEST, "false"));
                                }
                                break;
                            case ObjectWrapper.REFUSE_REQUEST:
                                Integer rri = (Integer) data.getData();
                                String refuseRequestRes = deleteFriends(rri);
                                if (refuseRequestRes.equals("ok")) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REFUSE_REQUEST, "true"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REFUSE_REQUEST, "false"));
                                }
                                break;
                            case ObjectWrapper.SEARCH_USER:
                                String sus = (String) data.getData();
                                ArrayList<User> searchUserRes = searchUser(idUser, sus);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER, searchUserRes));
                                break;

                            case ObjectWrapper.SEARCH_USER_DETAIL:
                                Integer checkId = (Integer) data.getData();
                                Friend searchUserDetailRes = checkFriend(idUser, checkId);
                                System.out.println("checkfriend result:" + searchUserDetailRes.getStatus());
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER_DETAIL, searchUserDetailRes.getStatus()));

                                break;
                            case ObjectWrapper.ADD_FRIEND:
                                Integer afi = (Integer) data.getData();
                                String addFriendsRes = addFriend(idUser, afi);
                                if (addFriendsRes.equals("ok")) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND, "true"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND, "false"));
                                }
                                break;
                            case ObjectWrapper.CREATE_PRIVATE_CALL:
                                Integer cpci = (Integer) data.getData();
                                onCall = true;
                                PrivateCall newPrivateCall = new PrivateCall(idUser, cpci);
                                PrivateCall CallCreated = createPrivateCall(newPrivateCall);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.CREATE_PRIVATE_CALL, "ok"));
                                informPrivateCall(CallCreated);
//                                this.sendDataUDP(new ObjectWrapper(ObjectWrapper.CREATE_PRIVATE_CALL, newPrivateCall));
//                                //receive
//                                ObjectWrapper cpc = this.receiveData();
//                                if (cpc.getPerformative() == ObjectWrapper.REPLY_CREATE_PRIVATE_CALL) {
//                                    Integer nci = (Integer) cpc.getData();
//                                    System.out.println("ID cua cuoc goi moi: " + nci);
//                                    newPrivateCall.setId(nci);
//                                    informPrivateCall(newPrivateCall);
//                                }
                                break;
                        }

                    }
                }
            } catch (EOFException | SocketException e) {
                //e.printStackTrace();
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size() + " " + idUser);

                view.showMessage("Id user " + myAddress.getPort() + "...");
                int index = -1;
                for (int i = 0; i < listUserId.size(); i++) {
                    if (idUser == listUserId.get(i)) {
                        index = i;
                        break;
                    }
                }
                if (index >= 0) {
                    for (int i = index; i < listUserId.size() - 1; i++) {
                        listUserId.set(i, listUserId.get(i + 1));
                    }
                }
                if (listUserId.size() > 0) {
                    listUserId.remove(listUserId.size() - 1);
                }
//                System.out.println(listUserId);
                publicClientNumber();
                try {
                    mySocket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int login(User user) {
        try {
            int result = userRO.login(user);
            if (result > -1) {
                return result; //return user id 
            } else {
                return -1;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String signup(User user) {
        try {
            if (userRO.signup(user)) {
                return "ok";
            } else {
                return "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getListFriends(int userId) {
        try {
            return friendRO.getListFriends(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Friend> getListRequests(int userId) {
        try {
            return friendRO.getListRequests(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String deleteFriends(Integer friendId) {
        try {
            if (friendRO.deleteFriends(friendId)) {
                return "ok";
            } else {
                return "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String deleteGroup(Integer Id) {
        try {
            if (groupRO.deleteGroup(Id)) {
                return "ok";
            } else {
                return "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String leaveGroup(Integer groupId, Integer userId) {
        try {
            if (groupRO.leaveGroup(groupId, userId)) {
                return "ok";
            } else {
                return "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String acceptRequest(Integer friendId) {
        try {
            if (friendRO.acceptRequest(friendId)) {
                return "ok";
            } else {
                return "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String addFriend(int userId, int targetId) {
        try {
            if (friendRO.addFriend(userId, targetId)) {
                return "ok";
            } else {
                return "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Friend checkFriend(int userId, int checkId) {
        try {
            return friendRO.checkFriend(userId, checkId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> searchUser(int userId, String key) {
        try {
            return userRO.searchUser(key, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Group> getListGroups(int userId) {
        try {
            return groupRO.getListGroups(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createGroup(int userId, String groupName) {
        try {
            if (groupRO.createGroup(groupName, userId)) {
                return "ok";
            } else {
                return "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> getMemberInGroup(int groupId) {
        try {
            return (groupRO.getListUserInGroup(groupId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrivateCall createPrivateCall(PrivateCall call) {
        try {
            return (privateCallRO.createPrivateCall(call));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
