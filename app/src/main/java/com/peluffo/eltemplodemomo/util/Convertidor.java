package com.peluffo.eltemplodemomo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convertidor {
    public Convertidor() {}

    public static String fecha(String fecha){
        String dia ="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try{
            Date d = dateFormat.parse(fecha);
            dia = formato.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }
    public static String fechaYHora(String fecha){ //LocalDate
        String dia ="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try{
            Date d = dateFormat.parse(fecha);
            dia = formato.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }
}
