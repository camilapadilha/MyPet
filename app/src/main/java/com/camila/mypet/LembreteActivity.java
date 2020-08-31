package com.camila.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class LembreteActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembrete);
        mAuth = FirebaseAuth.getInstance();
    }


    public void chamarCadastrarLembrete(View view) {
        Intent itt = new Intent(this, CadastrarEditarLembreteActivity.class);
        startActivity(itt);
    }
}