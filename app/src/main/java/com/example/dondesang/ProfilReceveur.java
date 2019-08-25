package com.example.dondesang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dondesang.donneur_receveur.Notificationdonneur;

public class ProfilReceveur extends AppCompatActivity {
    private SessionManager sessionManager;
    private TextView pseudo_prflrc;
    private long ID_rec;
    ImageButton notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profil_receveur);
        getSupportActionBar().hide();

        pseudo_prflrc= findViewById(R.id.pseudo_prfl_rc);
        //Recuperer le pseudo dans le textview pseudo_prfl_don
        Bundle b1= getIntent().getExtras();
        String psd= b1.getString("pseudo");
        pseudo_prflrc.setText(psd);


        Intent intent = getIntent();
        final long ID_receveur= intent.getLongExtra("ID_receveur",198);
        ID_rec=ID_receveur;

        TextView modifier = findViewById(R.id.modifier_profil_perso);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilReceveur.this, ModifierProfil.class);
                intent.putExtra("ID_profil",ID_receveur);
                startActivity(intent);
            }
        });


        notification=findViewById(R.id.notif_profil_perso_receveur);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilReceveur.this, Notificationdonneur.class);
                intent.putExtra("ID_receveur", ID_receveur);
                startActivity(intent);
            }
        });

        TextView publier_annonces = findViewById(R.id.PbAnnonces);
        publier_annonces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilReceveur.this, PublierAnnonce.class);
                intent.putExtra("ID_profil",ID_receveur);
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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ProfilReceveur.this);
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
