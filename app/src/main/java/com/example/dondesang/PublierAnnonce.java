package com.example.dondesang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PublierAnnonce extends AppCompatActivity {
    EditText nom, prenom, commune, adresse, gp, num_tel, email;
    Button valider, annuler;
    String Nom= null, Prenom= null, Commune= null , Adresse= null, Email= null, Ntel= null, Gp= null;
    Long ID_annonce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publier_annonce);
        getSupportActionBar().hide();


        Intent intent=getIntent();
        ID_annonce=intent.getLongExtra("ID_annonce",898);

        nom= (EditText)(findViewById(R.id.nom1));
        prenom= (EditText)(findViewById(R.id.prenom1));
        adresse= (EditText)(findViewById(R.id.adresse1));
        commune=(EditText) (findViewById(R.id.commune1));
        gp= (EditText)(findViewById(R.id.gp1));
        num_tel= (EditText)(findViewById(R.id.num_tel1));
        email= (EditText)(findViewById(R.id.email1));
        valider= (Button) (findViewById(R.id.valider));
        annuler= (Button)(findViewById(R.id.annuler));

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recupererString();
                if(!verifieInfo()){
                SaveTask save=new SaveTask();
                save.execute((Void)null);}

                Intent intent=new Intent();
                setResult(RESULT_OK,intent);

            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });


    }

    public void recupererString(){
        Nom=nom.getText().toString().trim();
        Prenom=prenom.getText().toString().trim();
        Adresse=adresse.getText().toString().trim();
        Commune=commune.getText().toString().trim();
        Gp=gp.getText().toString().trim();
        Email=email.getText().toString().trim();
        Ntel=num_tel.getText().toString().trim();

    }


    public Boolean verifieInfo() {
        Boolean erreur = false;

        if (TextUtils.isEmpty(Nom)) {
            nom.setError("Champ obligatoire");
            erreur = true;
        }
        if (TextUtils.isEmpty(Prenom)) {
            prenom.setError("Champ obligatoire");
            erreur = true;
        }
        if (TextUtils.isEmpty(Gp)) {
            gp.setError("Champ obligatoire");
            erreur = true;
        }

        if (TextUtils.isEmpty(Adresse)) {
            adresse.setError("Champ obligatoire");
            erreur = true;
        }
        if (TextUtils.isEmpty(Commune)) {
            commune.setError("Champ obligatoire");
            erreur = true;
        }
        if (TextUtils.isEmpty(Email)) {
            email.setError("Champ obligatoire");
            erreur = true;
        }
        if (TextUtils.isEmpty(Ntel)) {
            num_tel.setError("Champ obligatoire");
            erreur = true;
        }
        return erreur;
    }

    public class SaveTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean b= true;
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://10.0.2.2/DonDeSang/PublierAnnonce.php");
            try{
                List<NameValuePair> infos = new ArrayList<>(20);
                infos.add(new BasicNameValuePair("ID_annonce", String.valueOf(ID_annonce)));
                infos.add(new BasicNameValuePair("Nom", Nom));
                infos.add(new BasicNameValuePair("Prenom", Prenom));
                infos.add(new BasicNameValuePair("Gp", Gp));
                infos.add(new BasicNameValuePair("Adresse", Adresse));
                infos.add(new BasicNameValuePair("Commune", Commune));
                infos.add(new BasicNameValuePair("Ntel", Ntel));
                infos.add(new BasicNameValuePair("Email", Email));
                post.setEntity(new UrlEncodedFormEntity(infos));
                client.execute(post);
                b=true;
            }
            catch (ClientProtocolException e){
                e.printStackTrace();
            }
            catch (HttpHostConnectException e){
                b=false;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }

        @Override
        protected void onPostExecute(Boolean a) {
            Toast.makeText(PublierAnnonce.this, "Annonce publiée", Toast.LENGTH_LONG).show();
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
            finish();

        }
    }

    public AlertDialog.Builder Alerte(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Pas de connexion Wifi");
        builder.setMessage("Veuillez vous connectez ");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder;
    }
}
