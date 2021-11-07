package com.peluffo.eltemplodemomo.ui.noticia;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.peluffo.eltemplodemomo.modelo.Juego;
import com.peluffo.eltemplodemomo.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiaJuegoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Juego>> lista;
    private Context context;

    public NoticiaJuegoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Juego>> getLista() {
        if(lista == null){
            lista = new MutableLiveData<>();
        }
        return lista;
    }
    public void cargarJuegos(){
        SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
        String token = sp.getString("token", "no token");
        Call<List<Juego>> call = ApiClient.getMyApiClient().juegos(token);
        call.enqueue(new Callback<List<Juego>>() {
            @Override
            public void onResponse(Call<List<Juego>> call, Response<List<Juego>> response) {
                if(response.isSuccessful()){
                    lista.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Juego>> call, Throwable t) {
                Log.d("Salida", t.getMessage());
            }
        });
    }
}