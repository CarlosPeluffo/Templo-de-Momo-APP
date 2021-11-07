package com.peluffo.eltemplodemomo.ui.noticia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.databinding.FragmentNoticiaDetalleBinding;
import com.peluffo.eltemplodemomo.util.Convertidor;
import com.peluffo.eltemplodemomo.modelo.Noticia;

public class NoticiaDetalleFragment extends Fragment {
    private NoticiaDetalleViewModel ndViewModel;
    private FragmentNoticiaDetalleBinding binding;

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

        ndViewModel.getTextoM().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                btEditar.setText(s);
            }
        });
        ndViewModel.getEstadoM().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                tvTitulo.setEnabled(aBoolean);
                tvCuerpo.setEnabled(aBoolean);
            }
        });
        ndViewModel.getNoticiaM().observe(getViewLifecycleOwner(), new Observer<Noticia>() {
            @Override
            public void onChanged(Noticia noticia) {
                Convertidor convertidor = new Convertidor();
                tvIdN.setText(String.valueOf(noticia.getId()));
                tvFecha.setText(convertidor.fecha(noticia.getFecha()));
                tvTitulo.setText(noticia.getTitulo());
                tvCuerpo.setText(noticia.getCuerpo());
                tvCreador.setText(noticia.getCreador().getNickName());
                btComentario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idNoticia", noticia.getId());
                        Navigation.findNavController(view).navigate(R.id.nav_comentario, bundle);
                    }
                });
                btEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String texto = ((Button)view).getText().toString();
                        Noticia n = new Noticia(
                                noticia.getId(), noticia.getFecha(), tvTitulo.getText().toString(),
                                tvCuerpo.getText().toString(), 0, 0
                        );
                        ndViewModel.editar(texto, n);
                    }
                });
            }
        });
        ndViewModel.cargar(getArguments());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}