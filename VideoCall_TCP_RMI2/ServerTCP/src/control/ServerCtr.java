package control;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import model.Friend;
import model.IPAddress;
import model.ObjectWrapper;
import model.PrivateCall;
import model.User;
import rmi.general.FriendInterface;
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

    // RMI
    public boolean init() {
        try {
            // get the registry
            Registry registry = LocateRegistry.getRegistry(serverAddress.getHost(), serverAddress.getPort());
            // lookup the remote objects
            userRO = (UserInterface) (registry.lookup(rmiService));
            friendRO = (FriendInterface) (registry.lookup(rmiService));
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

    // luôn liên tục gửi danh sách id những người đang truy cập server
    public void publicClientNumber() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_INFORM_ACTIVE_USER, listUserId);
        for (ServerProcessing sp : myProcess) {
            sp.sendData(data);
        }
    }

    // Thông báo từ chối cuộc gọi
    public void informDenyCall(PrivateCall privateCall) {
        String res = "deny";
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, res);
        for (ServerProcessing sp : myProcess) {
            if (sp.getIdUser() == privateCall.getSource().getId()) {
                sp.setOnCall(false);
                sp.sendData(data);
                System.out.println("deny");
                break;
            }
        }
    }

    // Thông báo chấp nhận cuộc gọi
    public void informAcceptCall(PrivateCall privateCall) {
        String resForSource = "accept";
        ObjectWrapper dataForSource = new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, resForSource);
        for (ServerProcessing sp : myProcess) {
            if (sp.getIdUser() == privateCall.getSource().getId()) {
                sp.setOnCall(true);
                sp.sendData(dataForSource);
                System.out.println("accept");
                break;
            }
        }
    }

    // Thông báo bận
    public void informBusyCall(PrivateCall privateCall) {
        String res = "busy";
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, res);
        for (ServerProcessing sp : myProcess) {
            if (sp.getIdUser() == privateCall.getSource().getId()) {
                sp.setOnCall(false);
                sp.sendData(data);
                break;
            }
        }
    }

    public void informPrivateCall(PrivateCall privateCall) {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.INCOMING_CALL, privateCall);
        for (ServerProcessing sp : myProcess) {
            //check bận trc, k thì mới tạo
            if (sp.getIdUser() == privateCall.getTarget().getId() && sp.isOnCall() == true) {
                informBusyCall(privateCall);
            } else if (sp.getIdUser() == privateCall.getTarget().getId()) {
                sp.setOnCall(true);
                sp.sendData(data);
                break;
            }
        }
    }

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
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                    publicClientNumber();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class ServerProcessing extends Thread {

        private Socket mySocket;
        private Integer idUser;
        private User user1;
        private User userFriend;

        //lưu trong cuộc gọi tại TCP server
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
                            case ObjectWrapper.CREATE_PRIVATE_CALL:
                                User cpci = (User) data.getData();
                                userFriend = cpci;
                                onCall = true;
                                PrivateCall newPrivateCall = new PrivateCall(user1, cpci);
                                PrivateCall ResPrivateCall = createPrivateCall(newPrivateCall);

                                System.out.println("ResPrivateCall");
                                System.out.println(ResPrivateCall);

                                informPrivateCall(ResPrivateCall);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, "ok"));
                                break;

                            case ObjectWrapper.DENY_CALL:
                                setOnCall(false);
//                                System.out.println("tcp server receive deny call!");
                                PrivateCall denyCallData = (PrivateCall) data.getData();
                                informDenyCall(denyCallData);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, "deny_ok"));
                                break;

                            case ObjectWrapper.ACCEPT_CALL:
                                setOnCall(true);
                                
                                System.out.println("tcp server receive accept call!");
                                PrivateCall acceptCallData = (PrivateCall) data.getData();
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_PRIVATE_CALL, "accep_ok"));
                                informAcceptCall(acceptCallData);
                                break;

                            case ObjectWrapper.SENT_VIDEO:
                                System.out.println("loggggg img img");
                                
                                ImageIcon ic;
                                ic = (ImageIcon) data.getData();
                                ObjectWrapper dataRV = new ObjectWrapper(ObjectWrapper.RECIEVE_VIDEO, ic);

                                for (ServerProcessing sp : myProcess) {
                                    if (sp.getIdUser() == userFriend.getId()) {
                                        sp.sendData(dataRV);
                                        break;
                                    }
                                }
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.RECIEVE_VIDEO, null));
                                break;

                            case ObjectWrapper.LOGIN_USER:
                                User user = (User) data.getData();
                                int loginRes = (int) login(user);
                                if (loginRes == -1) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "false"));
                                } else {
                                    idUser = loginRes;
                                    listUserId.add(idUser);
                                    user1 = getUserById(idUser);
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

    public User getUserById(int userId) {
        try {
            return userRO.getUserById(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
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
