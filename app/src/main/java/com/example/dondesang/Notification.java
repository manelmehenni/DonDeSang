package com.example.dondesang;

public class Notification {
    private long ID;
    private long ID_user;
    private String contenue;
    private int type;
    private long ID_cible;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getID_user() {
        return ID_user;
    }

    public void setID_user(long ID_user) {
        this.ID_user = ID_user;
    }

    public String getContenue() {
        return contenue;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getID_cible() {
        return ID_cible;
    }

    public void setID_cible(long ID_cible) {
        this.ID_cible = ID_cible;
    }
}
