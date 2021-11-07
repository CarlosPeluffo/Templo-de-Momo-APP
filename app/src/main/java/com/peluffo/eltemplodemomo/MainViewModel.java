package com.peluffo.eltemplodemomo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.peluffo.eltemplodemomo.modelo.Creador;
import com.peluffo.eltemplodemomo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<Creador> creadorM;
    private Context context;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Creador> getCreadorM() {
        if(creadorM ==  null){
            creadorM = new MutableLiveData<>();
        }
        return creadorM;
    }
    public void cargarCreador(){
        SharedPreferences sp = context.getSharedPreferences("Usuarios", 0);
        String token = sp.getString("token", "no token");
        Call<Creador> callAct = ApiClient.getMyApiClient().perfil(token);
        callAct.enqueue(new Callback<Creador>() {
            @Override
            public void onResponse(Call<Creador> call, Response<Creador> response) {
                if(response.isSuccessful()){
                    creadorM.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Creador> call, Throwable t) {
                Log.d("Salida", t.getMessage());
            }
        });
    }
}
