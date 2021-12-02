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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.DbHelper;
import model.Lista;
import model.Usuarios;
import modelDAO.CategoriaDAO;
import modelDAO.ListaDAO;

public class TelaCadastroLista extends AppCompatActivity {
    private EditText descricaoLista;
    private Spinner spinnerCategoriaLista;
    private Button btnSalvarLista;
    private CheckBox idFlagUrgencia;
    private int idLista = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro_lista);

        descricaoLista = findViewById(R.id.edtDescricaoLista);
        spinnerCategoriaLista = findViewById(R.id.spinnerCategoriaLista);
        idFlagUrgencia = findViewById(R.id.idFlagUrgencia);

        DbHelper dbHelper = new DbHelper(getApplicationContext());

        ListaDAO listaDAO = new ListaDAO(dbHelper);

        CategoriaDAO catDAO = new CategoriaDAO(dbHelper);

        ArrayList<Categoria> categoriaLista = new ArrayList<>();

        for(Categoria lst: catDAO.listarCategorias()){
            Categoria cat = new Categoria();
            cat.setIdCategoria(lst.getIdCategoria());
            cat.setDescricaoCategoria(lst.getDescricaoCategoria());
            categoriaLista.add(cat);
//            categoriaLista.add(lst.getDescricaoCategoria().toString()) ;
        }

        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, categoriaLista );

        spinnerCategoriaLista.setAdapter(adapterCategoria);

        btnSalvarLista = findViewById(R.id.btnSalvarLista);

        if(getIntent() != null && getIntent().hasExtra("idLista")){
            Bundle bundle = getIntent().getExtras();
            idLista = bundle.getInt("idLista");

            Log.i("Log # ", "Id da lista recebida: "+idLista);

//            DbHelper dbHelper = new DbHelper(getApplicationContext());
            Lista lista = new Lista();
            lista.setIdLista(idLista);
            lista.setIdUsuario(MainActivity.idUsuario);

            //listaDao foi instanciado no começo do onCreate
            for(Lista lst: listaDAO.populaLista(lista)){
                descricaoLista.setText(lst.getDescricaoLista());
                if(lst.getFlagUrgencia() > 0){
                    idFlagUrgencia.setChecked(true);
                }else{
                    idFlagUrgencia.setChecked(false);
                }
                Log.i("Log # ", lst.getCategoria().getDescricaoCategoria());
            }

        }

        btnSalvarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Categoria c = (Categoria) spinnerCategoriaLista.getSelectedItem();
                    int idCategoria = c.getIdCategoria();

                    int i = 0;
                    if(idFlagUrgencia.isChecked()){
                        i = 1;
                    }

                    DbHelper dbHelper = new DbHelper(getApplicationContext());

                    Lista lista = new Lista();
                    lista.setIdLista(idLista);
                    lista.setIdUsuario(MainActivity.idUsuario);
                    Categoria cat = new Categoria();
                    cat.setIdCategoria(idCategoria);
                    lista.setCategoria(cat);
                    lista.setDescricaoLista(descricaoLista.getText().toString());
                    lista.setFlagUrgencia(i);

                    ListaDAO listaDAO = new ListaDAO(dbHelper);

                    int retorno = -1;
                    if(idLista > 0){
                        retorno = listaDAO.updateLista(lista);
                    }else{
                        retorno = listaDAO.addLista(lista);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putInt("salvarLista", retorno);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

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