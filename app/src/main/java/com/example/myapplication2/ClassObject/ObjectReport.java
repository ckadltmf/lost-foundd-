package com.example.myapplication2.ClassObject;

public class ObjectReport {


    private String Description;
    private String reportType;
    private String UserID;



    private boolean permission;

    public ObjectReport() {
    }

    public ObjectReport(String description, String UserID) {
        this.Description = description;
        this.UserID = UserID;

    }



    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}