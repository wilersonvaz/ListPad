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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import model.DbHelper;
import model.ItensLista;
import model.Lista;
import modelDAO.ItensListaDAO;

public class TelaCadastroItemLista extends AppCompatActivity {
    TextView edtDescricaoItemLista;
    Button btnSalvarItemLista;
    int idLista = -1;
    int idItemLista = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_item_lista);
        edtDescricaoItemLista = findViewById(R.id.edtDescricaoItemLista);
        btnSalvarItemLista = findViewById(R.id.btnSalvarItemLista);

        if(getIntent() != null && getIntent().hasExtra("idLista")){
            Bundle bundle = getIntent().getExtras();
            idLista = bundle.getInt("idLista");

            if(getIntent().hasExtra("idItemLista")) {

                DbHelper dbHelper = new DbHelper(getApplicationContext());

                idItemLista = bundle.getInt("idItemLista");

                Lista l = new Lista();
                l.setIdLista(idLista);

                ItensLista itensLista = new ItensLista();
                itensLista.setIdItem(idItemLista);
                itensLista.setLista(l);

                ItensListaDAO itensListaDAO = new ItensListaDAO(dbHelper);
                for (ItensLista lst : itensListaDAO.populaItensLista(itensLista)) {
                    edtDescricaoItemLista.setText(lst.getItemLista());
                }

            }
        }

        btnSalvarItemLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                Lista lista = new Lista();
                lista.setIdLista(idLista);

                ItensLista itensLista = new ItensLista();
                itensLista.setIdItem(idItemLista);
                itensLista.setLista(lista);
                itensLista.setItemLista(edtDescricaoItemLista.getText().toString());

                ItensListaDAO itensListaDAO = new ItensListaDAO(dbHelper);
                if(idItemLista > 0){

                    int retorno = itensListaDAO.updateItemLista(itensLista);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idLista", idLista);
                    bundle.putInt("salvarItemLista", retorno );

                    Intent intent = new Intent(getApplicationContext(), TelaItensLista.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    int retorno = itensListaDAO.adicionaItem(itensLista);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idLista", idLista);
                    bundle.putInt("salvarItemLista", retorno );

                    Intent intent = new Intent(getApplicationContext(), TelaItensLista.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

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