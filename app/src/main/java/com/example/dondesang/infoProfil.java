package com.example.dondesang;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class infoProfil extends Activity {
    public EditText nom = null;
    public EditText prenom =null;
    public EditText datenaiss = null;
    public EditText taille= null;
    public EditText  poids = null;
    public EditText adresse = null;
    public EditText groupesanguin= null;
    public RadioGroup sexe=null;
    public EditText  nomutilisateur = null;
    public  EditText email= null;
    public  EditText ntel = null;
    public  EditText mdp = null;
    public  EditText cmdp = null;




    String snom = null;
    String sprénom = null;
    String sdn = null;
    String staille = null;
    String spoids = null;
    String sadresse = null;
    String gp = null;
    String ssexe = null;
    String spseudo = null;
    String mail = null;
    String snum_tel = null;
    String smdp = null;
    String smdp1 = null;

    public void recupererString(){
        snom = nom.getText().toString().trim();
        sprénom = prenom.getText().toString().trim();
        sdn = datenaiss.getText().toString().trim();
        spoids =poids.getText().toString().trim();
        staille = taille.getText().toString().trim();
        sadresse = adresse.getText().toString().trim();
        if(R.id.rd_homme==sexe.getCheckedRadioButtonId()) ssexe="Homme";
        else ssexe="Femme";
        gp = groupesanguin.getText().toString().trim();
        spseudo = nomutilisateur.getText().toString().trim();
        mail= email.getText().toString().trim();
        snum_tel= ntel.getText().toString().trim();
        smdp=mdp.getText().toString().trim();
        smdp1= cmdp.getText().toString().trim();

    }

    public Boolean ErreurMdp() {
        Boolean erreur = false;
        if (TextUtils.isEmpty(smdp)) {
            mdp.setError("Champ obligatoire");
            erreur = true;
        } else {
            if ((smdp.length() < 8)) {
                mdp.setError("Mot de passe trop court");
                erreur = true;
            }
        }
        if (TextUtils.isEmpty(smdp1)) {
            cmdp.setError("Champ obligatoire");
            erreur = true;
        } else {
            if (!smdp1.equals(smdp)) {
                cmdp.setError("mots de passe non identiques");
                erreur = true;
            }
        }
        return erreur;
    }

    public Boolean ErreurName(EditText name, String sname) {
        Boolean erreur =false;
        if (TextUtils.isEmpty(sname)) {
            name.setError("Champ obligatoire");
            erreur = true;
        } else {
            if (sname.length() > 20) {
                name.setError("trop long");
                erreur = true;
            } else {
                Pattern p;
                Matcher m;
                p = Pattern.compile("[\\w-_\\sáàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ&&\\D]*", Pattern.CASE_INSENSITIVE);
                m = p.matcher(sname);
                if (!m.find()) {
                    name.setError("invalide");
                    erreur=true;
                }
            }
        }
        return erreur;
    }

    public Boolean ErreurNum(){
        Pattern p;
        Matcher m;
        Boolean erreur=false;
        p = Pattern.compile("(0|\\+213|00213)[0-9]{9}", Pattern.CASE_INSENSITIVE);
        m = p.matcher(snum_tel);
        if (!m.find()) {
            ntel.setError("invalide");
            erreur=true;
        }
        return erreur;
    }

    public boolean isThisDateValid(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/mm/dd");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd");
        sdf.setLenient(false);
        sdf2.setLenient(false);
        sdf3.setLenient(false);

        try {
            Date date = sdf.parse(sdn);
            System.out.println(date);

        } catch (ParseException e) {
            try {
                Date date = sdf2.parse(sdn);
                System.out.println(date);

            } catch (ParseException e1) {

                try {
                    Date date = sdf3.parse(sdn);
                    System.out.println(date);

                } catch (ParseException e2) {
                    datenaiss.setError("date invalide(format yyyy/mm/jj)");
                    return false;
                }

            }
        }

        return true;
    }
}
