package com.win.components.reports;

import java.util.Set;

public class ReportsResponse {

    private String responseCode;
    private String responseDesc;
    private String detailedResponseDesc;
    private Set<Reports> reports;

    public ReportsResponse() {
        this.responseCode = responseCode;
        this.responseDesc = responseDesc;
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


    public String getDetailedResponseDesc() {
        return detailedResponseDesc;
    }

    public void setDetailedResponseDesc(String detailedResponseDesc) {
        this.detailedResponseDesc = detailedResponseDesc;
    }

    public Set<Reports> getReports() {
        return reports;
    }

    public void setReports(Set<Reports> reports) {
        this.reports = reports;
    }
}
