package com.example.dondesang;

public class Annonce {
    Long ID;
    private String nom;
    private String prenom;
    private String gp;
    private String adresse;
    private String ntel;
    private String email;
    private long ID_user;



    public Annonce() {
        this.nom = "";
        this.prenom = "";
        this.gp = " ";
        this.adresse="";
        this.ntel="";
        this.email = "";
    }
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNtel() {
        return ntel;
    }

    public void setNtel(String ntel) {
        this.ntel = ntel;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }



}
