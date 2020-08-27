package com.camila.mypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        chamarPrincipal(currentUser);
    }

    public void efetuarLogin(View view) {
        EditText editText1 = findViewById(R.id.input_email_login);
        EditText editText2 = findViewById(R.id.input_senha_login);
        mAuth.signInWithEmailAndPassword(editText1.getText().toString(), editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            chamarPrincipal(user);
                        } else {
                            Toast.makeText(LoginActivity.this, "Deu erro!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void chamarRecuperarSenha(View view) {
        Intent intent = new Intent(this, RecuperarSenhaActivity.class);
        startActivity(intent);
    }

    private void chamarPrincipal(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void cadastrar(View view) {
        Intent intent = new Intent(this, NovoUsuarioActivity.class);
        startActivity(intent);
    }
}