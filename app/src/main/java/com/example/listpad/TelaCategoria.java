package com.example.listpad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapter.AdapterCategoria;
import model.Categoria;
import model.DbHelper;
import modelDAO.CategoriaDAO;

public class TelaCategoria extends AppCompatActivity {
    ArrayList<Categoria> listaCategoria ;
    RecyclerView mRecyclerView;
    int idCategoria = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_categoria);
        listaCategoria = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerCategoria);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaCategorias();

        AdapterCategoria adapterCategoria = new AdapterCategoria(listaCategoria);

        adapterCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TelaCadastroCategoria.class);

                if(listaCategoria != null) {
                    idCategoria = listaCategoria.get(mRecyclerView.getChildAdapterPosition(view)).getIdCategoria();
                }

                Bundle bundle = new Bundle();
                bundle.putInt("idCategoria", idCategoria);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        mRecyclerView.setAdapter(adapterCategoria);

        FloatingActionButton btnAdicionategoria = findViewById(R.id.btnAdicionaCategoria);

        btnAdicionategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cadCat = new Intent(getApplicationContext(), TelaCadastroCategoria.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idCategoria", idCategoria);
                cadCat.putExtras(bundle);
                startActivity(cadCat);

            }
        });

    }

    private void listaCategorias() {
        try{
            DbHelper dbHelper = new DbHelper(getApplicationContext());

            CategoriaDAO catDAO = new CategoriaDAO(dbHelper);

            for(Categoria lst: catDAO.listarCategorias()){
                listaCategoria.add(new Categoria(lst.getIdCategoria(), lst.getDescricaoCategoria()));
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