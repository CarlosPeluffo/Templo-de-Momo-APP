package com.peluffo.eltemplodemomo.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convertidor {
    public Convertidor() {}

    @SuppressLint("SimpleDateFormat")
    public static String fecha(String fecha){
        String dia ="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try{
            Date d = dateFormat.parse(fecha);
            assert d != null;
            dia = formato.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }
    @SuppressLint("SimpleDateFormat")
    public static String fechaYHora(String fecha){ //LocalDate
        String dia ="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try{
            Date d = dateFormat.parse(fecha);
            assert d != null;
            dia = formato.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }
}
