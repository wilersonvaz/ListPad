package com.example.listpad;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import Adapter.AdapterLista;
import model.DbHelper;
import model.Lista;
import modelDAO.ListaDAO;

public class MainActivity extends AppCompatActivity {

    public static  int idUsuario = -1;
    public static String nomeUsuario = "";
    TextView textView;
    ArrayList<Lista> lista = new ArrayList<>();
    RecyclerView idRecyclerLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(idUsuario < 0){
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }else{
            textView = findViewById(R.id.idTextView);
            textView.setText("Seja bem-vindo "+nomeUsuario+"!");
//            this.setTitle("Ola "+nomeUsuario+"!");

            if(getIntent() != null && getIntent().hasExtra("salvarLista")){
                Bundle bundle = getIntent().getExtras();
                int salvarLista = bundle.getInt("salvarLista");

                if(salvarLista > 0){
                    Log.i("Log # ","Lista cadastrada com sucesso!");
                    Snackbar.make(findViewById(android.R.id.content) , "Lista cadastrada com sucesso!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(findViewById(android.R.id.content) , "Ocorreu um erro ao cadastrar a lista!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            idRecyclerLista = findViewById(R.id.idRecyclerLista);
            idRecyclerLista.setLayoutManager(new LinearLayoutManager(this));

            listarListas();

            AdapterLista adapterLista = new AdapterLista(lista);
            idRecyclerLista.setAdapter(adapterLista);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent lista = new Intent(getApplicationContext(), TelaCadastroLista.class);
                    startActivity(lista);
                }
            });

        }

        //Fecha o onCreate
    }

    private void listarListas() {
        try{
            DbHelper dbHelper= new DbHelper(getApplicationContext());
            ListaDAO listaDAO = new ListaDAO(dbHelper);
            for(Lista lst: listaDAO.listarlistas()){
                lista.add(new Lista(lst.getIdLista(), lst.getIdUsuario(), lst.getCategoria(), lst.getDescricaoLista(), lst.getFlagUrgencia()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
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
                    idUsuario = -1;
                    nomeUsuario = "";
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