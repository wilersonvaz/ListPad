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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import Adapter.AdapterLista;
import model.DbHelper;
import model.Lista;
import modelDAO.CategoriaDAO;
import modelDAO.ListaDAO;

public class MainActivity extends AppCompatActivity {

    public static  int idUsuario = -1;
    public static String nomeUsuario = "";
    TextView textView,idVerDetalhesLista,idExcluirDetalhesLista;
    ArrayList<Lista> lista = new ArrayList<>();
    RecyclerView idRecyclerLista;
    int idLista = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(idUsuario < 0){
//            Intent intent = new Intent(this, Login.class);
//            startActivity(intent);
//        }else{
        DbHelper dbHelper= new DbHelper(getApplicationContext());
        CategoriaDAO categoriaDAO = new CategoriaDAO(dbHelper);
        int countCategoria = categoriaDAO.countCategorias();
        if(countCategoria > 0) {
            textView = findViewById(R.id.idTextView);
            textView.setText("Seja bem-vindo " + nomeUsuario + "!");
            idVerDetalhesLista = findViewById(R.id.idVerDetalhesLista);
            idExcluirDetalhesLista = findViewById(R.id.idExcluirDetalhesLista);


            if (getIntent() != null && getIntent().hasExtra("salvarLista")) {
                Bundle bundle = getIntent().getExtras();
                int salvarLista = bundle.getInt("salvarLista");

                if (salvarLista > 0) {
                    Snackbar.make(findViewById(android.R.id.content), "Lista salva com sucesso!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Ocorreu um erro ao salvar a lista!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            if (getIntent() != null && getIntent().hasExtra("excluirLista")) {
                Bundle bundle = getIntent().getExtras();
                int excluirLista = bundle.getInt("excluirLista");

                if (excluirLista > 0) {
                    Snackbar.make(findViewById(android.R.id.content), "Lista Excluída com sucesso!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Ocorreu um erro ao excluir a lista!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            idRecyclerLista = findViewById(R.id.idRecyclerLista);
            idRecyclerLista.setLayoutManager(new LinearLayoutManager(this));

            listarListas();

            AdapterLista adapterLista = new AdapterLista(lista);
            adapterLista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (lista != null) {
                        idLista = lista.get(idRecyclerLista.getChildAdapterPosition(view)).getIdLista();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("idLista", idLista);
                    Intent intent = new Intent(getApplicationContext(), TelaCadastroLista.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            idRecyclerLista.setAdapter(adapterLista);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent lista = new Intent(getApplicationContext(), TelaCadastroLista.class);
                    startActivity(lista);
                }
            });

//        }

        }else{
            Toast.makeText(getApplicationContext(), "Por favor, adicione pelo menos uma categoria!", Toast.LENGTH_LONG).show();
            Intent cat = new Intent(this, TelaCategoria.class);
            startActivity(cat);
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
//                case R.id.sair:
//                    idUsuario = -1;
//                    nomeUsuario = "";
//                    Intent sair = new Intent(this, Login.class);
//                    startActivity(sair);
//                    break;
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