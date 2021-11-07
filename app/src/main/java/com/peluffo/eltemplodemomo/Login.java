package com.peluffo.eltemplodemomo;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText mail, contraseña;
    private Button btLogin;
    private TextView tvMensaje;
    private LoginViewModel loginViewModel;
    private ImageView ivLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarVista();
        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        loginViewModel.getVisibleM().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tvMensaje.setVisibility(integer);
            }
        });
    }
    private void iniciarVista(){
        ivLogin = findViewById(R.id.ivLogin);
        btLogin = findViewById(R.id.btLogin);
        tvMensaje = findViewById(R.id.tvMensaje);
        mail = findViewById(R.id.etMail);
        contraseña = findViewById(R.id.etPassword);
        ivLogin.setImageResource(R.drawable.logo);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.iniciarSesion(mail.getText().toString(),
                        contraseña.getText().toString());
            }
        });
        if(permisos()){
            btLogin.setEnabled(true);
        }else{
            btLogin.setEnabled(false);
        }
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
            if(grantResults.length== 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                btLogin.setEnabled(true);
            }else{
                permisoManual();
            }
        }
    }

    private void cargarDialogo(){
        AlertDialog.Builder dialogo= new AlertDialog.Builder(Login.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 1000);
            }
        });
        dialogo.show();
    }
    private void permisoManual(){
        final CharSequence[] opciones={"Si", "No"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(Login.this);
        alertOpciones.setTitle("¿Desea configurar los permisos manualmente?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
            }
        });
        alertOpciones.show();
    }
}