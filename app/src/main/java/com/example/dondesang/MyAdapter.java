package com.example.dondesang;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    //L'adapteur s'occupe de l'ensemble du contenu
    //ViewHolder s'occuupe des spécificités d'une cellule
    private List<Annonce> annoncess;
    private Context mcontext;
    private long Id ;
    private String typeProfil;


   /* MyAdapter(List<Annonce> a,Context mc){
        annoncess=a;
        mcontext=mc;
    }*/

    MyAdapter(List<Annonce> a,Context mc,String type,long id){
        annoncess=a;
        mcontext=mc;
        typeProfil=type;
        Id=id;
    }


    @Override
    //Retoune le nombre total de cellules
    public int getItemCount() {
        return annoncess.size();
    }


    @Override
    // Parent pour créer la vue et viewType dans le cas ou les cellules sont de type différent
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    //Applique une donnée à une vue
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //Récupère la donnée
        holder.annonce = annoncess.get(position);
        //On la donne au holder pour qu'il l'affiche
        holder.display(holder.annonce);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ApercuAnnonceActivity.class);
                intent.putExtra("ID_Annonce",holder.annonce.getID());
                intent.putExtra("typeProfil",typeProfil);
                intent.putExtra("ID",Id);
                mcontext.startActivity(intent);
            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private final TextView nom;
        private final TextView prenom;
        private final TextView gp;
        private final TextView adresse;
        private final TextView ntel;
        private final TextView email;
        private Annonce annonce;

        //On récupère les TextView dans le constructeur pour être sûr de le faire qu'une seule fois et pas à chaque fois que l'on veut
        //afficher une donnée car l'opération est lourde
        public MyViewHolder(final View itemView) {
            super(itemView);
            mView=itemView;
            nom = (itemView.findViewById(R.id.NOM));
            prenom = (itemView.findViewById(R.id.PRENOM));
            adresse = (itemView.findViewById(R.id.ADRESSE));
            gp= (itemView.findViewById(R.id.GP));
            ntel = ( itemView.findViewById(R.id.NTEL));
            email = ( itemView.findViewById(R.id.EMAIL));
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void display(Annonce annonce) {
            nom.setText(annonce.getNom());
            prenom.setText(annonce.getPrenom());
            adresse.setText(annonce.getAdresse());
            gp.setText(annonce.getGp());
            ntel.setText(annonce.getNtel());
            email.setText(annonce.getEmail());


        }
    }
}
