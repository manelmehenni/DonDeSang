package com.example.dondesang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MesAnnonces extends AppCompatActivity {
    Long ID_don;
    TextView aucun;
    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        ID_don= intent.getLongExtra("ID_donneur",0);

        aucun= findViewById(R.id.aucune_annonce);

        recycler=findViewById(R.id.mes_annonces);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        AnnonceTask eventTask=new AnnonceTask();
        eventTask.execute((Void) null);
    }

    @SuppressLint("StaticFieldLeak")
    public class AnnonceTask extends AsyncTask<Void, Void,Integer> {

        List<Annonce> annonces;

        AnnonceTask() {
            annonces= new ArrayList<>();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer b=1;
            StringBuilder reponse = new StringBuilder();
            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost("http://10.0.2.2/DonDeSang/ChargerAnnoce.php");
            try {
                List<NameValuePair> infos=new ArrayList<>(5);
                infos.add(new BasicNameValuePair("ID_donneur",String.valueOf(ID_don)));
                post.setEntity(new UrlEncodedFormEntity(infos));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();//récupérer corps de la reponse
                InputStream content = entity.getContent();//lire (récuperer des octet)le contenu car par car et former une chain//inputstreamreader on reconstruit les car et bufferReader la ligne
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line ;
                while ((line =reader.readLine()) != null) {
                    reponse.append(line);
                }
                /* Résultats de la requête */
                JSONArray jsonArray = new JSONArray(reponse.toString());
                JSONObject j = jsonArray.getJSONObject(0);
                if (j.getBoolean("vide"))
                {b=2;}
                else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        Annonce annonce=new Annonce();
                        annonce.setNom(jObj.getString("Nom"));
                        annonce.setPrenom(jObj.getString("Prenom"));
                        annonce.setGp(jObj.getString("Groupe_sanguin"));
                        annonce.setAdresse(jObj.getString("Adresse"));
                        annonce.setNtel(jObj.getString("NumTel"));
                        annonce.setID(Long.valueOf(jObj.getString("ID_Annonce")));

                    }
                }

            }
            catch (ClientProtocolException e){e.printStackTrace();}
            catch (IOException e){e.printStackTrace();}
            catch (Exception e){e.printStackTrace();}
            return b;}

        @Override
        protected void onPostExecute(Integer a) {
            if(a==2){aucun.setVisibility(View.VISIBLE);}
            else{
                recycler.setAdapter(new MyAdapter(annonces,MesAnnonces.this,"donneur",ID_don));
            }
        }


    }
}
