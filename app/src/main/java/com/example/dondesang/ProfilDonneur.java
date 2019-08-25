package com.example.dondesang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dondesang.donneur_receveur.Notificationdonneur;

public class ProfilDonneur extends AppCompatActivity {
    private SessionManager sessionManager;

    ImageButton Accueil;

    private TextView pseudo;

    private long ID_don;

    ImageButton notification;
    SearchView recherche;
    private TextView pseudo_prfl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profildonneur);
        getSupportActionBar().hide();
        pseudo_prfl= findViewById(R.id.pseudo_prfl_don);
        //Recuperer le pseudo dans le textview pseudo_prfl_don
        Bundle b= getIntent().getExtras();
        String ps= b.getString("pseudo");
        pseudo_prfl.setText(ps);

        sessionManager = new SessionManager(this);
        if(sessionManager.isLogged()){
            String pseudo= sessionManager.getPseudo();
            String id= sessionManager.getId();

        }

        Intent intent = getIntent();
        final long ID_donneur= intent.getLongExtra("ID_donneur",198);
        ID_don=ID_donneur;



        notification=findViewById(R.id.notif_profil_perso_donneur);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilDonneur.this, Notificationdonneur.class);
                intent.putExtra("ID_donneur", ID_donneur);
                startActivity(intent);
            }
        });

        TextView modifier = findViewById(R.id.modifier_profil_perso);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilDonneur.this, ModifierProfil.class);
                intent.putExtra("ID_profil",ID_donneur);
                startActivity(intent);
            }
        });

        TextView mes_annonces = findViewById(R.id.PbAnnonces);
        mes_annonces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilDonneur.this, MesAnnonces.class);
                intent.putExtra("ID_profil",ID_donneur);
                startActivity(intent);
            }
        });


        TextView publier_annonces = findViewById(R.id.PAnnonces);
        publier_annonces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilDonneur.this, PublierAnnonce.class);
                intent.putExtra("ID_profil",ID_donneur);
                startActivity(intent);
            }
        });


        TextView supprimer = findViewById(R.id.supprimer_compte_perso);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SupprimerCompte.class);
                startActivityForResult(intent,1);
            }
        });

        TextView deconnecter = findViewById(R.id.déconnecter);
        deconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ProfilDonneur.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putString("Connecté", "false");
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
