package com.example.listpad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import model.Categoria;
import model.DbHelper;
import model.Usuarios;
import modelDAO.CategoriaDAO;

public class TelaCadastroCategoria extends AppCompatActivity {
    EditText edtDescricaoCategoria;
    Button btnSalvarCategoria;
    FloatingActionButton btnAdicionaCategoria;
    int idCategoria= -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_categoria);

        edtDescricaoCategoria = findViewById(R.id.edtDescricaoCategoria);
        btnSalvarCategoria = findViewById(R.id.btnSalvarCategoria);

        if(getIntent() != null && getIntent().hasExtra("idCategoria")){
            Bundle bundle = getIntent().getExtras();
            idCategoria = bundle.getInt("idCategoria");

            DbHelper dbHelper = new DbHelper(getApplicationContext());
            Categoria cat = new Categoria();
            cat.setIdCategoria(idCategoria);

            CategoriaDAO catDAO = new CategoriaDAO(dbHelper);
            for(Categoria lst: catDAO.populaCategoria(cat)){
                edtDescricaoCategoria.setText(lst.getDescricaoCategoria());
            }
        }

        btnSalvarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    DbHelper dbHelper = new DbHelper(getApplicationContext());

                    Usuarios usu = new Usuarios();
                    usu.setIdUsuario(MainActivity.idUsuario);

                    Categoria cat = new Categoria();
                    cat.setIdCategoria(idCategoria);
                    cat.setUsuarios(usu);
                    cat.setDescricaoCategoria(edtDescricaoCategoria.getText().toString());

                    CategoriaDAO catDAO = new CategoriaDAO(dbHelper);

                    //Se o id já existe altera, senão insere
                    if(idCategoria > 0){
                        int retorno = catDAO.updateCategoria(cat);

                        if(retorno > 0){
                            Intent intent = new Intent(getApplicationContext(), TelaCategoria.class);
                            startActivity(intent);
                        }else{
                            Snackbar.make(view, "Ocorreu um erro ao alterar a Catergoria!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }else{

                        int retorno = catDAO.addCategoria(cat);

                        if(retorno > 0){
                            Intent intent = new Intent(getApplicationContext(), TelaCategoria.class);
                            startActivity(intent);
                        }else{
                            Snackbar.make(view, "Ocorreu um erro ao cadastrar a Catergoria!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try{
            switch (item.getItemId()){
                case R.id.home:
                    Intent main = new Intent(this, MainActivity.class);
                    startActivity(main);
                    break;
                case R.id.sair:
                    MainActivity.idUsuario = -1;
                    MainActivity.nomeUsuario = "";
                    Intent sair = new Intent(this, Login.class);
                    startActivity(sair);
                    break;
                case R.id.listaCategoria:
                    Intent cat = new Intent(this, TelaCategoria.class);
                    startActivity(cat);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }
}