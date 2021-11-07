package com.peluffo.eltemplodemomo.ui.comentario;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peluffo.eltemplodemomo.databinding.FragmentComentarioBinding;
import com.peluffo.eltemplodemomo.modelo.Comentario;

import java.util.List;

public class ComentarioFragment extends Fragment {
    private RecyclerView rvComentario;
    private ComentarioAdapter adapter;
    private ComentarioViewModel comentarioViewModel;
    private FragmentComentarioBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        comentarioViewModel = new ViewModelProvider(this).get(ComentarioViewModel.class);
        binding = FragmentComentarioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        rvComentario = binding.rvComentarios;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        rvComentario.setLayoutManager(gridLayoutManager);

        comentarioViewModel.getComentarioM().observe(getViewLifecycleOwner(), new Observer<List<Comentario>>() {
            @Override
            public void onChanged(List<Comentario> comentarios) {
                adapter = new ComentarioAdapter(comentarios, view.getContext(), inflater);
                rvComentario.setAdapter(adapter);
            }
        });

        comentarioViewModel.cargarComentarios(getArguments());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}