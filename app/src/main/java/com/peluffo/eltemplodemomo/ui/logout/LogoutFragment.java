package com.peluffo.eltemplodemomo.ui.logout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peluffo.eltemplodemomo.Login;
import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {
    private FragmentLogoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        cerrarAplicacion(root.getContext());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void cerrarAplicacion(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon(R.drawable.ic_logout);
        builder.setTitle("¿Realmente desea cerrar sesión?");
        builder.setCancelable(false);
        builder.setNegativeButton(android.R.string.cancel,
                (dialogInterface, i) -> requireActivity().onBackPressed());
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            SharedPreferences sp = c.getSharedPreferences("Usuarios", 0);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            Intent intent = new Intent(getActivity(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        builder.show();
    }
}