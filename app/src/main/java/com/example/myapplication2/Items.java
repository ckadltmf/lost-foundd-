package com.example.myapplication2;


public class Items {

    private String place;
    private String description;
    private String img;


    private boolean permission;

    public Items() {
    }

    public Items(String ObjectTitle, String description,String img) {
        this.place = ObjectTitle;
        this.description = description;
        this.img = img;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public boolean getPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}