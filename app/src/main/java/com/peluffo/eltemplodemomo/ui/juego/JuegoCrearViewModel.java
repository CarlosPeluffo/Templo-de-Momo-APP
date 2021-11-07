package com.peluffo.eltemplodemomo.ui.juego;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.peluffo.eltemplodemomo.modelo.Juego;
import com.peluffo.eltemplodemomo.request.ApiClient;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JuegoCrearViewModel extends AndroidViewModel {
    private MutableLiveData<byte[]> byteM;
    private MutableLiveData<Juego> juegoM;
    private Context context;

    public JuegoCrearViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<byte[]> getByteM() {
        if(byteM == null){
            byteM = new MutableLiveData<>();
        }
        return byteM;
    }

    public LiveData<Juego> getJuegoM() {
        if(juegoM == null){
            juegoM = new MutableLiveData<>();
        }
        return juegoM;
    }

    public void cargarImagen(int requestCode, int resultCode, @Nullable Bitmap bit, int codSelecciona) {
        if(requestCode == codSelecciona && resultCode == RESULT_OK){
            Bitmap bitmap = bit;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            byte [] bytes = output.toByteArray();
            byteM.setValue(bytes);
        }else{
            Toast.makeText(context, "Debe cargar una imagen", Toast.LENGTH_LONG).show();
        }
    }
    public void crearJuego(Juego juego){
        if(byteM.getValue() != null){
            SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
            String token = sp.getString("token", "no token");
            Call<Juego> call = ApiClient.getMyApiClient().crearJuego(token, juego);
            call.enqueue(new Callback<Juego>() {
                @Override
                public void onResponse(Call<Juego> call, Response<Juego> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(context, "Creado con éxito", Toast.LENGTH_LONG).show();
                        juegoM.postValue(response.body());
                    }else{
                        Toast.makeText(context, "Debe cargar una imagen", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Juego> call, Throwable t) {
                    Log.d("Salida", "Error "+ t.getMessage());
                }
            });
        }
    }
}