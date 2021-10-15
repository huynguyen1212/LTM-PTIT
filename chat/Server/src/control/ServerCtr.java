/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import jdbc.dao.UserDAO;
import model.Friend;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import view.ServerMainFrm;
import dto.RequestDTO;

public class ServerCtr {

    private ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private IPAddress myAddress = new IPAddress("localhost", 8888);  //default server host and port

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

    private void openServer() {
        try {
            myServer = new ServerSocket(myAddress.getPort());
            myListening = new ServerListening();
            myListening.start();
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress);
            view.showMessage("TCP server is running at the port " + myAddress.getPort() + "...");
        } catch (Exception e) {
            e.printStackTrace();
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
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER, myProcess.size());
        for (ServerProcessing sp : myProcess) {
            sp.sendData(data);
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
        //private ObjectInputStream ois;
        //private ObjectOutputStream oos;

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
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
                    Object o = ois.readObject();
                    if (o instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) o;
                        UserDAO ud = new UserDAO();
                        System.out.print("SERVER_REPLY: ");
                        System.out.println(data.getPerformative());
                        boolean booleanRes = false;

                        switch (data.getPerformative()) {

                            case ObjectWrapper.LOGIN_USER:
                                User res = ud.login((User) data.getData());
                                if (res != null) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, res));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, null));
                                }
                                break;

                            case ObjectWrapper.SIGNUP_USER:
                                if (ud.signup((User) data.getData())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SIGNUP_USER, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SIGNUP_USER, "false"));
                                }
                                break;

                            case ObjectWrapper.GET_LIST_USER:
                                ArrayList<User> users = ud.getUsers((User) data.getData());
                                if (users.size() >= 0) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_LIST_USER, users));
                                }
                                break;

                            case ObjectWrapper.ADD_FRIEND:
                                booleanRes = ud.addFriend((ArrayList<Long>) data.getData());
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND, booleanRes ? "ok" : "false"));
                                break;

                            case ObjectWrapper.GET_REQUESTS:
                                ArrayList<RequestDTO> requests = ud.getRequests((Long) data.getData());
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_REQUESTS, requests));
                                break;

                            case ObjectWrapper.CONFIRM_FRIEND:
                                booleanRes = ud.confirmFriend((Long) data.getData());
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CONFIRM_FRIEND, booleanRes ? "ok" : "false"));
                                break;
                        }

                    }
                    //ois.reset();
                    //oos.reset();
                }
            } catch (EOFException | SocketException e) {
                //e.printStackTrace();
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size());
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
}
