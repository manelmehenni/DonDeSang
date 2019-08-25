package com.example.dondesang;

import android.content.ContentValues;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class Compte {

    private long ID;
    private String Pseudo;
    private String Password;



    public Compte() {
        Pseudo = "";
        Password = "";
    }

    public Compte(String pseudo, String password) {
        Pseudo = pseudo;
        Password = password;
    }

    public long getID() {
        return ID;
    }

    public String getPseudo() {
        return Pseudo;
    }

    public String getPassword() {
        return Password;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setPseudo(String pseudo) {
        Pseudo = pseudo;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public ContentValues Values() {
        ContentValues value = new ContentValues();
        value.put("Pseudo", this.getPseudo());
        value.put("Password", this.getPassword());
        return value;
    }

    public List<NameValuePair> pairValues(){
        List<NameValuePair> infos=new ArrayList<NameValuePair>(5);
        infos.add(new BasicNameValuePair("Pseudo", this.getPseudo()));
        infos.add(new BasicNameValuePair("Password", this.getPassword()));
        return infos;
    }


}
