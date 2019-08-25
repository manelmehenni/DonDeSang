package com.example.dondesang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Inscription extends infoProfil implements AdapterView.OnItemSelectedListener {

  public Button btn_confirmer;
    private MyRequest request;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        btn_confirmer= (Button) findViewById(R.id.btn_confirmer);


        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        datenaiss = (EditText) findViewById(R.id.datenaiss);
        taille = (EditText) findViewById(R.id.taille);
        poids = (EditText) findViewById(R.id.poids);
        adresse = (EditText) findViewById(R.id.adresse);
        groupesanguin = (EditText) findViewById(R.id.groupesanguin);
        sexe= (RadioGroup) findViewById(R.id.sexeG);
        nomutilisateur = (EditText) findViewById(R.id.nomutilisateur);
        email = (EditText) findViewById(R.id.email);
        ntel = (EditText) findViewById(R.id.ntel);
        mdp = (EditText) findViewById(R.id.mdp);
        cmdp = (EditText) findViewById(R.id.cmdp);

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);


        //La liste des groupe sanguins
        Spinner spinner= findViewById(R.id.spinner1);
        groupesanguin= (EditText) findViewById(R.id.groupesanguin);
        if(spinner!=null)
        {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.groupeS, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);




        btn_confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //récupérer les données en cliquant sur le boutton
                recupererString();
                request.register(snom, sprénom, sdn, staille, spoids, sadresse, ssexe, gp, spseudo, mail, snum_tel, smdp, smdp1, new MyRequest.RegisterCallBack() {
                    @Override
                    public void onSuccess(String message) {

                        int p=Integer.parseInt(spoids.replaceAll("[\\D]","")); //Convertir en int;
                        if(p  >= 50){
                            Intent i = new Intent(getApplicationContext(), ProfilDonneur.class);
                            i.putExtra("pseudo", spseudo); //pour le passer dans une autre activity
                            startActivity(i);
                            finish();
                        }else{
                            Intent i2 = new Intent(getApplicationContext(), ProfilReceveur.class);
                            i2.putExtra("pseudo", spseudo); //pour le passer dans une autre activity
                            startActivity(i2);
                            finish();
                        }

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


    }



    @Override
    //selection du groupe sanguin
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!= 0){
        String text= parent.getItemAtPosition(position).toString();
        groupesanguin.setText(text);}

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void RadioButtonSexe(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rd_femme:
                if (checked)

                    break;
            case R.id.rd_homme:
                if (checked)

                    break;
        }
    }
}
