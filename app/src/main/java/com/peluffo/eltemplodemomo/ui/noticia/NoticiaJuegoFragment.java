package com.peluffo.eltemplodemomo.ui.noticia;

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

import com.peluffo.eltemplodemomo.databinding.FragmentNoticiajuegoBinding;

public class NoticiaJuegoFragment extends Fragment {
    private RecyclerView rvNoticiaJuego;
    private NoticiaJuegoAdapter adapter;
    private FragmentNoticiajuegoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        NoticiaJuegoViewModel viewModel = new ViewModelProvider(this).get(NoticiaJuegoViewModel.class);
        binding = FragmentNoticiajuegoBinding.inflate(inflater, container,false);
        View root = binding.getRoot();
        rvNoticiaJuego = binding.rvNoticiaJuego;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvNoticiaJuego.setLayoutManager(gridLayoutManager);
        viewModel.getLista().observe(getViewLifecycleOwner(), juegos -> {
            adapter = new NoticiaJuegoAdapter(juegos, root.getContext(), inflater);
            rvNoticiaJuego.setAdapter(adapter);
        });
        viewModel.cargarJuegos();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}