package com.catalog.data;

public class UserSession {
    private static UserSession instance;

    private String username;
    private UserType userType;
    private Object userDetails;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(String username, UserType userType, Object userDetails) {
        this.username = username;
        this.userType = userType;
        this.userDetails = userDetails;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public Object getUserDetails() {
        return userDetails;
    }

    public void clearSession() {
        username = null;
        userType = null;
        userDetails = null;
    }

    public enum UserType {
        STUDENT, TEACHER
    }
}
