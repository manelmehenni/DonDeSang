package com.example.dondesang.donneur_receveur;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.dondesang.ApercuAnnonceActivity;
import com.example.dondesang.Notification;
import com.example.dondesang.R;

public class Notificationdonneur extends AppCompatActivity {
    List<Notification> notifs = new ArrayList<>();
    long ID_don;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Intent intent =getIntent();
        ID_don=intent.getLongExtra("ID_donneur_receveur",0);
    }

    public class NotifTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            Integer b = 1;
            StringBuilder reponse = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://10.0.2.2/DonDeSang/ChargerNotif.php");
            final List<NameValuePair> infos=new ArrayList<>(5);
            try {
                infos.add(new BasicNameValuePair("ID_user",String.valueOf(ID_don)));
                infos.add(new BasicNameValuePair("Type_profil","donneur_receveur"));
                post.setEntity(new UrlEncodedFormEntity(infos));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();//récupérer corps de la reponse
                InputStream content = entity.getContent();//lire (récuperer des octet)le contenu car par car et former une chain//inputstreamreader on reconstruit les car et bufferReader la ligne
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    reponse.append(line);
                }
                /* Résultats de la requête */
                JSONArray jsonArray = new JSONArray(reponse.toString());
                JSONObject j = jsonArray.getJSONObject(0);
                Log.i("********","********"+j.getBoolean("vide")+ID_don);
                if (j.getBoolean("vide")) {
                    b = 2;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        Notification notif = new Notification();
                        notif.setContenue(jObj.getString("Contenu"));
                        notif.setType(jObj.getInt("Type"));
                        notif.setID_cible(Long.valueOf(jObj.getString("ID_cible")));
                        notif.setID( Long.valueOf(jObj.getString("ID")));
                        notifs.add(0, notif);
                    }
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (HttpHostConnectException e) {
                b = 10;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return b;
        }


        @Override
        protected void onPostExecute(Integer a) {
            RecyclerView recycler = findViewById(R.id.notifications);
            NotifAdapter adapter = new NotifAdapter();
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(Notificationdonneur.this));
            if (a == 10) Alerte(Notificationdonneur.this).show();

        }

        public AlertDialog.Builder Alerte(Context c) {

            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setTitle("Pas de connexion Wifi");
            builder.setMessage("Veuillez vous connectez ");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Notificationdonneur.this.onRestart();
                }
            });

            return builder;
        }
    }
    public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {
        @Override
        //Retoune le nombre total de cellules
        public int getItemCount() {
            return notifs.size();
        }


        @Override
        // Parent pour créer la vue et viewType dans le cas ou les cellules sont de type différent
        public NotifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.notif_item, parent, false);
            return new NotifViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        //Applique une donnée à une vue
        public void onBindViewHolder(final NotifViewHolder holder, int position) {
            //Récupère la donnée
            holder.notif = notifs.get(position);
            //On la donne au holder pour qu'il l'affiche
            holder.display(holder.notif);
            if (holder.notif.getType() == 3) holder.mView.setBackgroundColor(Color.RED);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (holder.notif.getType()) {
                        case 1:
                            Intent intent = new Intent(Notificationdonneur.this, ApercuAnnonceActivity.class);
                            intent.putExtra("ID_Annonce", holder.notif.getID_cible());
                            intent.putExtra("typeProfil", "Compte_donneur_receveur");
                            intent.putExtra("ID", ID_don);
                            Notificationdonneur.this.startActivity(intent);
                            break;


                    }
                }
            });
            holder.supp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SuppNotifeTask refuserDemande = new SuppNotifeTask(holder.notif.getID(), holder.getAdapterPosition());
                    refuserDemande.execute((Void) null);
                }
            });
        }

        public class NotifViewHolder extends RecyclerView.ViewHolder {

            private final View mView;
            private final TextView contenue;
            private final ImageButton supp;
            private Notification notif;

            NotifViewHolder(final View itemView) {
                super(itemView);
                mView = itemView;
                contenue = (itemView.findViewById(R.id.contenuNotif));
                supp = (itemView.findViewById(R.id.supprimer_notif));
            }

            public void display(Notification notification) {
                contenue.setText(Html.fromHtml("" + notification.getContenue()));
            }
        }

        @SuppressLint("StaticFieldLeak")
        public class SuppNotifeTask extends AsyncTask<Void, Void, Integer> {
            private long Id;
            private int Position;

            SuppNotifeTask(long id, int position) {
                Id = id;
                Position = position;
            }

            @Override
            protected Integer doInBackground(Void... params) {
                final HttpClient client = new DefaultHttpClient();
                final HttpPost post = new HttpPost("http://10.0.0.2/DonDeSang/SuppNotif.php");
                final List<NameValuePair> infos = new ArrayList<>(5);
                try {
                    infos.add(new BasicNameValuePair("ID", String.valueOf(Id)));
                    post.setEntity(new UrlEncodedFormEntity(infos));
                    client.execute(post);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Position;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                notifs.remove(Position);
                notifyItemRemoved(Position);

            }
        }
    }
}
