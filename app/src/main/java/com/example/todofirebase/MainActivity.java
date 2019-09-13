package com.example.todofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.todofirebase.adapter.TarefaAdapter;
import com.example.todofirebase.modelo.Tarefa;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //Banco
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //Objetos
    private EditText editTextNome;
    private List<Tarefa> tarefas = new ArrayList<Tarefa>();
    private ArrayAdapter<Tarefa> arrayAdapterTarefa;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.edit_text_nome);
        listView = findViewById(R.id.list_view);

        conectarBanco();
        eventoBanco();
    }

    private void conectarBanco(){
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //Faz a leitura do Banco de dados
    private void eventoBanco(){
        databaseReference.child("tarefa").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tarefas.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Tarefa tarefa = snapshot.getValue(Tarefa.class);
                    tarefas.add(tarefa);
                }

                arrayAdapterTarefa = new TarefaAdapter(MainActivity.this, (ArrayList<Tarefa>) tarefas);
                listView.setAdapter(arrayAdapterTarefa);

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                        excluirDado(tarefas.get(i));

                        /*databaseReference.child("tarefa")
                                .child(tarefas.get(i).getUuid()).
                                removeValue();*/

                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void eventoBanco2CliqueSimples(){
        databaseReference.child("tarefa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Tarefa tarefa = snapshot.getValue(Tarefa.class);
                    tarefas.add(tarefa);
                }

                arrayAdapterTarefa = new TarefaAdapter(MainActivity.this, (ArrayList<Tarefa>) tarefas);
                listView.setAdapter(arrayAdapterTarefa);

                //Aqui o valor precisa ser booleano//
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        concluirTarefa(tarefas.get(i));

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Salvar objeto no banco
    public void salvarDado(View v){
        Tarefa tarefa = new Tarefa(UUID.randomUUID().toString(),
                editTextNome.getText().toString());
        databaseReference.child("tarefa").
                child(tarefa.getUuid()).
                setValue(tarefa);



    }

    public void excluirDado (final Tarefa tarefa) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Você deseja remover esta tarefa?");
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child("tarefa").
                        child(tarefa.getUuid()).
                        removeValue();

            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void concluirTarefa(final Tarefa tarefa){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Você deseja concluir esta tarefa?");
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                tarefa.setStatus(true);

                databaseReference.child("tarefa").
                        child(tarefa.getUuid()).
                        setValue(tarefa);
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
