package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listpad.R;

import java.util.ArrayList;

import model.Lista;

public class AdapterLista extends RecyclerView.Adapter<AdapterLista.ViewLista> implements View.OnClickListener{
    ArrayList<Lista> lista;
    private View.OnClickListener listener;

    public AdapterLista(ArrayList<Lista> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewLista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lista_tarefa, null, false);
        view.setOnClickListener(this);
        return new ViewLista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewLista holder, int position) {
        holder.idLista.setText(String.valueOf(lista.get(position).getIdLista()));
        holder.idDescricaoLista.setText(lista.get(position).getDescricaoLista());
        holder.idDescricaoCategoriaLista.setText(lista.get(position).getCategoria().getDescricaoCategoria());
        String urgente = "";
        if (lista.get(position).getFlagUrgencia() > 0) {
            urgente = "Urgência alta";
        }else{
            urgente = "Urgência baixa";
        }
        holder.idFlagUrgente.setText(urgente);

    }

    @Override
    public int getItemCount() {
        return lista.size();
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

    public class ViewLista extends RecyclerView.ViewHolder{
        TextView idLista, idDescricaoLista, idDescricaoCategoriaLista, idFlagUrgente, idVerDetalhesLista, idExcluirDetalhesLista;

        public ViewLista(@NonNull View itemView) {
            super(itemView);
            idLista = itemView.findViewById(R.id.idLista);
            idDescricaoLista = itemView.findViewById(R.id.idDescricaoLista);
            idDescricaoCategoriaLista = itemView.findViewById(R.id.idDescricaoCategoriaLista);
            idFlagUrgente = itemView.findViewById(R.id.idFlagUrgente);
            idVerDetalhesLista = itemView.findViewById(R.id.idVerDetalhesLista);
            idExcluirDetalhesLista = itemView.findViewById(R.id.idExcluirDetalhesLista);
        }
    }
}
