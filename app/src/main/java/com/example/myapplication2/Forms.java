package com.example.myapplication2;


public class Forms {

    private String ObjectTitle;
    private String description;
    private String img;

    private String date;
    private String place;
    private String status;
    private String UserID;
    private String category;
    private String happend;


    private boolean permission;

    public Forms() {
    }

    public Forms(String ObjectTitle, String description, String img) {
        this.ObjectTitle = ObjectTitle;
        this.description = description;
        this.img = img;
    }

    public String getObjectTitle() {
        return ObjectTitle;
    }

    public void setObjectTitle(String ObjectTitle) {
        this.ObjectTitle = ObjectTitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String profilePic) {
        this.description = profilePic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHappend() {
        return happend;
    }

    public void setHappend(String happend) {
        this.happend = happend;
    }
}