package com.example.dondesang;

public class CompteAdministrateur extends Compte {
    private int ID_Admin;

    public CompteAdministrateur() {
        super();
    }

    public CompteAdministrateur(String pseudo, String password) {
        super(pseudo, password);
    }

    public int getID_Admin() {
        return ID_Admin;
    }

    public void setID_Admin(int ID_Admin) {
        this.ID_Admin = ID_Admin;
    }
}
