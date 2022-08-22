package Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listpad.R;
import com.example.listpad.TelaItensLista;

import java.util.ArrayList;

import model.DbHelper;
import model.ItensLista;
import model.Lista;
import modelDAO.ItensListaDAO;

public class AdapterItemLista extends RecyclerView.Adapter<AdapterItemLista.ViewItemLista> implements View.OnClickListener{
    ArrayList<ItensLista> itensLista;
    private View.OnClickListener listener;

    public AdapterItemLista(ArrayList<ItensLista> itensLista) {
        this.itensLista = itensLista;
    }


    @NonNull
    @Override
    public ViewItemLista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mostra_itens_lista, null, false);
        view.setOnClickListener(this);
        return new ViewItemLista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewItemLista holder, int position) {
        holder.idCodigoItemLista.setText("Código: "+String.valueOf(itensLista.get(position).getIdItem()));
        holder.descItemLista.setText(itensLista.get(position).getItemLista());

        if(itensLista.get(position).getFlagFinalizado() > 0){
            holder.idFlagFinalizado.setChecked(true);
        }else{
            holder.idFlagFinalizado.setChecked(false);
        }

        holder.idFlagFinalizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DbHelper dbHelper = new DbHelper(compoundButton.getContext());

                Lista lista= new Lista();
                lista.setIdLista(itensLista.get(position).getLista().getIdLista());

                ItensLista itLista = new ItensLista();
                itLista.setIdItem(itensLista.get(position).getIdItem());

                if(holder.idFlagFinalizado.isChecked()){
                    itLista.setFlagFinalizado(1);
                }else{
                    itLista.setFlagFinalizado(0);
                }

                ItensListaDAO itensListaDAO = new ItensListaDAO(dbHelper);

                int finalizaItem = itensListaDAO.finalizaItem(itLista);
                Bundle bundle = new Bundle();
                bundle.putInt("finalizaItem",finalizaItem);
                bundle.putInt("idLista", itensLista.get(position).getLista().getIdLista());

                Intent intent = new Intent(compoundButton.getContext(), TelaItensLista.class);
                intent.putExtras(bundle);
                compoundButton.getContext().startActivity(intent);
            }
        });

        holder.btnExcluirItemLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    DbHelper dbHelper = new DbHelper(view.getContext());

                    Lista lista = new Lista();
                    lista.setIdLista(itensLista.get(position).getLista().getIdLista());

                    ItensLista itLista = new ItensLista();
                    itLista.setIdItem(itensLista.get(position).getIdItem());
                    itLista.setLista(lista);

                    ItensListaDAO itensListaDAO = new ItensListaDAO(dbHelper);
                    int excluirItem =  itensListaDAO.excluirItem(itLista);
                    Bundle bundle = new Bundle();
                    bundle.putInt("excluirItem", excluirItem);
                    bundle.putInt("idLista", itensLista.get(position).getLista().getIdLista());

                    Intent intent = new Intent(view.getContext(), TelaItensLista.class);
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
        return itensLista.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewItemLista extends RecyclerView.ViewHolder{
        TextView idCodigoItemLista,descItemLista;
        CheckBox idFlagFinalizado;
        ImageButton btnExcluirItemLista;

        public ViewItemLista(@NonNull View itemView) {
            super(itemView);
            idCodigoItemLista = itemView.findViewById(R.id.idCodigoItemLista);
            descItemLista = itemView.findViewById(R.id.descItemLista);
            idFlagFinalizado = itemView.findViewById(R.id.idFlagFinalizado);
            btnExcluirItemLista = itemView.findViewById(R.id.btnExcluirItemLista);
        }
    }
}
