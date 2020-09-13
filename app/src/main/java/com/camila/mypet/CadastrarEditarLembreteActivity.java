package com.camila.mypet;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.camila.mypet.entities.Lembrete;
import com.camila.mypet.util.Mascara;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CadastrarEditarLembreteActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Lembrete lembrete = new Lembrete();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_editar_lembrete);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        EditText data = (EditText) findViewById(R.id.input_Data);
        EditText horario = (EditText) findViewById(R.id.input_horario);
        data.addTextChangedListener(Mascara.insert(Mascara.FORMAT_DATE, data));
        horario.addTextChangedListener(Mascara.insert(Mascara.FORMAT_HOUR, horario));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("CHAVE_LEMBRETE")) {
            try {
                carregarDados(bundle);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    private void preencherDados() throws ParseException {
        System.out.println("LEMBRETE NOME 2 -----> " + lembrete.getNome());

        EditText editText = (EditText) findViewById(R.id.input_name);
        editText.setText(lembrete.getNome());

        EditText editTextData = (EditText) findViewById(R.id.input_Data);
        editTextData.setText(convertedata(lembrete.getData()));

        EditText editTextHorario = (EditText) findViewById(R.id.input_horario);
        editTextHorario.setText(converteHorario(lembrete.getData()));

        EditText editTextComentario = (EditText) findViewById(R.id.input_comentario);
        editTextComentario.setText(lembrete.getComentario());

    }

    public String convertedata(Date vdata) throws ParseException {
        Date data = vdata;
        String formato = "dd/MM/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        System.out.println("A data formatada é: " + formatter.format(data));

        return formatter.format(data);
    }

    public String converteHorario(Date vdata) throws ParseException {
        Date data = vdata;
        String formato = "HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        System.out.println("A data formatada é: " + formatter.format(data));
        return formatter.format(data);
    }

    private void carregarDados(Bundle bundle) throws ParseException {
        final String chave = bundle.getString("CHAVE_LEMBRETE");
        databaseReference.child(user.getUid()).child("lembrete").child(chave).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lembrete = dataSnapshot.getValue(Lembrete.class);
                try {
                    preencherDados();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

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
        String chave = this.lembrete.getChave() == null ? databaseReference.child(user.getUid()).child("lembrete").push().getKey() : this.lembrete.getChave();
        lembrete.setChave(chave);
        databaseReference.child(user.getUid()).child("lembrete").child(chave).setValue(lembrete);

        Toast.makeText(this, "O seu Lembrete foi armazenada com sucesso!!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void cancelar(View view) {
        finish();
    }

    public void vozTextoComentario(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Qual o comentário?");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Log.e("CadastrarEditarLembrete", "Activity não encontrada");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    EditText editText = (EditText) findViewById(R.id.input_comentario);
                    editText.setText(resultado.get(0));
                }
                break;
            }
        }
    }
}