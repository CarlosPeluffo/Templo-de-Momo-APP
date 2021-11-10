package com.peluffo.eltemplodemomo.ui.juego;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import androidx.fragment.app.Fragment;

import com.peluffo.eltemplodemomo.databinding.FragmentJuegoCrearBinding;
import com.peluffo.eltemplodemomo.modelo.Juego;

import java.io.IOException;
import java.util.Base64;

public class JuegoCrearFragment extends Fragment {
    private JuegoCrearViewModel vmJuegoC;
    private FragmentJuegoCrearBinding binding;
    private static final int COD_SELECCIONA = 10;
    private Juego juego;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vmJuegoC = new ViewModelProvider(this).get(JuegoCrearViewModel.class);
        binding = FragmentJuegoCrearBinding.inflate(inflater, container, false);
        juego = new Juego();
        guardaImagen();
        View root = binding.getRoot();
        final EditText etTitulo = binding.etTituloJCrear;
        final EditText etDescripcion = binding.etDescripJCrear;
        final EditText etRequisitos = binding.etRequJCrear;
        final EditText etPrecio = binding.etPrecioJCrear;
        final Button btCargar = binding.btCargaJCrear;
        final Button btCrear = binding.btCrearJuego;
        btCargar.setOnClickListener(view -> mostrarDialogOpciones());
        btCrear.setOnClickListener(view -> {
            juego.setId(0);
            juego.setTitulo(etTitulo.getText().toString());
            juego.setDescripcion(etDescripcion.getText().toString());
            juego.setRequisitos(etRequisitos.getText().toString());
            juego.setPrecio(
                    Double.parseDouble(
                            (etPrecio.getText().toString().equals("")) ? "0" : etPrecio.getText().toString() ));
            juego.setCreadorId(0);

            vmJuegoC.crearJuego(juego, view);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void guardaImagen(){
        vmJuegoC.getByteM().observe(getViewLifecycleOwner(), bytes -> {
            String nube = Base64.getEncoder().encodeToString(bytes);
            juego.setPortadaMovil(nube);
        });
    }
    @SuppressLint({"IntentReset", "QueryPermissionsNeeded"})
    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, (dialogInterface, i) -> {
            if(opciones[i].equals("Elegir de Galeria")){ //ACTION_PEAK
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                if(intent.resolveActivity(requireActivity().getPackageManager()) != null){
                    startActivityForResult(intent, COD_SELECCIONA);
                }
            }else{
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        final ImageView ivPortada = binding.ivPortadaJCrear;
        if (requestCode == COD_SELECCIONA) {
            assert data != null;
            Uri path = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), path);
                ivPortada.setImageBitmap(bitmap); //imageView
                vmJuegoC.cargarImagen(requestCode, resultCode, bitmap, COD_SELECCIONA);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}