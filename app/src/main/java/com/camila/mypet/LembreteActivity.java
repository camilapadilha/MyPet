package com.camila.mypet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.camila.mypet.entities.Lembrete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LembreteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;
    ListView listView;
    private List<Lembrete> lembretes = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembrete);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.listLembretes);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        carregarLista();

    }

    public void chamarCadastrarLembrete(View view) {
        Intent itt = new Intent(this, CadastrarEditarLembreteActivity.class);
        startActivity(itt);
    }

    public void carregarLista() {
        System.out.println("Entrou " + user.getUid());
        databaseReference.child(user.getUid()).child("lembrete")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                lembretes.add(snapshot.getValue(Lembrete.class));
                            }
                        }
                        System.out.println("Carregou lista");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        ListaLembretesAdapter adapter = new ListaLembretesAdapter(this, lembretes);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Lembrete lembrete = (Lembrete) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, CadastrarEditarLembreteActivity.class);
        intent.putExtra("CHAVE_LEMBRETE", lembrete.getChave());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Lembrete lembrete = (Lembrete) parent.getItemAtPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja excluir este lembrete?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //remover
                databaseReference.child(user.getUid()).child("lembrete").child(lembrete.getChave()).removeValue();
                carregarLista();
            }
        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

        return true;
    }
}