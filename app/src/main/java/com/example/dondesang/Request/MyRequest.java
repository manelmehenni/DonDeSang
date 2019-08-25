package com.example.dondesang.Request;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dondesang.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class MyRequest {
    private Context context;
    private RequestQueue queue;
    private Button btn_suivante, btn_confirmer;
    private EditText nom, prenom, datenaiss, taille, poids, adresse, groupesanguin, nomutilisateur, email, ntel, mdp, cmdp;
    private RadioGroup sexeG;
    private MyRequest request;



    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }
    //Gérer l'inscription
    public void register(final String nom,final String prenom, final String datenaiss, final String taille,final String poids,final String adresse,final String sexeG, final String groupesanguin, final String nomUtilisateur, final String email, final String ntel,final String mdp1, final String mdp2, final RegisterCallBack callBack) {

        String url = "http://10.0.2.2/DonDeSang/inscription.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, String> errors = new HashMap<>();
                try {

                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error) {
                        //l'inscription s'est bien déroulée
                        callBack.onSuccess("Vous êtes bien inscrit");
                    } else {
                        //erreurs lors de l'inscription
                        JSONObject messages = json.getJSONObject("message");
                        if (messages.has("pseudo")) {
                            errors.put("pseudo", messages.getString("pseudo"));
                        }
                        if (messages.has("email")) {
                            errors.put("email", messages.getString("email"));
                        }
                        if (messages.has("mdp1")) {
                            errors.put("mdp1", messages.getString("mdp1"));
                        }
                        callBack.inputErrors(errors);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Exp pas de réseau
                if (error instanceof NetworkError) {
                    callBack.onError("Impossible de se connecter");
                }
                //Erreur de volley
                else if (error instanceof VolleyError)
                    callBack.onError("Une erreur s'est produite");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("snom", nom);
                map.put("sprenom",prenom);
                map.put("sdn", datenaiss);
                map.put("sadresse", adresse);
                map.put("staille", taille);
                map.put("spoids", poids);
                map.put("gp", groupesanguin);
                map.put("ssexe", sexeG);
                map.put("spseudo", nomUtilisateur);
                map.put("mail", email);
                map.put("snum_tel", ntel);
                map.put("smdp", mdp1);
                map.put("smdp1", mdp2);
                return map;
            }

        };

        //queue.add(request);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface RegisterCallBack {
        void onSuccess(String message);

        void inputErrors(Map<String, String> errors);

        void onError(String message);
    }




    //Gérer la connexion
    public void connection(final String pseudo, final String mdp, final LoginCallBack callBack) {

        String url1 = "https://10.0.2.2/DonDeSang/connexion.php";

        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json= new JSONObject(response);
                    Boolean error= json.getBoolean("error");

                    if(!error){
                        //S'il n'y a pas d'erreur
                        String id= json.getString("id");
                        String pseudo= json.getString("pseudo");
                        //USER user= new USER(id, pseudo);
                        callBack.OnSuccess(id, pseudo);

                    }else{
                        callBack.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    callBack.onError("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Exp pas de réseau
                if (error instanceof NetworkError) {
                    callBack.onError("Impossible de se connecter");
                }

                else if(error instanceof  ParseError){
                    callBack.onError("Parse error");
                }
                else if(error instanceof ServerError){
                    callBack.onError("Server error");
                }
                else if(error instanceof AuthFailureError){
                    callBack.onError("AuthFailureError");
                }
                else if(error instanceof NoConnectionError){
                    callBack.onError("NoConnection error");
                }
                else if(error instanceof TimeoutError){
                    Toast.makeText(context, "TimeOutError",Toast.LENGTH_LONG ).show();
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                //map.put("btn_login", "true");
                map.put("pseudo", pseudo);
                map.put("mdp", mdp);


                return map;
            }

        };

        //queue.add(request);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface LoginCallBack {
        void OnSuccess(String id, String pseudo);
        void onError(String message);
    }




    //Gérer la modification
    public void upadate(final String nom_modifié,final String prenom_modifié, final String dn_modifié,final String adresse_modifiée, final String taille_modifiée,final String poids_modifié,final String sexeG_modifié, final String groupesanguin_modifié, final String nomUtilisateur_modifié, final String email_modifié, final String ntel_modifié,final String mdp1_modifié, final String mdp2_modifié, final updateCallBack callback){

        String url = "http://10.0.2.2/DonDeSang/ModifProfil.php";


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, String> errors = new HashMap<>();
                try {

                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    if (!error) {
                        //la modification s'est bien déroulée
                        callback.onSuccess("Les informations ont bien été modifiées");
                    } else {
                        //erreurs lors de la modification
                        JSONObject messages = json.getJSONObject("message");
                        if (messages.has("pseudo")) {
                            errors.put("pseudo", messages.getString("pseudo"));
                        }
                        if (messages.has("email")) {
                            errors.put("email", messages.getString("email"));
                        }
                        if (messages.has("mdp1")) {
                            errors.put("mdp1", messages.getString("mdp1"));
                        }
                        callback.inputErrors(errors);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // pas de réseau
                if (error instanceof NetworkError) {
                    callback.onError("Impossible de se connecter");
                }
                //Erreur de volley
                else if (error instanceof VolleyError)
                    callback.onError("Une erreur s'est produite");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("nom", nom_modifié);
                map.put("prenom",prenom_modifié);
                map.put("datenaiss", dn_modifié);
                map.put("adresse", adresse_modifiée);
                map.put("taille", taille_modifiée);
                map.put("poids", poids_modifié);
                map.put("groupesanguin", groupesanguin_modifié);
                map.put("sexe", sexeG_modifié);
                map.put("nomUtilisateur", nomUtilisateur_modifié);
                map.put("email", email_modifié);
                map.put("ntel", ntel_modifié);
                map.put("mdp1", mdp1_modifié);
                map.put("mdp2", mdp2_modifié);
                return map;


            }
        };
    }

    public interface updateCallBack{
        void onSuccess(String message);

        void inputErrors(Map<String, String> errors);

        void onError(String message);
    }
}

