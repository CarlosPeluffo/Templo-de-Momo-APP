package com.peluffo.eltemplodemomo.ui.juego;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.modelo.Juego;
import com.peluffo.eltemplodemomo.request.ApiClient;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JuegoCrearViewModel extends AndroidViewModel {
    private MutableLiveData<byte[]> byteM;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

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

    public void cargarImagen(int requestCode, int resultCode, @Nullable Bitmap bit, int codSelecciona) {
        if(requestCode == codSelecciona && resultCode == RESULT_OK){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            assert bit != null;
            bit.compress(Bitmap.CompressFormat.JPEG, 100, output);
            byte [] bytes = output.toByteArray();
            byteM.setValue(bytes);
        }else{
            Toast.makeText(context, "Debe cargar una imagen", Toast.LENGTH_LONG).show();
        }
    }
    public void crearJuego(Juego juego, View view){
        if(byteM.getValue() != null){
            SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
            String token = sp.getString("token", "no token");
            Call<Juego> call = ApiClient.getMyApiClient().crearJuego(token, juego);
            call.enqueue(new Callback<Juego>() {
                @Override
                public void onResponse(@NonNull Call<Juego> call, @NonNull Response<Juego> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(context, "Creado con éxito", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.nav_juego);
                    }else{
                        Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Juego> call, @NonNull Throwable t) {
                    Log.d("Salida", "Error "+ t.getMessage() +"Cause: " +t.getCause());
                    if(t.getCause() == null){
                        Toast.makeText(context, "Ya existe un juego con ese nombre.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(context, "Debe cargar una imagen", Toast.LENGTH_LONG).show();
        }
    }
}