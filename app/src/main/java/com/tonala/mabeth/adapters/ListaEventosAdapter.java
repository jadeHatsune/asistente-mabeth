package com.tonala.mabeth.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tonala.mabeth.R;
import com.tonala.mabeth.entities.Eventos;
import com.tonala.mabeth.ui.Gestor.EditarEventoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ListaEventosAdapter extends RecyclerView.Adapter<ListaEventosAdapter.EventoViewHolder>{

    private List<Eventos> listaEventos;
    private Timer timer;
    private List<Eventos> EventosSource;

    public ListaEventosAdapter(List<Eventos> listaEventos){
        this.listaEventos = listaEventos; EventosSource = listaEventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        holder.viewTitulo.setText(listaEventos.get(position).getTitulo());
        holder.viewCuerpo.setText(listaEventos.get(position).getCuerpo());
        holder.viewFecha.setText(listaEventos.get(position).getFecha());
        holder.viewHora.setText(listaEventos.get(position).getHora());
        holder.viewChecked.setText(listaEventos.get(position).getChecked());
        if (listaEventos.get(position).getColor() != null){
            Drawable background = holder.layouteventos.getBackground();
            if (background instanceof ShapeDrawable) {
                ShapeDrawable shapeDrawable = (ShapeDrawable) background;
                shapeDrawable.getPaint().setColor(Color.parseColor(listaEventos.get(position).getColor()));
            } else if (background instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) background;
                gradientDrawable.setColor(Color.parseColor(listaEventos.get(position).getColor()));
            } else if (background instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) background;
                colorDrawable.setColor(Color.parseColor(listaEventos.get(position).getColor()));
            }
        }else {
            holder.layouteventos.setBackgroundColor(Color.parseColor("#6E727A"));
        }


    }

    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView viewTitulo, viewCuerpo, viewFecha, viewHora, viewChecked;
        LinearLayout layouteventos;

        public EventoViewHolder(@NonNull View itemView){
            super(itemView);
            viewTitulo = itemView.findViewById(R.id.lblTituloListaNoty);
            viewCuerpo = itemView.findViewById(R.id.lblCuerpoListaNoty);
            viewFecha = itemView.findViewById(R.id.lblFechaListaNoty);
            layouteventos = itemView.findViewById(R.id.layouteventos);
            viewHora = itemView.findViewById(R.id.lblHoraListaNoty);
            viewChecked = itemView.findViewById(R.id.lblEstadoListaNoty);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, EditarEventoActivity.class);
                    intent.putExtra( "ID", listaEventos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void searchActividades(final String searchKeyboard){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(searchKeyboard.trim().isEmpty()){
                    listaEventos = EventosSource;
                }else{
                    ArrayList<Eventos> temp = new ArrayList<>();
                    for(Eventos evento : EventosSource){
                        if(evento.getTitulo().toLowerCase().contains(searchKeyboard.toLowerCase()) || evento.getCuerpo().toLowerCase().contains(searchKeyboard.toLowerCase())){
                            temp.add(evento);
                        }
                    }
                    listaEventos = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run(){
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500);
    }

    public void cancelTimer(){
        if(timer != null){
            timer.cancel();
        }
    }
}
