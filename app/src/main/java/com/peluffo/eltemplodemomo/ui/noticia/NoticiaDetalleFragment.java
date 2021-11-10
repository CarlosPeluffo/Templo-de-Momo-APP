package com.peluffo.eltemplodemomo.ui.noticia;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.databinding.FragmentNoticiaDetalleBinding;
import com.peluffo.eltemplodemomo.util.Convertidor;
import com.peluffo.eltemplodemomo.modelo.Noticia;

public class NoticiaDetalleFragment extends Fragment {
    private NoticiaDetalleViewModel ndViewModel;
    private FragmentNoticiaDetalleBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ndViewModel = new ViewModelProvider(this).get(NoticiaDetalleViewModel.class);
        binding = FragmentNoticiaDetalleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        final TextView tvFecha = binding.tvFechaND;
        final EditText tvTitulo = binding.tvTituloND;
        final EditText tvCuerpo = binding.tvCuerpoND;
        final TextView tvCreador = binding.tvCreadorND;
        final TextView tvIdN = binding.tvIdNot;
        final Button btComentario = binding.btComentND;
        final Button btEditar = binding.btEditarNot;

        ndViewModel.getNoticiaM().observe(getViewLifecycleOwner(), noticia -> {
            tvIdN.setText(String.valueOf(noticia.getId()));
            tvFecha.setText(Convertidor.fecha(noticia.getFecha()));
            tvTitulo.setText(noticia.getTitulo());
            tvCuerpo.setText(noticia.getCuerpo());
            tvCreador.setText(noticia.getCreador().getNickName());
            btComentario.setOnClickListener(view1 -> {
                Bundle bundle = new Bundle();
                bundle.putInt("idNoticia", noticia.getId());
                Navigation.findNavController(view1).navigate(R.id.nav_comentario, bundle);
            });
            btEditar.setOnClickListener(view1 -> {
                String texto = ((Button) view1).getText().toString();
                Noticia n = new Noticia(
                        noticia.getId(), noticia.getFecha(), tvTitulo.getText().toString(),
                        tvCuerpo.getText().toString(), 0, 0
                );
                ndViewModel.editar(texto, n, view1);
            });
        });
        ndViewModel.getTextoM().observe(getViewLifecycleOwner(), btEditar::setText);
        ndViewModel.getEstadoM().observe(getViewLifecycleOwner(), aBoolean -> {
            tvTitulo.setEnabled(aBoolean);
            tvCuerpo.setEnabled(aBoolean);
        });
        ndViewModel.getDrawableM().observe(getViewLifecycleOwner(), drawable -> {
            Drawable[] dr = btEditar.getCompoundDrawables();
            ((BitmapDrawable)dr[1]).setBitmap(((BitmapDrawable)drawable).getBitmap());
        });
        ndViewModel.cargar(requireArguments());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}