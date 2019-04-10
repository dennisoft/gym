package com.win.components.users;

import java.util.Set;

public class UserResponse {

    private String responseCode;
    private String responseDesc;
    private String userID;
    private String detailedResponseDesc;
    private Set<User> user;

    public UserResponse(){
        super();
    }

    public UserResponse(String responseCode, String responseDesc, String userID, String detailedResponseDesc) {
        this.responseCode = responseCode;
        this.responseDesc = responseDesc;
        this.detailedResponseDesc = detailedResponseDesc;
        this.userID = userID;
    }

    public String getDetailedResponseDesc() {
        return detailedResponseDesc;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDetailedResponseDesc(String detailedResponseDesc) {
        this.detailedResponseDesc = detailedResponseDesc;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }
}
