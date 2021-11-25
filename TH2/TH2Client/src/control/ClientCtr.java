package control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

//import jdbc.dao.UserDAO;
import model.IPAddress;
import model.ObjectWrapper;
//import view.Sudoku;

public class ClientCtr {

    private Socket mySocket;
    private ArrayList<Integer> listUserId;
    private boolean inCall;
//    private Sudoku view;
    private ClientListening myListening;                            // thread to listen the data from the server
    private ArrayList<ObjectWrapper> myFunction;                  // list of active client functions
    private IPAddress serverAddress = new IPAddress("localhost", 8888);  // default server host and port

    public ClientCtr(Socket mySocket) {
        this.mySocket = mySocket;
    }

    public Socket getMySocket() {
        return mySocket;
    }

    public void setMySocket(Socket mySocket) {
        this.mySocket = mySocket;
    }

//    public ClientCtr(Sudoku view) {
//        super();
//        this.view = view;
//        myFunction = new ArrayList<ObjectWrapper>();
//    }

    public ArrayList<Integer> getListUserId() {
        return listUserId;
    }

    public void setListUserId(ArrayList<Integer> listUserId) {
        this.listUserId = listUserId;
    }

    public boolean isInCall() {
        return inCall;
    }

    public void setInCall(boolean inCall) {
        this.inCall = inCall;
    }

//    public ClientCtr(Sudoku view, IPAddress serverAddr) {
//        super();
//        this.view = view;
//        this.serverAddress = serverAddr;
//        myFunction = new ArrayList<ObjectWrapper>();
//    }

    public boolean openConnection() {
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean sendData(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(obj);

        } catch (Exception e) {
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
                mySocket.close();
            }
            myFunction.clear();
        } catch (Exception e) {
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

                        if (data.getPerformative() == ObjectWrapper.SERVER_INFORM_ACTIVE_USER) {
                            ArrayList<Integer> listUserActive = (ArrayList<Integer>) data.getData();
                            setListUserId(listUserActive);
                        } else {
                            for (ObjectWrapper fto : myFunction) {
                                if (fto.getPerformative() == data.getPerformative()) {
                                    switch (data.getPerformative()) {
                                        case ObjectWrapper.REPLY_LOGIN_USER:

                                            break;
                                    }
                                }
                            }
                            //view.showMessage("Received an object: " + data.getPerformative());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                view.showMessage("Error when receiving data from the server!");
//                view.resetClient();
            }
        }
    }
}
