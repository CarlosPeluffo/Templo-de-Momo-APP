package com.peluffo.eltemplodemomo;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    private MutableLiveData<Integer> pbM;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

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

    public LiveData<Integer> getPbM() {
        if(pbM == null){
            pbM = new MutableLiveData<>();
        }
        return pbM;
    }

    public void iniciarSesion(String m, String c) {
        if(m.length()>10 && c.length()>0) {
            pbM.setValue(View.VISIBLE);
            visibleM.postValue(View.INVISIBLE);
            Call<String> call = ApiClient.getMyApiClient().login(m, c);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("Usuarios", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", "Bearer " + response.body());
                        if (editor.commit()) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    } else {
                        pbM.postValue(View.INVISIBLE);
                        visibleM.postValue(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Log.d("Salida", t.getMessage() + "");
                }
            });
        }else{
            pbM.setValue(View.INVISIBLE);
            Toast.makeText(context, "Complete con datos válidos", Toast.LENGTH_LONG).show();
        }
    }
}
