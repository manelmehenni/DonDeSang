package com.example.dondesang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dondesang.donneur_receveur.Notificationdonneur;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ApercuAnnonceActivity extends AppCompatActivity {

    private long ID_Annonce;
    private long ID_User;
    private long ID_profil;
    private String type_profil;
    private TextView nom;
    private TextView prenom;
    private TextView gp;
    private TextView adresse;
    private TextView numtel;
    private TextView email;
    private Button accepter;
    private Button refuser;
    ImageButton notification;
    SearchView recherche;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apercu_annonce);

        final Intent intent = getIntent();
        ID_Annonce = intent.getLongExtra("ID_Annonce", 495);
        Log.i("!!!!!!!!!", "je passe par là"+ID_Annonce);

        ID_profil = intent.getLongExtra("ID", 495);
        type_profil = intent.getStringExtra("typeProfil");


        nom=findViewById(R.id.nom2);
        prenom=findViewById(R.id.prenom2);
        adresse=findViewById(R.id.Adresse2);
        gp= findViewById(R.id.groupesanguin2);
        numtel=findViewById(R.id.numtel);
        email=findViewById(R.id.email2);
        accepter = (Button) findViewById(R.id.accepter);
        refuser= (Button) findViewById(R.id.refuser);
        back=findViewById(R.id.back);
        notification= findViewById(R.id.notif_profil_perso_donneur);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type_profil.equals("donneur_receveur")){
                    Intent intent = new Intent(ApercuAnnonceActivity.this, Notificationdonneur.class);
                    intent.putExtra("ID_donneur", ID_profil);
                    startActivity(intent);
                }
            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    public class IDUserTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            Integer b = 1;
            StringBuilder reponse = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://10.0.2.2/DonDeSang/ID_User.php");
            try {
                List<NameValuePair> infos = new ArrayList<>(5);
                infos.add(new BasicNameValuePair("ID_profil", String.valueOf(ID_profil)));
                infos.add(new BasicNameValuePair("type_profil", String.valueOf(type_profil)));
                post.setEntity(new UrlEncodedFormEntity(infos));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();//récupérer corps de la reponse
                InputStream content = entity.getContent();//lire (récuperer des octet)le contenu car par car et former une chain//inputstreamreader on reconstruit les car et bufferReader la ligne
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                if ((line = reader.readLine()) != null) {
                    reponse.append(line);
                }

                JSONObject jObj = new JSONObject(reponse.toString());
                ID_User=Long.valueOf(jObj.getString("ID_User"));

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return b;
        }


    }
}
