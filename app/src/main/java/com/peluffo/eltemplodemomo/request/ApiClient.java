package com.peluffo.eltemplodemomo.request;

import android.util.Log;

import com.google.gson.*;
import com.peluffo.eltemplodemomo.modelo.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public class ApiClient {
    private static final String URLBASE ="http://192.168.1.105:5001/api/";
    private static PostInterface postInterface;

    public static PostInterface getMyApiClient(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        postInterface = retrofit.create(PostInterface.class);
        Log.d("Salida", retrofit.baseUrl().toString());
        return postInterface;
    }
    public interface PostInterface{
        @FormUrlEncoded
        @POST("Creadores/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("Creadores")
        Call<Creador> perfil(@Header("Authorization") String Token);

        @PUT("Creadores")
        Call<Creador> actualizarCreador(@Header("Authorization") String token, @Body Creador c);

        @GET("Juegos")
        Call<List<Juego>> juegos(@Header("Authorization") String token);

        @GET("Juegos/{id}")
        Call<Juego> unJuego(@Header("Authorization") String token, @Path("id") int idIn);

        @POST("Juegos")
        Call<Juego> crearJuego(@Header("Authorization") String token, @Body Juego juego);

        @GET("Noticias/{id}")
        Call<List<Noticia>> noticiasDeJuego(@Header("Authorization") String token, @Path("id") int idIn);

        @GET("Noticias/Detalles/{id}")
        Call<Noticia> unaNoticia(@Header("Authorization") String token, @Path("id") int idNot);

        @POST("Noticias")
        Call<Noticia> crearNoticia(@Header("Authorization") String token, @Body Noticia noticia);

        @PUT("Noticias/{id}")
        Call<Noticia> actualizarNoticia(@Header("Authorization") String token, @Path("id") int idNot, @Body Noticia n);

        @GET("Comentarios/{id}")
        Call<List<Comentario>> comentsNoticia(@Header("Authorization") String token, @Path("id") int idNot);
    }
}
