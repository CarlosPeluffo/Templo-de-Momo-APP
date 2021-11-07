package com.peluffo.eltemplodemomo.ui.noticia;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

import com.peluffo.eltemplodemomo.MainActivity;
import com.peluffo.eltemplodemomo.modelo.Noticia;
import com.peluffo.eltemplodemomo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiaDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Noticia> noticiaM;
    private MutableLiveData<Boolean> estadoM;
    private MutableLiveData<String> textoM;
    private Noticia noticia;
    private Context context;

    public NoticiaDetalleViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Noticia> getNoticiaM() {
        if(noticiaM == null){
            noticiaM = new MutableLiveData<>();
        }
        return noticiaM;
    }

    public LiveData<Boolean> getEstadoM() {
        if(estadoM == null){
            estadoM = new MutableLiveData<>();
        }
        return estadoM;
    }

    public LiveData<String> getTextoM() {
        if(textoM == null){
            textoM = new MutableLiveData<>();
        }
        return textoM;
    }

    public void cargar(Bundle b){
        noticia = (Noticia) b.getSerializable("noticia");
        noticiaM.setValue(noticia);
    }

    public void editar(String boton, Noticia n){
        if(boton.equals("Editar")){
            estadoM.setValue(true);
            textoM.setValue("Guardar");
        }else {
            SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
            String token = sp.getString("token", "no token");
            Call<Noticia> call = ApiClient.getMyApiClient().actualizarNoticia(token, n.getId(), n);
            call.enqueue(new Callback<Noticia>() {
                @Override
                public void onResponse(Call<Noticia> call, Response<Noticia> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Actualizada con éxito", Toast.LENGTH_LONG).show();
                        estadoM.postValue(false);
                        textoM.postValue("Editar");
                        noticiaM.setValue(response.body());
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Noticia> call, Throwable t) {

                }
            });
        }
    }
}