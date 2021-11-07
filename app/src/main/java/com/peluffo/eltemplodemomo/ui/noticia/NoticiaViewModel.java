package com.peluffo.eltemplodemomo.ui.noticia;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

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
    private Context context;

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
            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                if(response.isSuccessful()){
                    listaM.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                Log.d("Salida", t.getMessage());
            }
        });
    }
}