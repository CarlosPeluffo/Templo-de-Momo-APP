package com.peluffo.eltemplodemomo.ui.noticia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peluffo.eltemplodemomo.databinding.FragmentNoticiaBinding;

public class NoticiaFragment extends Fragment {
    private NoticiaAdapter adapter;
    private RecyclerView rvNoticia;
    private FragmentNoticiaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NoticiaViewModel noticiaViewModel = new ViewModelProvider(this).get(NoticiaViewModel.class);
        binding = FragmentNoticiaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvNoticia = binding.rvNoticias;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        rvNoticia.setLayoutManager(gridLayoutManager);
        noticiaViewModel.getListaM().observe(getViewLifecycleOwner(), noticias -> {
            adapter = new NoticiaAdapter(noticias, inflater);
            rvNoticia.setAdapter(adapter);
        });
        assert getArguments() != null;
        noticiaViewModel.cargarNoticias(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}