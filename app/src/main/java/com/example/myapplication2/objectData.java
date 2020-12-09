package com.example.myapplication2;

public class objectData {
    //private int serialNum;
    private String Category;
    private String lostOrFound;
    private String Description;
    public objectData(String lostOrFound, String Category , String description) {
      //  this.serialNum = num;
        this.Category = Category;
        this.lostOrFound = lostOrFound;
        this.Description=description;
    }

    public String getCategory() {
        return Category;
    }
    public void setCategory(String category) {
        this.Category = category;
    }
    public String getLostOrFound() {
        return lostOrFound;
    }
    public void setLostOrFound(String lostOrFound) {
        this.lostOrFound = lostOrFound;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        this.Description = description;
    }
}