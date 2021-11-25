package model;

import java.io.Serializable;

public class ObjectWrapper implements Serializable {

    private static final long serialVersionUID = 20210811011L;
    public static final int LOGIN_USER = 1;
    public static final int REPLY_LOGIN_USER = 2;
    public static final int SIGNUP_USER = 3;
    public static final int REPLY_SIGNUP_USER = 4;
    public static final int LIST_FRIENDS = 5;
    public static final int REPLY_LIST_FRIENDS = 6;
    public static final int LIST_GROUPS = 7;
    public static final int REPLY_LIST_GROUPS = 8;
    public static final int LIST_REQUESTS = 9;
    public static final int REPLY_LIST_REQUESTS = 10;
    public static final int DELETE_FRIEND = 11;
    public static final int REPLY_DELETE_FRIEND = 12;
    public static final int REFUSE_REQUEST = 13;
    public static final int REPLY_REFUSE_REQUEST = 14;
    public static final int ACCEPT_REQUEST = 15;
    public static final int REPLY_ACCEPT_REQUEST = 16;
    public static final int SEARCH_USER = 17;
    public static final int REPLY_SEARCH_USER = 18;
    public static final int SEARCH_USER_DETAIL = 19;
    public static final int REPLY_SEARCH_USER_DETAIL = 20;
    public static final int ADD_FRIEND = 21;
    public static final int REPLY_ADD_FRIEND = 22;
    public static final int LEAVE_GROUP = 23;
    public static final int REPLY_LEAVE_GROUP = 24;
    public static final int DELETE_GROUP = 25;
    public static final int REPLY_DELETE_GROUP = 26;
    public static final int CREATE_PRIVATE_CALL = 27;
    public static final int REPLY_CREATE_PRIVATE_CALL = 28;
    public static final int INCOMING_CALL = 29;
    public static final int REPLY_INCOMING_CALL = 30;
    public static final int DENY_CALL = 31;
    public static final int ACCEPT_CALL = 32;
    public static final int SERVER_INFORM_CLIENT_NUMBER = 100;
    public static final int USER_OUT = 101;
    public static final int SERVER_INFORM_ACTIVE_USER = 102;

    private int performative;
    private Object data;

    public ObjectWrapper() {
        super();
    }

    public ObjectWrapper(int performative, Object data) {
        super();
        this.performative = performative;
        this.data = data;
    }

    public int getPerformative() {
        return performative;
    }

    public void setPerformative(int performative) {
        this.performative = performative;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        ObjectWrapper ow = new ObjectWrapper();
//        ow.setPerformative(this.performative);
//        ow.setData(this.data);
//
//        return ow; //To change body of generated methods, choose Tools | Templates.
//    }
}
