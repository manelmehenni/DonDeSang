package com.example.dondesang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfirmerSignalement extends AppCompatActivity {
    Button signaler, annuler;
    private String smotif;
    private long ID_profil;
    private String Type_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmer_signalement);

        Intent intent=getIntent();
        ID_profil=intent.getLongExtra("ID_profil",0);
        Type_profil=intent.getStringExtra("Type_profil");

        signaler= (Button) findViewById(R.id.confirmer_signal_compte);
        annuler= (Button) findViewById(R.id.non_signal_compte);

        final RadioGroup motif= findViewById(R.id.motif_groupe_profil);
        final RadioButton m1=findViewById(R.id.b_1);
        final RadioButton m2=findViewById(R.id.b_2);

        signaler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (motif.getCheckedRadioButtonId()){
                    case R.id.b_1:
                        smotif=m1.getText().toString();
                        break;
                    case R.id.b_2:
                        smotif=m2.getText().toString();
                        break;
                }
                SignalerTask signalerTask=new SignalerTask();
                signalerTask.execute((Void)null);
            }

        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    public class SignalerTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {
            Integer b = 1;
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://192.168.1.107/DonDeSang/signalerCompte.php");
            try {
                List<NameValuePair> infos = new ArrayList<>(5);
                infos.add(new BasicNameValuePair("ID_profil", String.valueOf(ID_profil)));
                infos.add(new BasicNameValuePair("Type_profil", Type_profil));
                infos.add(new BasicNameValuePair("Motif", smotif));
                post.setEntity(new UrlEncodedFormEntity(infos));
                client.execute(post);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return b;
        }
        @Override
        protected void onPostExecute(Integer integer) {
            finish();
        }
    }
}
