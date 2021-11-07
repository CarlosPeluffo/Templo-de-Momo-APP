package com.peluffo.eltemplodemomo;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.peluffo.eltemplodemomo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> visibleM;
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Integer> getVisibleM(){
        if(visibleM == null){
            visibleM = new MutableLiveData<>();
        }
        return visibleM;
    }

    public void iniciarSesion(String m, String c) {
        Call<String> call = ApiClient.getMyApiClient().login(m, c);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    visibleM.postValue(View.INVISIBLE);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Usuarios", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", "Bearer " + response.body());
                    if (editor.commit()) {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                } else {
                    visibleM.postValue(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Salida", t.getMessage() + "");
            }
        });
    }
}
