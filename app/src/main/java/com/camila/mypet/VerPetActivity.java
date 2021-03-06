package com.camila.mypet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.camila.mypet.entities.Pet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VerPetActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;
    ListView listView;
    private List<Pet> pets = new ArrayList<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pet);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.listPets);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        carregarLista();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void carregarLista() {
        databaseReference.child(user.getUid()).child("pet")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                pets.add(snapshot.getValue(Pet.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        ListaPetsAdapter adapter = new ListaPetsAdapter(this, pets);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Pet pet = (Pet) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, CadastrarVisualizarPetActivity.class);
        intent.putExtra("CHAVE_PET", pet.getChave());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Pet pet = (Pet) parent.getItemAtPosition(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja excluir este Pet?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //remover
                databaseReference.child(user.getUid()).child("pet").child(pet.getChave()).removeValue();
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

    public void chamarCadastrarPet(View view) {
        Intent itt = new Intent(this, CadastrarVisualizarPetActivity.class);
        startActivity(itt);
    }
}