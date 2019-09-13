package com.example.todofirebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todofirebase.R;
import com.example.todofirebase.modelo.Tarefa;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TarefaAdapter extends ArrayAdapter<Tarefa> {

    private Context context;
    private List<Tarefa> tarefas;

    public TarefaAdapter(Context context, ArrayList<Tarefa> tarefas){

        super(context, 0, tarefas);
        this.context = context;
        this.tarefas = tarefas;
    }


    //Sobrescreve o método herdado pelo pai
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listaItem = convertView;

        //Inicializando o layout_list na ListView
        if (listaItem == null){
            listaItem = LayoutInflater.from(context)
                    .inflate(R.layout.layout_lista, parent, false);
        }

        //Vai executar cada elemento da lista
        Tarefa tarefaAtual = tarefas.get(position);

        TextView nomeTarefa = listaItem. findViewById(R.id.text_view_nome_tarefa);
        nomeTarefa.setText(tarefaAtual.getNome());

        TextView statusTarefa = listaItem.findViewById(R.id.text_view_status_tarefa);
        statusTarefa.setText("Não concluída");

        ImageView imagemTarefa = listaItem.findViewById(R.id.image_view_tarefa);
        Picasso.get()
                .load(tarefaAtual.getImageScr())
                .resize(70, 70)
                .centerCrop()
                .into(imagemTarefa);

        return listaItem;
    }
}
