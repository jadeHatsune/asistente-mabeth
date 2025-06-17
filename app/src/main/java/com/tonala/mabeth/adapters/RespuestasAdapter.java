package com.tonala.mabeth.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tonala.mabeth.R;
import com.tonala.mabeth.entities.Respuestas;

import java.util.List;

public class RespuestasAdapter extends RecyclerView.Adapter<RespuestasAdapter.RespuestasViewHolder>{
    List<Respuestas> listarespuestas;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public int news;

    public RespuestasAdapter(List<Respuestas> listarespuestas){
        this.listarespuestas = listarespuestas;
    }

    @NonNull
    @Override
    public RespuestasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_respuestas, parent, false);
        return new RespuestasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RespuestasAdapter.RespuestasViewHolder holder, int position) {
        holder.viewCreador.setText("Respuesta de: " + listarespuestas.get(position).getCreador());
        holder.viewCuerpo.setText(listarespuestas.get(position).getRespuesta());
        holder.viewLike.setAnimation(R.raw.likeforos);
        holder.viewLike.setScale(200);
        holder.viewLike.setSpeed(2);
        holder.viewLike.setMinAndMaxFrame(0, 99);
        ObtenerNumLikes(holder, listarespuestas.get(position).getIDRespuesta());
        verificarLike(holder, listarespuestas.get(position).getIDRespuesta());

        holder.viewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.viewLike.isAnimating()){
                    if(holder.viewLike.getFrame() == 99){
                        decrementarLikes(holder, listarespuestas.get(holder.getAdapterPosition()).getIDRespuesta());
                        holder.viewLike.cancelAnimation();
                        holder.viewLike.setFrame(0);
                    }else{
                        incrementarLikes(holder, listarespuestas.get(holder.getAdapterPosition()).getIDRespuesta());
                        holder.viewLike.playAnimation();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listarespuestas.size();
    }

    public class RespuestasViewHolder extends RecyclerView.ViewHolder{
        TextView viewCreador, viewCuerpo, viewNumLikes;
        LottieAnimationView viewLike;
        public RespuestasViewHolder(@NonNull View itemView) {
            super(itemView);
            viewCreador = itemView.findViewById(R.id.CreadorRespuesta);
            viewCuerpo = itemView.findViewById(R.id.CuerpoRespuesta);
            viewNumLikes = itemView.findViewById(R.id.lblNumLikeRespuesta);
            viewLike = itemView.findViewById(R.id.animLikeRespuesta);
        }
    }

    public void ObtenerNumLikes(@NonNull RespuestasAdapter.RespuestasViewHolder holder, String idRespuesta){
        mDatabase.child("Respuestas").child(idRespuesta).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("likes").getValue().toString();
                holder.viewNumLikes.setText(lik);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void incrementarLikes(@NonNull RespuestasAdapter.RespuestasViewHolder holder, String idRespuesta){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(idRespuesta).child(id).setValue(1);
        mDatabase.child("Respuestas").child(idRespuesta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("likes").getValue().toString();
                news = Integer.parseInt(lik) + 1;
                mDatabase.child("Respuestas").child(idRespuesta).child("likes").setValue(String.valueOf(news));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        ObtenerNumLikes(holder, idRespuesta);
    }

    public void decrementarLikes(@NonNull RespuestasAdapter.RespuestasViewHolder holder, String idRespuesta){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(idRespuesta).child(id).removeValue();
        mDatabase.child("Respuestas").child(idRespuesta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lik = dataSnapshot.child("likes").getValue().toString();
                news = Integer.parseInt(lik) - 1;
                mDatabase.child("Respuestas").child(idRespuesta).child("likes").setValue(String.valueOf(news));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        ObtenerNumLikes(holder, idRespuesta);
    }

    public void verificarLike(@NonNull RespuestasAdapter.RespuestasViewHolder holder,String idRespuesta){
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Likes").child(idRespuesta).addListenerForSingleValueEvent(new ValueEventListener() {
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
