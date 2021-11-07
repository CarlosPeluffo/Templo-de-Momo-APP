package com.peluffo.eltemplodemomo.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.databinding.FragmentPerfilBinding;
import com.peluffo.eltemplodemomo.modelo.Creador;

public class PerfilFragment extends Fragment {
    private Button btEditar;
    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPerfil;
        final EditText etNickName = binding.etNickName;
        final EditText etNombre = binding.etNombre;
        final EditText etApellido = binding.etApellido;
        final EditText etEmail = binding.etEMail;
        final EditText etContra = binding.etContra;
        final ImageView ivAvatar = binding.ivAvatar;
        final TextView tvAvatar = binding.tvAvatar;

        editar();
        perfilViewModel.getmText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
        perfilViewModel.getEstadoM().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                etNickName.setEnabled(aBoolean);
                etNombre.setEnabled(aBoolean);
                etApellido.setEnabled(aBoolean);
            }
        });
        perfilViewModel.getCreadorM().observe(getViewLifecycleOwner(), new Observer<Creador>() {
            @SuppressLint("ResourceType")
            @Override
            public void onChanged(Creador creador) {

                etNickName.setText(creador.getNickName());
                etNombre.setText(creador.getNombre());
                etApellido.setText(creador.getApellido());
                etEmail.setText(creador.getMail());
                etContra.setText(creador.getPassword());
                Glide.with(root.getContext())
                        .load("http://192.168.1.105:5001"+creador.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivAvatar);
                tvAvatar.setText(creador.getAvatar() + "");
            }
        });
        perfilViewModel.getTextB().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                btEditar.setText(s);
            }
        });
        perfilViewModel.cargarCreador();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void editar(){
        btEditar = binding.btEditar;
        final EditText etNickName = binding.etNickName;
        final EditText etNombre = binding.etNombre;
        final EditText etApellido = binding.etApellido;
        final EditText etEmail = binding.etEMail;
        final EditText etContra = binding.etContra;
        final TextView tvAvatar = binding.tvAvatar;
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = ((Button)view).getText().toString();
                Creador c = new Creador(
                        etNickName.getText().toString(),
                        etEmail.getText().toString(),
                        etContra.getText().toString(),
                        etNombre.getText().toString(),
                        etApellido.getText().toString(),
                        tvAvatar.getText().toString()

                );
                perfilViewModel.cambiarEstado(texto, c);
            }
        });
    }
}