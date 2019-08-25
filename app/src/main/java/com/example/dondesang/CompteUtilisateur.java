package com.example.dondesang;

import android.content.ContentValues;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class CompteUtilisateur extends Compte {
    protected long ID_User;
    private String Nom;
    private String Prénom;
    private String DN;
    private String Sexe;
    private String Adresse;
    private String Email;
    private String Num_tel;
    private String GP;

    public CompteUtilisateur() {
        super();
        Nom = "";
        Prénom = "";
        DN = "";
        Sexe = "";
        Email = "";
        Num_tel = "";
        Adresse = "";
        GP = "";
    }
    public CompteUtilisateur (String pseudo,String nom, String prénom, String password, String dn, String sexe, String num_tel, String email, String adresse, String gp) {
        super(pseudo, password);
        Nom = nom;
        Prénom = prénom;
        DN = dn;
        Sexe = sexe;
        Email = email;
        Num_tel = num_tel;
        Adresse = adresse;
        GP = gp;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDN() {
        return DN;
    }

    public void setDN(String DN) {
        this.DN = DN;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getNum_tel() {
        return Num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.Num_tel = num_tel;
    }

    public String getPrénom() {
        return Prénom;
    }

    public void setPrénom(String prénom) {
        Prénom = prénom;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getGP() {
        return GP;
    }

    public void setGP(String GP) {
        this.GP = GP;
    }

    public CompteUtilisateur(long id){
        setID(id);
    }

    public long getID_User() {
        return ID_User;
    }

    public void setID_User(long ID_User) {
        this.ID_User = ID_User;
    }

    public ContentValues Values(){
        ContentValues v=new ContentValues();
        v.put("Nom", this.getNom());
        v.put("Prenom", this.getPrénom());
        v.put("DN", this.getDN());
        v.put("Sexe", this.getSexe());
        v.put("Email", this.getEmail());
        v.put("Num_tel", this.getNum_tel());
        v.put("GP", this.getGP());
        v.put("Adresse", this.getAdresse());
        v.put("ID",this.getID());
        return v;
    }

    public List<NameValuePair> pairValues(){
        List<NameValuePair> infos=new ArrayList<NameValuePair>(10);
        infos.add(new BasicNameValuePair("Nom", this.getNom()));
        infos.add(new BasicNameValuePair("Prenom", this.getPrénom()));
        infos.add(new BasicNameValuePair("DN", this.getDN()));
        infos.add(new BasicNameValuePair("Sexe", this.getSexe()));
        infos.add(new BasicNameValuePair("Email", this.getEmail()));
        infos.add(new BasicNameValuePair("Num_tel", this.getNum_tel()));
        infos.add(new BasicNameValuePair("Adresse", this.getAdresse()));
        infos.add(new BasicNameValuePair("GP", this.getGP()));
        infos.add(new BasicNameValuePair("ID",String.valueOf(this.getID())));
        return infos;
    }

}
