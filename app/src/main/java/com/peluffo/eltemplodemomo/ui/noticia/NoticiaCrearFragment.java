package com.peluffo.eltemplodemomo.ui.noticia;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.peluffo.eltemplodemomo.MainActivity;
import com.peluffo.eltemplodemomo.databinding.FragmentNoticiaCrearBinding;
import com.peluffo.eltemplodemomo.modelo.Noticia;

public class NoticiaCrearFragment extends Fragment {
    private NoticiaCrearViewModel vmNoticiaCrear;
    private FragmentNoticiaCrearBinding binding;
    private Noticia noticia;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vmNoticiaCrear = new ViewModelProvider(this).get(NoticiaCrearViewModel.class);
        binding = FragmentNoticiaCrearBinding.inflate(inflater, container, false);
        noticia = new Noticia();
        View root = binding.getRoot();
        final EditText etTitulo = binding.etTituloNotCrear;
        final EditText etCuerpo = binding.etCuerpoNotCrear;
        final Button btCrear = binding.btCrearNoticia;
        vmNoticiaCrear.getNoticiaM().observe(getViewLifecycleOwner(), new Observer<Noticia>() {
            @Override
            public void onChanged(Noticia noticia) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticia.setId(0);
                noticia.setCreadorId(0);
                noticia.setTitulo(etTitulo.getText().toString());
                noticia.setCuerpo(etCuerpo.getText().toString());
                noticia.setFecha("1994-05-12");
                vmNoticiaCrear.crearNoticia(getArguments(), noticia);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}