package com.camila.mypet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

    }

    public void chamarCadastrarPet(View view) {
        Intent itt = new Intent(this, CadastrarVisualizarPetActivity.class);
        startActivity(itt);
    }

    public void chamarVerPet(View view) {
        Intent itt = new Intent(this, VerPetActivity.class);
        startActivity(itt);
    }

    public void chamarLembretes(View view) {
        Intent itt = new Intent(this, LembreteActivity.class);
        startActivity(itt);
    }

    public void sair(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja sair?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                finish();
            }
        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}