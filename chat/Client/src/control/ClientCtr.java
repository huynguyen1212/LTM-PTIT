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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.IPAddress;
import model.ObjectWrapper;
import model.User;
import view.AddFriendFrm;
import view.MainFrm;
import view.RequestFrm;
import view.SignupFrm;

public class ClientCtr {
    private Socket mySocket;
    private MainFrm mainFrm;
    private ClientListening myListening;                            // thread to listen the data from the server
    private ArrayList<ObjectWrapper> myFunction;                  // list of active client functions
    private IPAddress serverAddress = new IPAddress("localhost", 8888);  // default server host and port

    public ClientCtr(MainFrm mainFrm) {
        super();
        this.mainFrm = mainFrm;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public boolean openConnection() {
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
        } catch (Exception e) {
            mainFrm.showMessage("Error when connecting to the server!");
            return false;
        }
        return true;
    }

    public boolean sendData(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(obj);

        } catch (Exception e) {
            //e.printStackTrace();
            mainFrm.showMessage("Error when sending data to the server!");
            return false;
        }
        return true;
    }

    public boolean closeConnection() {
        try {
            if (myListening != null) {
                myListening.stop();
            }
            if (mySocket != null) {
                System.out.println("DISCONNECT");
                mySocket.close();
                mainFrm.showMessage("Disconnected from the server!");
            }
            myFunction.clear();
        } catch (Exception e) {
            //e.printStackTrace();
            mainFrm.showMessage("Error when disconnecting from the server!");
            return false;
        }
        return true;
    }

    public ArrayList<ObjectWrapper> getActiveFunction() {
        return myFunction;
    }

    class ClientListening extends Thread {

        public ClientListening() {
            super();
        }

        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    Object obj = ois.readObject();
                    if (obj instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) obj;
                        System.out.print("CLIENT_RECEIVE_REPLY: ");
                        System.out.println(data.getPerformative());
                        for (int i = 0; i < myFunction.size(); i++) {
                            ObjectWrapper fto = myFunction.get(i);
                            if (fto.getPerformative() == data.getPerformative()) {
                                switch (data.getPerformative()) {
                                    case ObjectWrapper.REPLY_LOGIN_USER:
                                        MainFrm mainFrm = (MainFrm) fto.getData();
                                        mainFrm.receivedDataProcessing(data);
                                        break;
                                    case ObjectWrapper.REPLY_SIGNUP_USER:
                                        SignupFrm signupFrm = (SignupFrm) fto.getData();
                                        signupFrm.receivedDataProcessing(data);
                                        break;

                                    case ObjectWrapper.REPLY_GET_LIST_USER:
                                    case ObjectWrapper.REPLY_ADD_FRIEND:
                                        AddFriendFrm addFriendFrm = (AddFriendFrm) fto.getData();
                                        addFriendFrm.receivedDataProcessing(data);
                                        break;

                                    case ObjectWrapper.REPLY_CONFIRM_FRIEND:
                                    case ObjectWrapper.REPLY_GET_REQUESTS:
                                        RequestFrm requestFrm = (RequestFrm) fto.getData();
                                        requestFrm.receivedDataProcessing(data);
                                        break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainFrm.showMessage("Error when receiving data from the server!");
                mainFrm.resetClient();
            }
        }
    }
}
