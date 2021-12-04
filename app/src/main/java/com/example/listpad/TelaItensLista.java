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

import Adapter.AdapterItemLista;
import model.DbHelper;
import model.ItensLista;
import modelDAO.ItensListaDAO;

public class TelaItensLista extends AppCompatActivity {
    private int idLista = -1;
    private int idItemLista = -1;
    TextView idListaItens;
    ArrayList<ItensLista> itensLista ;
    private android.util.Log Log;
    RecyclerView idRecyclerMostraItens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_lista);
        idListaItens = findViewById(R.id.idItensLista);
        idRecyclerMostraItens= findViewById(R.id.idRecyclerMostraItens);
        itensLista = new ArrayList<>();

        if(getIntent() != null) {
            Bundle bundle = getIntent().getExtras();

            if (getIntent().hasExtra("idLista")) {
                idLista = bundle.getInt("idLista");
            }

            if(getIntent().hasExtra("salvarItemLista")){

                int salvarItemLista = bundle.getInt("salvarItemLista");

                if(salvarItemLista > 0){
                    Snackbar.make(findViewById(android.R.id.content) , "Item salvo com sucesso!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(findViewById(android.R.id.content) , "Ocorreu um erro ao salvar o item!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            if(getIntent().hasExtra("excluirItem")){

                int excluirItem = bundle.getInt("excluirItem");

                if(excluirItem > 0){
                    Snackbar.make(findViewById(android.R.id.content) , "Item excluído com sucesso!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(findViewById(android.R.id.content) , "Ocorreu um erro ao excluir o item!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            if(getIntent().hasExtra("finalizaItem")){
                int finalizaItem = bundle.getInt("finalizaItem");

                if(finalizaItem > 0){
                    Snackbar.make(findViewById(android.R.id.content) , "Item finalizado!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(findViewById(android.R.id.content) , "Ocorreu um erro ao finalizar o item!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        }

        idRecyclerMostraItens.setLayoutManager(new LinearLayoutManager(this));

        listaItens();

        AdapterItemLista adapterItemLista = new AdapterItemLista(itensLista);

        adapterItemLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TelaCadastroItemLista.class);

                if(itensLista != null){
                    idItemLista = itensLista.get(idRecyclerMostraItens.getChildAdapterPosition(view)).getIdItem();
                }

                Bundle bundle = new Bundle();
                bundle.putInt("idItemLista", idItemLista);
                bundle.putInt("idLista", idLista);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        idRecyclerMostraItens.setAdapter(adapterItemLista);

        FloatingActionButton idAdicionaItemLista = findViewById(R.id.idAdicionaItemLista);
        idAdicionaItemLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idLista", idLista);
                Intent intent = new Intent(getApplicationContext(), TelaCadastroItemLista.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void listaItens() {
        try{
            DbHelper dbHelper = new DbHelper(getApplicationContext());

            ItensListaDAO itensListaDAO = new ItensListaDAO(dbHelper);
            ItensLista itLista = new ItensLista();
            itLista.setIdItem(idLista);

            for(ItensLista lst : itensListaDAO.listaItens(itLista)){
                idListaItens.setText(lst.getLista().getDescricaoLista());
                itensLista.add(new ItensLista(lst.getIdItem(), lst.getLista(), lst.getItemLista(), lst.getFlagFinalizado()));
//                itensLista.ad
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