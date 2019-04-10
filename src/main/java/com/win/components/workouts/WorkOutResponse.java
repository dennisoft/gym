package com.win.components.workouts;

import com.win.components.subscription.Subscription;

import java.util.Set;

public class WorkOutResponse {

    private String responseCode;
    private String responseDesc;
    private String userID;
    private String detailedResponseDesc;
    private Set<WorkOut> workout;

    public WorkOutResponse(){
        super();
    }

    public WorkOutResponse(String responseCode, String responseDesc, String userID, String detailedResponseDesc) {
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

    public Set<WorkOut> getWorkOutDetails() {
        return workout;
    }

    public void setWorkOut(Set<WorkOut> workout) {
        this.workout = workout;
    }
}
