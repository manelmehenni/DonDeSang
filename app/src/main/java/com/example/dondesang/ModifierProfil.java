package com.example.dondesang;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.dondesang.Request.MyRequest;

import java.util.Map;

public class ModifierProfil extends infoProfil implements AdapterView.OnItemSelectedListener {
   public EditText nom_modifié, prénom_modifié, dn_modifié, adresse_modifiée,  gpmodifié, taille_modifiée, poids_modifié, pseudo_modifié,email_modifié, num_tel_modifié, mdp_modifié, confirmer_modifié;
   public Button valider, annuler;
   public RadioGroup sexe_modifié;

   String nom1, prenom1, dn1, adresse1, gp1, taille1, poids1, pseudo1, email1, ntel1, mdp1, mdp2, sexe1;

    private MyRequest request;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil);

        nom_modifié= (EditText) findViewById(R.id.nom_modifié);
        prénom_modifié= (EditText) findViewById(R.id.prénom_modifié);
        dn_modifié= (EditText) findViewById(R.id.dn_modifié);
        adresse_modifiée= (EditText) findViewById(R.id.adresse_modifiée);
        taille_modifiée= (EditText) findViewById(R.id.taille_modifiée);
        poids_modifié= (EditText) findViewById(R.id.poids_modifié);
        pseudo_modifié= (EditText) findViewById(R.id.pseudo_modifié);
        email_modifié= (EditText) findViewById(R.id.email_modifié);
        num_tel_modifié= (EditText) findViewById(R.id.num_tél_modifié);
        mdp_modifié= (EditText) findViewById(R.id.mdp_modifié);
        confirmer_modifié= (EditText) findViewById(R.id.confirmé_modifié);
        sexe_modifié= (RadioGroup) findViewById(R.id.sexe);
        valider= (Button) findViewById(R.id.valider_modif_profil);
        annuler= (Button) findViewById(R.id.annuler_modif_profil);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

       valider.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //recupérer les informations
               nom1= nom_modifié.getText().toString().trim();
               prenom1 = prénom_modifié.getText().toString().trim();
               dn1 = dn_modifié.getText().toString().trim();
               poids1 =poids_modifié.getText().toString().trim();
               taille1 = taille_modifiée.getText().toString().trim();
               adresse1 = adresse_modifiée.getText().toString().trim();

               gp1 = gpmodifié.getText().toString().trim();
               pseudo1 = pseudo_modifié.getText().toString().trim();
               email1= email_modifié.getText().toString().trim();
               ntel1= num_tel_modifié.getText().toString().trim();
               mdp1=mdp_modifié.getText().toString().trim();
               mdp2= confirmer_modifié.getText().toString().trim();

           request.upadate(nom1, prenom1, dn1, adresse1, taille1, poids1, sexe1, gp1, pseudo1, email1, ntel1, mdp1, mdp2, new MyRequest.updateCallBack() {
               @Override
               public void onSuccess(String message) {
                   Toast.makeText(getApplicationContext(), "Les informations ont bien été modifiées", Toast.LENGTH_SHORT).show();
               }

               @Override
               public void inputErrors(Map<String, String> errors) {

               }

               @Override
               public void onError(String message) {
                   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
               }
           });
           }
       });

        //La liste des groupe sanguins
        Spinner spinner= findViewById(R.id.spinner2);
        gpmodifié= (EditText) findViewById(R.id.gp_modifié);
        if(spinner!=null)
        {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.groupeS, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        gpmodifié.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

