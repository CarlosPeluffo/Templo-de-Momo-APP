package com.peluffo.eltemplodemomo.ui.noticia;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;
import androidx.navigation.Navigation;

import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.modelo.Noticia;
import com.peluffo.eltemplodemomo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticiaDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Noticia> noticiaM;
    private MutableLiveData<Boolean> estadoM;
    private MutableLiveData<String> textoM;
    private MutableLiveData<Drawable> drawableM;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

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

    public LiveData<Drawable> getDrawableM() {
        if(drawableM == null){
            drawableM = new MutableLiveData<>();
        }
        return drawableM;
    }

    public void cargar(Bundle b){
        Noticia noticia = (Noticia) b.getSerializable("noticia");
        noticiaM.setValue(noticia);
    }

    public void editar(String boton, final Noticia n, View view){
        if(boton.equals("Editar")){
            estadoM.setValue(true);
            textoM.setValue("Guardar");
            drawableM.postValue(ContextCompat.getDrawable(context, R.drawable.ic_guardar_30));
        }else {
            SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
            String token = sp.getString("token", "no token");
            Call<Noticia> call = ApiClient.getMyApiClient().actualizarNoticia(token, n.getId(), n);
            call.enqueue(new Callback<Noticia>() {
                @Override
                public void onResponse(@NonNull Call<Noticia> call, @NonNull Response<Noticia> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Actualizada con éxito", Toast.LENGTH_LONG).show();
                        estadoM.postValue(false);
                        textoM.postValue("Editar");
                        drawableM.postValue(ContextCompat.getDrawable(context, R.drawable.ic_editar_30));
                        Bundle bundle = new Bundle();
                        Noticia n = response.body();
                        assert n != null;
                        bundle.putInt("juego", n.getJuegoId());
                        Navigation.findNavController(view).navigate(R.id.nav_noticia, bundle);
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
}