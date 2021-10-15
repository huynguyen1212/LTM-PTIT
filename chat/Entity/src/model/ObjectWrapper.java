package model;

import java.io.Serializable;

public class ObjectWrapper implements Serializable {

    private static final long serialVersionUID = 20210811011L;

    public static final int SERVER_INFORM_CLIENT_NUMBER = 0;

    public static final int LOGIN_USER = 1;
    public static final int REPLY_LOGIN_USER = 2;
    // user
    public static final int SIGNUP_USER = 3;
    public static final int REPLY_SIGNUP_USER = 4;
    // FRIEND
    public static final int ADD_FRIEND = 5;
    public static final int REPLY_ADD_FRIEND = 6;

    public static final int CONFIRM_FRIEND = 7;
    public static final int REPLY_CONFIRM_FRIEND = 8;

    public static final int GET_LIST_USER = 9;
    public static final int REPLY_GET_LIST_USER = 10;

    public static final int GET_LIST_FRIEND = 11;
    public static final int REPLY_GET_LIST_FRIEND = 12;
    
    public static final int GET_REQUESTS = 13;
    public static final int REPLY_GET_REQUESTS = 14;

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
}
