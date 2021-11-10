package com.peluffo.eltemplodemomo.ui.juego;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peluffo.eltemplodemomo.databinding.FragmentJuegoBinding;

public class JuegoFragment extends Fragment {
    private RecyclerView rvJuego;
    private JuegoAdapter juegoAdapter;
    private FragmentJuegoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        JuegoViewModel juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        binding = FragmentJuegoBinding.inflate(inflater, container,false);
        View root = binding.getRoot();
        rvJuego = binding.rvJuegos;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvJuego.setLayoutManager(gridLayoutManager);
        juegoViewModel.getLista().observe(getViewLifecycleOwner(), juegos -> {
            juegoAdapter = new JuegoAdapter(juegos, root.getContext(), inflater);
            rvJuego.setAdapter(juegoAdapter);
        });
        juegoViewModel.cargarJuegos();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}