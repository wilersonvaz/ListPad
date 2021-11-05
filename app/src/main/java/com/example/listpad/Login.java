package com.example.listpad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import model.TelaUsuario;
import model.DbHelper;
import model.Usuarios;
import modelDAO.UsuariosDAO;

public class Login extends AppCompatActivity {

    EditText emailUsuario, senhaUsuario;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailUsuario = findViewById(R.id.edtEmailUsuario);
        senhaUsuario = findViewById(R.id.edtSenhaUsuario);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Usuarios> listaUsuario;
                try{
                    Usuarios usu = new Usuarios();
                    usu.setEmailUsuario(emailUsuario.getText().toString());
                    usu.setSenhaUsuario(senhaUsuario.getText().toString());

                    DbHelper dbHelper = new DbHelper(getApplicationContext());

                    UsuariosDAO usuDAO = new UsuariosDAO(dbHelper);

                    int login = 0;
                    for(Usuarios lst : usuDAO.login(usu)){
                        MainActivity.idUsuario = lst.getIdUsuario();
                        MainActivity.nomeUsuario = lst.getNomeUsuario();
                        login++;
                    }

                    if(login > 0){
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Snackbar.make(view, "Senha ou usuário errado!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }


//                    }else{
//
//                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void onClick(View v){
        Intent intent = new Intent(this, TelaUsuario.class);
        startActivity(intent);
//        Snackbar.make(v, "Cadastrar", Snackbar.LENGTH_LONG)
//               .setAction("Action", null).show();
    }
}