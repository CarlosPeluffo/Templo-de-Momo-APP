package com.peluffo.eltemplodemomo.ui.noticia;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiaCrearViewModel extends AndroidViewModel {
    private MutableLiveData<Noticia> noticiaM;
    private Context context;

    public NoticiaCrearViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Noticia> getNoticiaM() {
        if(noticiaM == null){
            noticiaM = new MutableLiveData<>();
        }
        return noticiaM;
    }
    public void crearNoticia(Bundle b, Noticia noticia){
        int i = b.getInt("juego");
        noticia.setJuegoId(i);
        SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
        String token = sp.getString("token", "no token");
        Call<Noticia> call = ApiClient.getMyApiClient().crearNoticia(token, noticia);
        call.enqueue(new Callback<Noticia>() {
            @Override
            public void onResponse(Call<Noticia> call, Response<Noticia> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Creada con éxito", Toast.LENGTH_LONG).show();
                    noticiaM.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Noticia> call, Throwable t) {
                Log.d("Salida", t.getMessage());
            }
        });
    }
}