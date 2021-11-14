package com.example.listpad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import model.DbHelper;
import model.Usuarios;
import modelDAO.UsuariosDAO;

public class TelaCadastroUsuario extends AppCompatActivity {

    EditText edtNomeUsuario, edtEmailUsuario, edtSenhaUsuario;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        edtNomeUsuario = findViewById(R.id.edtNomeUsuario);
        edtEmailUsuario = findViewById(R.id.edtEmailUsuario);
        edtSenhaUsuario = findViewById(R.id.edtSenhaUsuario);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Usuarios usu = new Usuarios();
                    usu.setNomeUsuario(edtNomeUsuario.getText().toString());
                    usu.setEmailUsuario(edtEmailUsuario.getText().toString());
                    usu.setSenhaUsuario(edtSenhaUsuario.getText().toString());

                    DbHelper dbHelper = new DbHelper(getApplicationContext());

                    UsuariosDAO usuDAO = new UsuariosDAO(dbHelper);
                    int usuarioExiste = usuDAO.checaEmailExiste(usu);
                    Log.i("Log # ", "UsuarioExiste: "+usuarioExiste);

                    if(usuarioExiste < 1){
                        if(usuDAO.addUsuario(usu) > 0){
                            Snackbar.make(view, "Usuário cadastrado com sucesso!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            edtNomeUsuario.setText(null);
                            edtEmailUsuario.setText(null);
                            edtSenhaUsuario.setText(null);
                        }
                    }else{
                        Snackbar.make(view, "Email já cadastrado!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnVoltar:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
            case R.id.btnApagarBase:
                try{
                    getApplicationContext().deleteDatabase(DbHelper.DATABASE_NAME);
                    Log.i("Log # ",  DbHelper.DATABASE_NAME+" apagada");
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }

    }
}