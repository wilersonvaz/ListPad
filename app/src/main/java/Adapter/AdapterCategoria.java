package Adapter;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.listpad.R;
import com.example.listpad.TelaCategoria;

import java.util.ArrayList;

import model.Categoria;
import model.DbHelper;
import modelDAO.CategoriaDAO;

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.ViewCategorias> implements View.OnClickListener {
    ArrayList<Categoria> listaCategoria;
    private View.OnClickListener listener;


    public AdapterCategoria(ArrayList<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    @NonNull
    @Override
    public AdapterCategoria.ViewCategorias onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_categoria, null, false);
        view.setOnClickListener(this);
        return new ViewCategorias(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategoria.ViewCategorias holder, int position) {
        holder.codigoCategoria.setText("Código: "+String.valueOf(listaCategoria.get(position).getIdCategoria()));
        holder.descricaoCategoria.setText(listaCategoria.get(position).getDescricaoCategoria());
        holder.btnExcluirCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    DbHelper dbHelper = new DbHelper(view.getContext());
//                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    Categoria cat = new Categoria();
                    cat.setIdCategoria(listaCategoria.get(position).getIdCategoria());

                    CategoriaDAO catDAO = new CategoriaDAO(dbHelper);
                    Bundle bundle = new Bundle();
                    if(catDAO.excluirCategoria(cat) > 0) {
                        bundle.putInt("excluirCategoria", 1);
                    }else{
                        bundle.putInt("excluirCategoria", 0);
                    }

                    Intent intent = new Intent(view.getContext(), TelaCategoria.class);
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void onClick(View view){
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewCategorias extends RecyclerView.ViewHolder{
        TextView codigoCategoria, descricaoCategoria, btnExcluirCategoria;
//        ImageButton btnExcluirCategoria;

        public ViewCategorias(@NonNull View itemView) {
            super(itemView);
            codigoCategoria = itemView.findViewById(R.id.idCodigoCategoria);
            descricaoCategoria = itemView.findViewById(R.id.descCategoria);
            btnExcluirCategoria = itemView.findViewById(R.id.btnExcluirCategoria);
        }
    }
}


