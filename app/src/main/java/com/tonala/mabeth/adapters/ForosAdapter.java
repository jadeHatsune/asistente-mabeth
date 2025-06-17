package com.tonala.mabeth.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tonala.mabeth.EliminarForoActivity;
import com.tonala.mabeth.R;
import com.tonala.mabeth.entities.Foros;
import com.tonala.mabeth.ui.Foros.RespForoActivity;
import com.tonala.mabeth.ui.MenuSlideActivity;

import java.util.List;

public class ForosAdapter extends RecyclerView.Adapter<ForosAdapter.ForosViewHolder>{
    List<Foros> listaforos;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public int news;
    int prio;

    public ForosAdapter(List<Foros> listaforos){
        this.listaforos = listaforos;
    }

    @NonNull
    @Override
    public ForosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_foros, parent, false);
        return new ForosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForosViewHolder holder, int position) {
        holder.viewCreador.setText("Creado por: " + listaforos.get(position).getCreador());
        holder.viewCuerpo.setText(listaforos.get(position).getCuerpo());
        holder.viewTitulo.setText(listaforos.get(position).getTitulo());
        holder.viewTema.setText(listaforos.get(position).getTema());
        holder.viewLike.setAnimation(R.raw.likeforos);
        holder.viewLike.setScale(200);
        holder.viewLike.setSpeed(2);
        holder.viewLike.setMinAndMaxFrame(0, 99);

        getInf(holder);

        ObtenerNumLikes(holder, listaforos.get(position).getIDForo());
        ObtenerNumRespuestas(holder, listaforos.get(position).getIDForo());

        String URL = listaforos.get(position).getURLimagen();
        if(!URL.equals("null")){
            Picasso.get()
                    .load(URL)
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.viewImagen);
        }else{
            holder.viewImagen.setVisibility(View.GONE);
        }

        verificarLike(holder, listaforos.get(position).getIDForo());

        holder.viewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.viewLike.isAnimating()){
                    if(holder.viewLike.getFrame() == 99){
                        decrementarLikes(holder, listaforos.get(holder.getAdapterPosition()).getIDForo());
                        holder.viewLike.cancelAnimation();
                        holder.viewLike.setFrame(0);
                    }else{
                        incrementarLikes(holder, listaforos.get(holder.getAdapterPosition()).getIDForo());
                        holder.viewLike.playAnimation();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return  listaforos.size();
    }

    public class ForosViewHolder extends RecyclerView.ViewHolder{

        TextView viewCreador, viewCuerpo, viewnumLikes, viewRespuestas, viewNumRespuestas, viewTema, viewTitulo, viewEliminar;
        ImageView viewImagen;
        LottieAnimationView viewLike;
        public ForosViewHolder(@NonNull View itemView) {
            super(itemView);
            viewCreador = itemView.findViewById(R.id.CreadorForo);
            viewCuerpo = itemView.findViewById(R.id.CuerpoForo);
            viewImagen = itemView.findViewById(R.id.ImagenForo);
            viewLike = itemView.findViewById(R.id.animLikeForo);
            viewEliminar = itemView.findViewById(R.id.btnEliminarForo);
            viewnumLikes = itemView.findViewById(R.id.lblNumLikeForo);
            viewRespuestas = itemView.findViewById(R.id.RespuestasForo);
            viewNumRespuestas = itemView.findViewById(R.id.NumRespuestasForo);
            viewTema = itemView.findViewById(R.id.TemaForo);
            viewTitulo = itemView.findViewById(R.id.TituloForo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RespForoActivity.class);
                    intent.putExtra( "ID", listaforos.get(getAdapterPosition()).getIDForo());
                    context.startActivity(intent);
                }
            });
            viewRespuestas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RespForoActivity.class);
                    intent.putExtra( "ID", listaforos.get(getAdapterPosition()).getIDForo());
                    context.startActivity(intent);
                }
            });

            viewEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EliminarForoActivity.class);
                    intent.putExtra( "ID", listaforos.get(getAdapterPosition()).getIDForo());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void getInf(@NonNull ForosViewHolder holder){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String prioridad = dataSnapshot.child("Prioridad").getValue().toString();
                    prio = Integer.parseInt(prioridad);
                    if(prio == 1){
                        holder.viewEliminar.setVisibility(View.VISIBLE);
                    }else{
                        holder.viewEliminar.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ObtenerNumLikes(@NonNull ForosViewHolder holder, String idForo){
        mDatabase.child("Foros").child(idForo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   String lik = dataSnapshot.child("likes").getValue().toString();
                   holder.viewnumLikes.setText(lik);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void ObtenerNumRespuestas(@NonNull ForosViewHolder holder, String idForo){
        mDatabase.child("Foros").child(idForo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("respuestas").getValue().toString();
                holder.viewNumRespuestas.setText(lik);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void incrementarLikes(@NonNull ForosViewHolder holder, String idForo){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(idForo).child(id).setValue(1);
        mDatabase.child("Foros").child(idForo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("likes").getValue().toString();
                news = Integer.parseInt(lik) + 1;
                mDatabase.child("Foros").child(idForo).child("likes").setValue(String.valueOf(news));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        ObtenerNumLikes(holder, idForo);
    }

    public void decrementarLikes(@NonNull ForosViewHolder holder, String idForo){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(idForo).child(id).removeValue();
        mDatabase.child("Foros").child(idForo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("likes").getValue().toString();
                news = Integer.parseInt(lik) - 1;
                mDatabase.child("Foros").child(idForo).child("likes").setValue(String.valueOf(news));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        ObtenerNumLikes(holder, idForo);
    }

    public void verificarLike(@NonNull ForosViewHolder holder,String idForo){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(idForo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if(dataSnapshot.child(id).exists()){
                        holder.viewLike.setFrame(99);
                    }else{
                        holder.viewLike.setFrame(0);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
