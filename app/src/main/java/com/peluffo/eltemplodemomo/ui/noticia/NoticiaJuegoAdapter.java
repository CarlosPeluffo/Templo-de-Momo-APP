package com.peluffo.eltemplodemomo.ui.noticia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.modelo.Juego;
import com.peluffo.eltemplodemomo.ui.juego.JuegoAdapter;

import java.util.List;

public class NoticiaJuegoAdapter extends RecyclerView.Adapter<NoticiaJuegoAdapter.ViewHolder> {
    private List<Juego> lista;
    private Context context;
    private LayoutInflater layoutInflater;

    public NoticiaJuegoAdapter(List<Juego> lista, Context context, LayoutInflater layoutInflater) {
        this.lista = lista;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public NoticiaJuegoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_juego, parent, false);
        return new NoticiaJuegoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiaJuegoAdapter.ViewHolder holder, int position) {
        Juego juego = lista.get(position);
        holder.tvTitulo.setText(juego.getTitulo());
        Glide.with(context)
                .load("http://192.168.1.105:5001"+juego.getPortada())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivPortada);
        holder.btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("juego", juego.getId());
                Navigation.findNavController(view).navigate(R.id.nav_noticiaCrear, bundle);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("juego", juego.getId());
                Navigation.findNavController(view).navigate(R.id.nav_noticia, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPortada;
        private TextView tvTitulo;
        private Button btCrear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPortada = itemView.findViewById(R.id.ivJuegoCard);
            tvTitulo = itemView.findViewById(R.id.tvTituloCJ);
            btCrear = itemView.findViewById(R.id.bttCrearNotC);
        }
    }
}