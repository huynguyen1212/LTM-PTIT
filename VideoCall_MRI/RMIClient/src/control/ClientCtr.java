package control;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import model.Friend;

import model.IPAddress;
import model.User;
import rmi.general.UserInterface;
import rmi.general.FriendInterface;
import rmi.general.GroupCallInterface;
import rmi.general.GroupInterface;
import rmi.general.GroupMemberInterface;
import rmi.general.PrivateCallInterface;
import view.ClientMainFrm;

public class ClientCtr {

    private ClientMainFrm view;
    private int userId;
    private UserInterface userRO;
    private FriendInterface friendRO;
    private GroupCallInterface groupCallRO;
    private GroupInterface groupRO;
    private GroupMemberInterface groupMemberRO;
    private PrivateCallInterface privateCallRO;

    private IPAddress serverAddress = new IPAddress("localhost", 9999); //default server address
    private String rmiService = "rmiServer";                            //default server service key

    public ClientCtr(ClientMainFrm view) {
        this.view = view;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ClientCtr(ClientMainFrm view, String serverHost, int serverPort, String service) {
        this.view = view;
        serverAddress.setHost(serverHost);
        serverAddress.setPort(serverPort);
        rmiService = service;
    }

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

    public String login(User user) {
        try {
            int result = userRO.login(user);
            if (result > -1) {
                userId = result;
                return "ok";
            } else {
                return "failed";
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
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

    public ArrayList<User> searchUser(String key) {
        try {
            return userRO.searchUser(key, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getListFriends() {
        try {
            return friendRO.getListFriends(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Friend> getListRequests() {
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

    public String addFriend(int targetId) {
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

    public Friend checkFriend(int checkId) {
        try {
            return friendRO.checkFriend(userId, checkId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
