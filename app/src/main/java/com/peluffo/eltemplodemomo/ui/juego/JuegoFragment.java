package com.peluffo.eltemplodemomo.ui.juego;

import androidx.lifecycle.Observer;
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
import com.peluffo.eltemplodemomo.modelo.Juego;

import java.util.List;

public class JuegoFragment extends Fragment {
    private RecyclerView rvJuego;
    private JuegoAdapter juegoAdapter;
    private JuegoViewModel juegoViewModel;
    private FragmentJuegoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        juegoViewModel = new ViewModelProvider(this).get(JuegoViewModel.class);
        binding = FragmentJuegoBinding.inflate(inflater, container,false);
        View root = binding.getRoot();
        rvJuego = binding.rvJuegos;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvJuego.setLayoutManager(gridLayoutManager);
        juegoViewModel.getLista().observe(getViewLifecycleOwner(), new Observer<List<Juego>>() {
            @Override
            public void onChanged(List<Juego> juegos) {
                juegoAdapter = new JuegoAdapter(juegos, root.getContext(), inflater);
                rvJuego.setAdapter(juegoAdapter);
            }
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