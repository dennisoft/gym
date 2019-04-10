package com.win.components.subscription;

import java.util.Set;

public class SubscriptionResponse {

    private String responseCode;
    private String responseDesc;
    private String userID;
    private String detailedResponseDesc;
    private Set<Subscription> subscription;

    public SubscriptionResponse(){
        super();
    }

    public SubscriptionResponse(String responseCode, String responseDesc, String userID, String detailedResponseDesc) {
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

    public Set<Subscription> getSubscriptionDetails() {
        return subscription;
    }

    public void setSubscription(Set<Subscription> subscription) {
        this.subscription = subscription;
    }
}
