package com.peluffo.eltemplodemomo.ui.noticia;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.Navigation;

import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.modelo.Noticia;
import com.peluffo.eltemplodemomo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiaCrearViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public NoticiaCrearViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void crearNoticia(Bundle b, Noticia noticia, View view){
        int i = b.getInt("juego");
        noticia.setJuegoId(i);
        SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
        String token = sp.getString("token", "no token");
        Call<Noticia> call = ApiClient.getMyApiClient().crearNoticia(token, noticia);
        call.enqueue(new Callback<Noticia>() {
            @Override
            public void onResponse(@NonNull Call<Noticia> call, @NonNull Response<Noticia> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Creada con éxito", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.nav_noticia, b);
                }else{
                    Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Noticia> call, @NonNull Throwable t) {
                Log.d("Salida", t.getMessage());
            }
        });
    }
}