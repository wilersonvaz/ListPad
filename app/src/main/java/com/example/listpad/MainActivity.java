package com.example.listpad;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static  int idUsuario = -1;
    public static String nomeUsuario = "";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.idTextView);
        if(idUsuario < 0){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }else{
            textView.setText("Seja bem-vindo "+nomeUsuario+"!");
        }

    //Fecha o onCreate
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                break;
            case R.id.sair:
                idUsuario = -1;
                nomeUsuario = "";
                Intent sair = new Intent(this, Login.class);
                startActivity(sair);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}