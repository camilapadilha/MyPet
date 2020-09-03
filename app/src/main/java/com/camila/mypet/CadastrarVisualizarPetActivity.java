package com.camila.mypet;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.camila.mypet.entities.Pet;
import com.camila.mypet.util.Mascara;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CadastrarVisualizarPetActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Pet pet = new Pet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_visualizar_pet);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        EditText dataNascimento = (EditText) findViewById(R.id.input_DataNasc);
        dataNascimento.addTextChangedListener(Mascara.insert(Mascara.FORMAT_DATE, dataNascimento));

        carregarSpinerSexo();
        carregarSpinerTipoPet();
    }

    private void carregarSpinerTipoPet() {
        final Spinner spin = (Spinner) findViewById(R.id.spinnerTipoPet);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spin.getItemAtPosition(position).toString().equals("Escolha o Tipo do seu pet")) {
                    pet.setTipoDePet(spin.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void carregarSpinerSexo() {

        final Spinner spin = (Spinner) findViewById(R.id.spinnerSexo);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spin.getItemAtPosition(position).toString().equals("Escolha o Sexo")) {
                    pet.setSexo(spin.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cadastrar(View view) throws ParseException {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

        EditText nome = (EditText) findViewById(R.id.input_name);
        EditText peso = (EditText) findViewById(R.id.input_peso);
        EditText raca = (EditText) findViewById(R.id.input_raca);
        EditText dataNascimento = (EditText) findViewById(R.id.input_DataNasc);

        pet.setNome(nome.getText().toString());
        if (!String.valueOf(peso.getText()).replace(" ", "").equals("")) {
            pet.setPeso(Double.parseDouble(String.valueOf(peso.getText())));
        }

        pet.setRaca(raca.getText().toString());

        if (!String.valueOf(dataNascimento.getText()).replace(" ", "").equals("")) {
            pet.setDataNascimento(formatoData.parse(String.valueOf(dataNascimento.getText())));
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String chave = databaseReference.child(user.getUid()).child("pet").push().getKey();
        pet.setChave(chave);
        databaseReference.child(user.getUid()).child("pet").child(chave).setValue(pet);

        Toast.makeText(this, "O seu Pet foi armazenada com sucesso!!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void cancelar(View view) {
        finish();
    }
}