package com.peluffo.eltemplodemomo.ui.juego;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.databinding.FragmentJuegoDetalleBinding;
import com.peluffo.eltemplodemomo.modelo.Juego;

public class JuegoDetalleFragment extends Fragment {
    private JuegoDetalleViewModel detalleViewModel;
    private FragmentJuegoDetalleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        detalleViewModel = new ViewModelProvider(this).get(JuegoDetalleViewModel.class);
        binding = FragmentJuegoDetalleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        final TextView tvTitulo = binding.tvTituloJD;
        final TextView tvCredor = binding.tvCreadorJD;
        final TextView tvPrecio = binding.tvPrecioJD;
        final TextView tvDescripcion = binding.tvDescripJD;
        final TextView tvRequisitos = binding.tvReqJD;
        final ImageView ivPortada = binding.ivPortadaJD;
        detalleViewModel.getJuegoM().observe(getViewLifecycleOwner(), new Observer<Juego>() {
            @Override
            public void onChanged(Juego juego) {
                tvTitulo.setText(juego.getTitulo());
                tvCredor.setText(juego.getCreador().getNickName());
                tvPrecio.setText(String.valueOf(juego.getPrecio()));
                tvDescripcion.setText(juego.getDescripcion());
                tvRequisitos.setText(juego.getRequisitos());
                Glide.with(view.getContext())
                        .load("http://192.168.1.105:5001"+juego.getPortada())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivPortada);
            }
        });
        detalleViewModel.cargar(getArguments());
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}