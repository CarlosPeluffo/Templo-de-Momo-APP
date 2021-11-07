package com.peluffo.eltemplodemomo.ui.juego;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.peluffo.eltemplodemomo.modelo.Juego;

public class JuegoDetalleViewModel extends ViewModel {
    private MutableLiveData<Juego> juegoM;
    private Juego juego;

    public LiveData<Juego> getJuegoM() {
        if(juegoM == null){
            juegoM = new MutableLiveData<>();
        }
        return juegoM;
    }
    public void cargar(Bundle bundle){
        juego = (Juego) bundle.getSerializable("juego");
        juegoM.setValue(juego);
    }
}