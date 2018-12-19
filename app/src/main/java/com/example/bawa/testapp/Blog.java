package com.example.bawa.testapp;

public class Blog {
    private String slotID;
    private String Status;
    private String Image;

    public Blog(String slotID, String status, String image) {
        this.slotID = slotID;
        Status = status;
        Image = image;
    }

    public String getSlotID() {

        return slotID;
    }

    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
    public Blog(){

    }
}
