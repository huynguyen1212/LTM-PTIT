/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Little Mango
 */
import com.sun.xml.internal.ws.api.pipe.Fiber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Friend;
import model.IPAddress;
import model.ObjectWrapper;
import model.PrivateCall;
import model.SearchWrapper;
import model.User;
import view.ServerMainFrm;

public class ServerCtr {

    private ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private ArrayList<Integer> listUserId = new ArrayList<Integer>();
    private IPAddress myAddress = new IPAddress("localhost", 8888);  //default server host and port
    //add those for udp client
    private DatagramSocket myClient;
    private IPAddress serverAddress = new IPAddress("localhost", 5555); //default server address

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
            e.printStackTrace();;
        }
    }

    public boolean open() {
        try {
            myClient = new DatagramSocket(myAddress.getPort());
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
//            view.setServerandClientInfo(serverAddress, myAddress);
            view.showMessage("UDP client is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());

//            System.out.println("UDP client is running at the host: " + myAddress.getHost() + ", port: " + myAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error to open the datagram socket!");
//            System.out.println("Error to open the datagram socket!");
            return false;
        }
        return true;
    }

    public void stopServer() {
        try {
            for (ServerProcessing sp : myProcess) {
                sp.stop();
            }
            myListening.stop();
            myServer.close();
            view.showMessage("TCP server is stopped!");
            myClient.close();
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

    public void informPrivateCall(PrivateCall privateCall) {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.INCOMING_CALL, privateCall);
        for (ServerProcessing sp : myProcess) {
            if (sp.getIdUser() == privateCall.getTargetId()) {
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
                    DatagramSocket clientUDP = myClient;
                    ServerProcessing sp = new ServerProcessing(clientSocket, clientUDP);
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
        private Integer idUser;
        private Integer callId = -1;
        private DatagramSocket myClient;
        private IPAddress serverAddress = new IPAddress("localhost", 5555);

        public Integer getIdUser() {
            return idUser;
        }

        public Integer getCallId() {
            return callId;
        }

        public boolean sendDataUDP(ObjectWrapper data) {
            try {
                //prepare the buffer and write the data to send into the buffer
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(data);
                oos.flush();

                //create data package and send
                byte[] sendData = baos.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverAddress.getHost()), serverAddress.getPort());
                myClient.send(sendPacket);

            } catch (Exception e) {
                e.printStackTrace();
                view.showMessage("Error in sending data package");
                return false;
            }
            return true;
        }

        public ObjectWrapper receiveData() {
            ObjectWrapper result = null;
            try {
                //prepare the buffer and fetch the received data into the buffer
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                myClient.receive(receivePacket);

                //read incoming data from the buffer 
                ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
                ObjectInputStream ois = new ObjectInputStream(bais);
                result = (ObjectWrapper) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                view.showMessage("Error in receiving data package");
            }
            return result;
        }

        public ServerProcessing(Socket s, DatagramSocket clientUDP) {
            super();
            mySocket = s;
            myClient = clientUDP;
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
                            case ObjectWrapper.LOGIN_USER:
                                User user = (User) data.getData();
                                //sending data
                                this.sendDataUDP(new ObjectWrapper(ObjectWrapper.LOGIN_USER, user));

                                //receive
                                ObjectWrapper datalogin = this.receiveData();
                                if (datalogin.getPerformative() == ObjectWrapper.REPLY_LOGIN_USER) {
                                    int result = (int) datalogin.getData();
//                                    System.out.println(result);
                                    if (result == -1) {
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "false"));
                                    } else {
                                        idUser = result;
                                        listUserId.add(idUser);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, "true"));

                                        publicClientNumber();
                                    }
                                }
                                break;
                            case ObjectWrapper.CREATE_USER:
                            {
                                User createUser = (User) data.getData();
                                System.out.println(createUser.getFullname());
                                //sending data
                                this.sendDataUDP(new ObjectWrapper(ObjectWrapper.CREATE_USER, createUser));

                                //receive
                                ObjectWrapper datacreate = this.receiveData();
                                if (datacreate.getPerformative() == ObjectWrapper.REPLY_CREATE_USER) {
                                    String result = (String) datacreate.getData();
                                    if (result.equals("true")) {
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_USER, "true"));
                                    } else {
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_USER, "false"));
                                    }

                                }
                                break;
                            }
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
                listUserId.remove(listUserId.size() - 1);
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
}
