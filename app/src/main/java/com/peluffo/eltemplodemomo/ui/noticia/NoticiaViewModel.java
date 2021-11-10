package com.peluffo.eltemplodemomo.ui.noticia;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.peluffo.eltemplodemomo.modelo.Noticia;
import com.peluffo.eltemplodemomo.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiaViewModel extends AndroidViewModel {
    private MutableLiveData<List<Noticia>> listaM;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public NoticiaViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Noticia>> getListaM() {
        if(listaM == null){
            listaM = new MutableLiveData<>();
        }
        return listaM;
    }

    public void cargarNoticias(Bundle b){
        int i = b.getInt("juego");
        SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
        String token = sp.getString("token", "no token");
        Call<List<Noticia>> call = ApiClient.getMyApiClient().noticiasDeJuego(token, i);
        call.enqueue(new Callback<List<Noticia>>() {
            @Override
            public void onResponse(@NonNull Call<List<Noticia>> call, @NonNull Response<List<Noticia>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && response.body().size() > 0){
                        listaM.postValue(response.body());
                    }else{
                        Toast.makeText(context, "No se encontraron noticias", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context, "Ocurrió un error :c", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Noticia>> call, @NonNull Throwable t) {
                Log.d("Salida", t.getMessage());
            }
        });
    }
}