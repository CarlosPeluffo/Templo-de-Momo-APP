package com.peluffo.eltemplodemomo.ui.comentario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.modelo.Comentario;
import com.peluffo.eltemplodemomo.request.ApiClient;
import com.peluffo.eltemplodemomo.util.Convertidor;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {
    private final List<Comentario> lista;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public ComentarioAdapter(List<Comentario> lista, Context context, LayoutInflater layoutInflater) {
        this.lista = lista;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_comentario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comentario c = lista.get(position);
        holder.tvFecha.setText(Convertidor.fechaYHora(c.getFecha()));
        holder.tvUsuario.setText(c.getUsuario().getNickName());
        holder.tvCuerpo.setText(c.getCuerpo());
        Glide.with(context)
                .load(ApiClient.imageURL() + c.getUsuario().getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivUserAvatar);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsuario, tvCuerpo, tvFecha;
        private final ImageView ivUserAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsuario = itemView.findViewById(R.id.tvUsuarioComent);
            tvCuerpo = itemView.findViewById(R.id.tvCuerpoComent);
            tvFecha = itemView.findViewById(R.id.tvFechaComent);
            ivUserAvatar = itemView.findViewById(R.id.ivComentario);
        }
    }
}
