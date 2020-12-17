package com.example.myapplication2;


public class Items {

    private String ObjectTitle;
    private String description;
    private String img;


    private boolean permission;

    public Items() {
    }

    public Items(String ObjectTitle, String description,String img) {
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

    public boolean getPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}