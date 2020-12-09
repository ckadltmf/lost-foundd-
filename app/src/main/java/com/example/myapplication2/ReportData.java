package com.example.myapplication2;

public class ReportData {

    //private int serialNum;
    private String UserID;
    private String ReportSubject;
    private String Description;
    public ReportData(String UserID, String ReportSubject , String description) {
        //  this.serialNum = num;
        this.UserID = UserID;
        this.ReportSubject = ReportSubject;
        this.Description=description;
    }

    public String getUserID() {
        return UserID;
    }
    public void setUserID(String userID) {
        this.UserID = userID;
    }
    public String getReportSubject() {
        return ReportSubject;
    }
    public void setReportSubject(String reportSubject) {
        this.ReportSubject = reportSubject;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        this.Description = description;
    }
}
