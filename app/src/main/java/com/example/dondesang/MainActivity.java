package com.example.dondesang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.dondesang.Request.MyRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.util.Log.e;

public class MainActivity extends AppCompatActivity  {
    private Connexiontask task;
    private Button btn_login;
    private Button btn_register;
    private EditText pseudo, mdp;
    private View progressBar;
    private View mLoginFormView;
    String j="";
    JSONObject jObj= null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("BlooDonation");
        getSupportActionBar().hide();
        //INITIALISATION
        btn_login= (Button) findViewById(R.id.btn_login);
        btn_register= (Button) findViewById(R.id.btn_register);
        pseudo= (EditText) findViewById(R.id.pseudo);
        mdp= (EditText) findViewById(R.id.mdp);
        progressBar= findViewById(R.id.progressBar);
        mLoginFormView = findViewById(R.id.log);

       /* queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);
        SessionManager = new SessionManager(this);*/

/*        if(SessionManager.isLogged()){
            Intent intent= new Intent(this, ProfilDonneur.class);
            startActivity(intent);
            finish();
        }*/

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Redirection vers la page inscription
                Intent intent= new Intent(getApplicationContext(),Inscription.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
    }


   /* private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }*/

    private void login(){
        //Restaurer erreur
        Log.d("msg1", "etape1******************");
        pseudo.setError(null);
        mdp.setError(null);

        //récupérer les données
        final String lepseudo= pseudo.getText().toString().trim();
        final String lemdp= mdp.getText().toString().trim();

        boolean cancel = false;

        // vérifier validité mot de passe
        if (TextUtils.isEmpty(lemdp)) {
            mdp.setError("Entrez le mot de passe");
            cancel = true;
        }
        else {
            if ((lemdp.length() < 7)) {
                mdp.setError("Mot de passe trop court");
                cancel = true;
            }
        }
        //Vérifier que le pseudo a été saisi
        if (TextUtils.isEmpty(lepseudo)) {
            pseudo.setError("champ obligatoire");
            cancel = true;
        }

        if (!cancel)
        { //showProgress(true);
            Connexiontask  task = new Connexiontask(lepseudo, lemdp);
            task.execute((Void) null);
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class Connexiontask extends AsyncTask<Void, Void,Integer>{
        private final String Nomuser;
        private final String Password;
        private  long ID_profil;

        Connexiontask(String nomuser, String password) {
            Log.d("msg1", "etape2******************");
            Nomuser = nomuser;
            Password = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Log.d("msg1", "etape3******************");
            Integer b=10;
            StringBuilder reponse = new StringBuilder();
            HttpClient client=new DefaultHttpClient();
            HttpPost post=new HttpPost("http://10.0.2.2/DonDeSang/connexion3.php");
            try {
                List<NameValuePair> infos=new ArrayList<>(5);
                infos.add(new BasicNameValuePair("pseudo",Nomuser));
                infos.add(new BasicNameValuePair("mdp",Password));
                infos.add(new BasicNameValuePair("tag","Connexion"));
                Log.d("msg1", "etape4******************");
                post.setEntity(new UrlEncodedFormEntity(infos));
                Log.d("msg1", "etape5******************");
                HttpResponse response = client.execute(post);
                Log.d("msg1", "etape6******************");
                HttpEntity entity = response.getEntity();//récupérer corps de la reponse
                InputStream content = entity.getContent();//lire (récuperer des octet)le contenu car par car et former une chaine
                //inputstreamreader on reconstruit les car et bufferReader la ligne
                Log.d("msg1", "etape7******************");
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line = reader.readLine();
                if (line != null)
                    reponse.append(line);

                /* Résultats de la requête */
                Log.d("msg1", "etape8******************");
                JSONObject jObj = new JSONObject(reponse.toString());


               // JSONArray jsonArray = new JSONArray(reponse.toString());
               // JSONObject jObj = jsonArray.getJSONObject(0);
                b=jObj.getInt("b");
                if(b!=1 && b!=0){
                    String ID=jObj.getString("Id_profil");
                    ID_profil=Long.valueOf(ID);}


            }

            catch (ClientProtocolException e){e.printStackTrace();}
            catch (HttpHostConnectException e){b=10;}
            catch (IOException e){e.printStackTrace();}
            catch (Exception e){e.printStackTrace();}
            return b;
        }

        @Override
        protected void onPostExecute(Integer a) {
            Log.d("msg1", "etape10******************");
            task = null;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            Log.d("msg1", "etape11******************");
            switch (a) {

                case 0:
                    Log.d("msg1", "etape12******************");

                    //showProgress(false);
                    pseudo.setError("Ce nom d'utilisateur n'existe pas");
                    break;

                case 1:
                    Log.d("msg1", "etape13******************");

                    //showProgress(false);
                    mdp.setError("Mot de passe incorrect");
                    break;

                case 2:
                    Log.d("msg1", "etape14******************");

                    Intent intent2 = new Intent(MainActivity.this, AdminActivity.class);
                    intent2.putExtra("ID_admin", ID_profil);
                    finish();
                    startActivity(intent2);

                    editor.putString("Connecté", "true");
                    editor.putLong("ID_profil", ID_profil);
                    editor.putString("type_profil", "Admin");
                    editor.apply();
                    break;
                case 3:
                    Log.d("msg1", "etape15******************");

                    Toast.makeText(MainActivity.this, " Bienvenue", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, ProfilDonneur.class);
                    intent.putExtra("ID_profil", ID_profil);
                    intent.putExtra("type_profil", "Compte_donneur");
                    finish();
                    startActivity(intent);

                    editor.putString("Connecté", "true");
                    editor.putLong("ID_profil", ID_profil);
                    editor.putString("type_profil", "Compte_donneur");
                    editor.commit();

                    break;
                case 4:
                    Log.d("msg1", "etape16******************");

                    Toast.makeText(MainActivity.this, " Bienvenue", Toast.LENGTH_LONG).show();
                    Intent intent3 = new Intent(MainActivity.this, ProfilReceveur.class);
                    intent3.putExtra("ID_profil", ID_profil);
                    intent3.putExtra("type_profil", "Compte_receveur");
                    finish();
                    startActivity(intent3);

                    editor.putString("Connecté", "true");
                    editor.putLong("ID_profil", ID_profil);
                    editor.putString("type_profil", "Compte_receveur");
                    editor.commit();

                    break;
                case 10:
                    Log.d("msg1", "etape17******************");

                    Alerte(MainActivity.this).show();
                    //showProgress(false);
                    break;
                default:
                    break;

            }
        }


        public AlertDialog.Builder Alerte(Context c) {
            Log.d("msg1", "etape18******************");
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setTitle("Pas de connexion Wifi");
            builder.setMessage("Veuillez vous connectez ");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            Log.d("msg1", "etape19******************");
            return builder;


        }

    }

}
