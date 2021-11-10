package com.peluffo.eltemplodemomo;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText mail, contrasenia;
    private Button btLogin;
    private TextView tvMensaje;
    private LoginViewModel loginViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarVista();
        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        loginViewModel.getVisibleM().observe(this, integer -> tvMensaje.setVisibility(integer));

        loginViewModel.getPbM().observe(this, integer -> progressBar.setVisibility(integer));
    }

    private void iniciarVista(){
        ImageView ivLogin = findViewById(R.id.ivLogin);
        btLogin = findViewById(R.id.btLogin);
        tvMensaje = findViewById(R.id.tvMensaje);
        mail = findViewById(R.id.etMail);
        contrasenia = findViewById(R.id.etPassword);
        ivLogin.setImageResource(R.drawable.logo);
        progressBar = findViewById(R.id.pbLogin);
        btLogin.setOnClickListener(view -> loginViewModel.iniciarSesion(mail.getText().toString(),
                contrasenia.getText().toString()));
        btLogin.setEnabled(permisos());
    }

    private boolean permisos(){
        if(checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }if((shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE))){
            cargarDialogo();
        }else{
            requestPermissions(new String[] {READ_EXTERNAL_STORAGE}, 1000);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000){
            if(grantResults.length== 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                btLogin.setEnabled(true);
            else{
                permisoManual();
            }
        }
    }

    private void cargarDialogo(){
        AlertDialog.Builder dialogo= new AlertDialog.Builder(Login.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");
        dialogo.setPositiveButton("Aceptar", (dialogInterface, i) -> requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 1000));
        dialogo.show();
    }
    private void permisoManual(){
        final CharSequence[] opciones={"Si", "No"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(Login.this);
        alertOpciones.setTitle("¿Desea configurar los permisos manualmente?");
        alertOpciones.setItems(opciones, (dialogInterface, i) -> {
            if(opciones[i].equals("Si")){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Los permisos no fueron aceptados", Toast.LENGTH_LONG).show();
                dialogInterface.dismiss();
            }
        });
        alertOpciones.show();
    }
}