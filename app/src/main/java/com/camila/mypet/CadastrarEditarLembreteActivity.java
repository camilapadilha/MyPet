package com.camila.mypet;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.camila.mypet.entities.Lembrete;
import com.camila.mypet.util.Mascara;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CadastrarEditarLembreteActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_editar_lembrete);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        EditText data = (EditText) findViewById(R.id.input_Data);
        EditText horario = (EditText) findViewById(R.id.input_horario);
        data.addTextChangedListener(Mascara.insert(Mascara.FORMAT_DATE, data));
        horario.addTextChangedListener(Mascara.insert(Mascara.FORMAT_HOUR, horario));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cadastrar(View view) throws ParseException {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        EditText nome = (EditText) findViewById(R.id.input_name);
        EditText data = (EditText) findViewById(R.id.input_Data);
        EditText hora = (EditText) findViewById(R.id.input_horario);
        EditText comentario = (EditText) findViewById(R.id.input_comentario);

        String horario = data.getText() + " " + hora.getText();

        Lembrete lembrete = new Lembrete();
        lembrete.setNome(nome.getText().toString());
        lembrete.setData(formatoData.parse(horario));
        lembrete.setComentario(comentario.getText().toString());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String chave = databaseReference.child(user.getUid()).child("lembrete").push().getKey();
        lembrete.setChave(chave);
        databaseReference.child(user.getUid()).child("lembrete").child(chave).setValue(lembrete);

        Toast.makeText(this, "O seu Lembrete foi armazenada com sucesso!!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void cancelar(View view) {
        finish();
    }
}