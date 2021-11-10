package com.peluffo.eltemplodemomo.ui.noticia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.util.Convertidor;
import com.peluffo.eltemplodemomo.modelo.Noticia;

import java.util.List;

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.ViewHolder> {
    private final List<Noticia> lista;
    private final LayoutInflater layoutInflater;

    public NoticiaAdapter(List<Noticia> lista, LayoutInflater layoutInflater) {
        this.lista = lista;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_noticia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Noticia noticia = lista.get(position);
        holder.titulo.setText(noticia.getTitulo());
        holder.fecha.setText(Convertidor.fecha(noticia.getFecha()));
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("noticia", noticia );
            Navigation.findNavController(view).navigate(R.id.nav_noticiaDetalle, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView fecha, titulo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.tvFechaCN);
            titulo = itemView.findViewById(R.id.tvTituloCN);
        }
    }
}
