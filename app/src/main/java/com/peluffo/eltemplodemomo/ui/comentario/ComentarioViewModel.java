package com.peluffo.eltemplodemomo.ui.comentario;


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

import com.peluffo.eltemplodemomo.modelo.Comentario;
import com.peluffo.eltemplodemomo.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentarioViewModel extends AndroidViewModel {
    private MutableLiveData<List<Comentario>> comentarioM;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public ComentarioViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Comentario>> getComentarioM() {
        if(comentarioM == null){
            comentarioM = new MutableLiveData<>();
        }
        return comentarioM;
    }
    public void cargarComentarios(Bundle b){
        int i = b.getInt("idNoticia");
        SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
        String token = sp.getString("token", "no token");
        Call<List<Comentario>> call = ApiClient.getMyApiClient().comentsNoticia(token, i);
        call.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comentario>> call, @NonNull Response<List<Comentario>> response) {
                if(response.isSuccessful()){
                    if(response.body() !=null && response.body().size() > 0) {
                        comentarioM.postValue(response.body());
                    }else{
                        Toast.makeText(context, "No se encontraron comentarios", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context,"Ocurrió un error :c", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Comentario>> call, @NonNull Throwable t) {
                Log.d("Salida", t.getMessage());
            }
        });
    }
}