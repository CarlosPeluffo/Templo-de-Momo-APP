package com.peluffo.eltemplodemomo.ui.perfil;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import com.peluffo.eltemplodemomo.databinding.FragmentPerfilBinding;
import com.peluffo.eltemplodemomo.modelo.Creador;
import com.peluffo.eltemplodemomo.request.ApiClient;

public class PerfilFragment extends Fragment {
    private Button btEditar;
    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText etNickName = binding.etNickName;
        final EditText etNombre = binding.etNombre;
        final EditText etApellido = binding.etApellido;
        final EditText etEmail = binding.etEMail;
        final EditText etContra = binding.etContra;
        final ImageView ivAvatar = binding.ivAvatar;
        final TextView tvAvatar = binding.tvAvatar;

        editar();

        perfilViewModel.getEstadoM().observe(getViewLifecycleOwner(), aBoolean -> {
            etNickName.setEnabled(aBoolean);
            etNombre.setEnabled(aBoolean);
            etApellido.setEnabled(aBoolean);
        });
        perfilViewModel.getCreadorM().observe(getViewLifecycleOwner(), creador -> {
            etNickName.setText(creador.getNickName());
            etNombre.setText(creador.getNombre());
            etApellido.setText(creador.getApellido());
            etEmail.setText(creador.getMail());
            etContra.setText(creador.getPassword());
            Glide.with(root.getContext())
                    .load(ApiClient.imageURL() + creador.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivAvatar);
            tvAvatar.setText(creador.getAvatar());
        });
        perfilViewModel.getTextB().observe(getViewLifecycleOwner(), s -> btEditar.setText(s));
        perfilViewModel.getDrawableM().observe(getViewLifecycleOwner(), drawable -> {
            Drawable[] dr = btEditar.getCompoundDrawables();
            ((BitmapDrawable)dr[0]).setBitmap(((BitmapDrawable)drawable).getBitmap());
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
        btEditar.setOnClickListener(view -> {
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
        });
    }
}