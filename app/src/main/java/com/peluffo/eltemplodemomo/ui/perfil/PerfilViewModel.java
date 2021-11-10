package com.peluffo.eltemplodemomo.ui.perfil;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.peluffo.eltemplodemomo.MainActivity;
import com.peluffo.eltemplodemomo.R;
import com.peluffo.eltemplodemomo.modelo.Creador;
import com.peluffo.eltemplodemomo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> estadoM;
    private MutableLiveData<Creador> creadorM;
    private MutableLiveData<String> textB;
    private MutableLiveData<Drawable> drawableM;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Boolean> getEstadoM() {
        if(estadoM == null){
            estadoM = new MutableLiveData<>();
        }
        return estadoM;
    }

    public LiveData<Creador> getCreadorM(){
        if(creadorM == null){
            creadorM = new MutableLiveData<>();
        }
        return creadorM;
    }

    public LiveData<String> getTextB() {
        if(textB == null){
            textB = new MutableLiveData<>();
        }
        return textB;
    }

    public LiveData<Drawable> getDrawableM() {
        if(drawableM == null){
            drawableM = new MutableLiveData<>();
        }
        return drawableM;
    }

    public void cargarCreador(){
        SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
        String token = sp.getString("token", "no token");
        Call<Creador> call = ApiClient.getMyApiClient().perfil(token);
        call.enqueue(new Callback<Creador>() {
            @Override
            public void onResponse(@NonNull Call<Creador> call, @NonNull Response<Creador> response) {
                if(response.isSuccessful()) {
                    creadorM.postValue(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Creador> call, @NonNull Throwable t) {
                Log.d("Salida", t.getMessage());
            }
        });
    }
    public void cambiarEstado(String t, final Creador c){
        if(t.equals("Editar")){
            estadoM.setValue(true);
            textB.setValue("Guardar");
            drawableM.setValue(ContextCompat.getDrawable(context, R.drawable.ic_guardar_30));
        }else {
            SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
            String token = sp.getString("token", "no token");
            Call<Creador> callAct = ApiClient.getMyApiClient().actualizarCreador(token, c);
            callAct.enqueue(new Callback<Creador>() {
                @Override
                public void onResponse(@NonNull Call<Creador> call, @NonNull Response<Creador> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Guardado con éxito", Toast.LENGTH_LONG).show();
                        estadoM.postValue(false);
                        textB.postValue("Editar");
                        drawableM.postValue(ContextCompat.getDrawable(context, R.drawable.ic_editar_30));
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Creador> call, @NonNull Throwable t) {
                    Log.d("Salida", t.getMessage());
                }
            });
        }
    }
}
