package com.peluffo.eltemplodemomo.ui.juego;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import com.peluffo.eltemplodemomo.databinding.FragmentJuegoDetalleBinding;
import com.peluffo.eltemplodemomo.modelo.Juego;
import com.peluffo.eltemplodemomo.request.ApiClient;

public class JuegoDetalleFragment extends Fragment {
    private FragmentJuegoDetalleBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        JuegoDetalleViewModel detalleViewModel = new ViewModelProvider(this).get(JuegoDetalleViewModel.class);
        binding = FragmentJuegoDetalleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        final TextView tvTitulo = binding.tvTituloJD;
        final TextView tvCredor = binding.tvCreadorJD;
        final TextView tvPrecio = binding.tvPrecioJD;
        final TextView tvDescripcion = binding.tvDescripJD;
        final TextView tvRequisitos = binding.tvReqJD;
        final ImageView ivPortada = binding.ivPortadaJD;
        detalleViewModel.getJuegoM().observe(getViewLifecycleOwner(), juego -> {
            tvTitulo.setText(juego.getTitulo());
            tvCredor.setText(juego.getCreador().getNickName());
            tvPrecio.setText("$ " + juego.getPrecio());
            tvDescripcion.setText(juego.getDescripcion());
            tvRequisitos.setText(juego.getRequisitos());
            Glide.with(view.getContext())
                    .load(ApiClient.imageURL() + juego.getPortada())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivPortada);
        });
        assert getArguments() != null;
        detalleViewModel.cargar(getArguments());
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}