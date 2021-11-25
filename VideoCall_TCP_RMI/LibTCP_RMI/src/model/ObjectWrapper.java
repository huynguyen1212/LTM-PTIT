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
    public static final int LINE_BUSY = 33;
    public static final int END_CALL = 34;
        public static final int CREATE_GROUP = 35;
    public static final int REPLY_CREATE_GROUP = 36;
    public static final int GET_MEMBER_IN_GROUPS = 37;
    public static final int REPLY_GET_MEMBER_IN_GROUPS = 38;
    public static final int ADD_MEMBER = 39;
    public static final int REPLY_ADD_MEMBER = 40;
    public static final int ACCEPT_MEMBER = 41;
    public static final int REPLY_ACCEPT_MEMBER = 42;
    public static final int REFUSE_MEMBER = 43;
    public static final int REPLY_REFUSE_MEMBER = 44;
    public static final int JOIN_GROUP = 45;
    public static final int REPLY_JOIN_GROUP = 46;
    public static final int SEARCH_GROUP = 47;
    public static final int REPLY_SEARCH_GROUP = 48;
    public static final int LIST_REQUEST_JOIN_GROUP = 49;
    public static final int REPLY_LIST_REQUEST_JOIN_GROUP = 50;
    public static final int LIST_FRIEND_TO_ADD_GROUP = 51;
    public static final int REPLY_LIST_FRIEND_TO_ADD_GROUP = 52;
    public static final int SERVER_INFORM_CLIENT_NUMBER = 100;
    public static final int USER_OUT = 101;
    public static final int SERVER_INFORM_ACTIVE_USER = 102;
    
    public static final int SENT_VIDEO = 113;
    public static final int RECIEVE_VIDEO = 114;
    
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
