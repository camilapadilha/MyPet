package com.camila.mypet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.camila.mypet.entities.Pet;
import com.camila.mypet.util.Mascara;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CadastrarVisualizarPetActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Pet pet = new Pet();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String currentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 105;

    ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_visualizar_pet);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        EditText dataNascimento = (EditText) findViewById(R.id.input_DataNasc);
        dataNascimento.addTextChangedListener(Mascara.insert(Mascara.FORMAT_DATE, dataNascimento));

        imagem = (ImageView) findViewById(R.id.fotoPet);

        imagem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imagem.setImageBitmap(null);
                imagem.setBackground(getResources().getDrawable(R.drawable.ic_camera_pet));
                pet.setFotoPet("");
                return true;
            }
        });

        carregarSpinerSexo();
        carregarSpinerTipoPet();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("CHAVE_PET")) {
            try {
                carregarDados(bundle);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void carregarDados(Bundle bundle) throws ParseException {
        final String chave = bundle.getString("CHAVE_PET");
        System.out.println("chave->" + chave);
        databaseReference.child(user.getUid()).child("pet").child(chave).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pet = dataSnapshot.getValue(Pet.class);
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

    private void preencherDados() throws ParseException {

        if (pet.getFotoPet() != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = stringToBitMap(pet.getFotoPet());

            ImageView imageView1 = (ImageView) findViewById(R.id.fotoPet);

            imageView1.setBackground(null);
            imageView1.setImageBitmap(bitmap);
        }

        EditText editText = (EditText) findViewById(R.id.input_name);
        editText.setText(pet.getNome());

        EditText editTextPeso = (EditText) findViewById(R.id.input_peso);
        editTextPeso.setText(pet.getPeso().toString());

        Spinner spinTipoPet = (Spinner) findViewById(R.id.spinnerTipoPet);
        List<String> tipoPet = new ArrayList<>(Arrays.asList("Escolha o Tipo do seu pet", "Cachorro", "Gato", "Coelho", "Pássaro", "Roedor", "Outro"));

        for (int i = 0; i <= tipoPet.size(); i++) {
            if (tipoPet.get(i).equals(pet.getTipoDePet())) {
                spinTipoPet.setSelection(i);
                break;
            }
        }

        Spinner spinSexo = (Spinner) findViewById(R.id.spinnerSexo);
        List<String> sexo = new ArrayList<>(Arrays.asList("Escolha o Sexo", "Fêmea", "Macho"));
        for (int i = 0; i <= sexo.size(); i++) {
            if (sexo.get(i).equals(pet.getSexo())) {
                spinSexo.setSelection(i);
                break;
            }
        }

        EditText editTextRaca = (EditText) findViewById(R.id.input_raca);
        editTextRaca.setText(pet.getRaca());

        EditText editTextData = (EditText) findViewById(R.id.input_DataNasc);
        editTextData.setText(convertedata(pet.getDataNascimento()));

    }


    public String convertedata(Date vdata) throws ParseException {
        Date data = vdata;
        String formato = "dd/MM/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        System.out.println("A data formatada é: " + formatter.format(data));

        return formatter.format(data);
    }

    public Bitmap stringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
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
        String chave = this.pet.getChave() == null ? databaseReference.child(user.getUid()).child("pet").push().getKey() : this.pet.getChave();
        pet.setChave(chave);
        databaseReference.child(user.getUid()).child("pet").child(chave).setValue(pet);

        Toast.makeText(this, "O seu Pet foi armazenada com sucesso!!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void cancelar(View view) {
        finish();
    }

    public void chamarCameraPet(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException
                    ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.camila.mypet",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, options);

                //Converter o bitmap em Base64 (string), que é útil para mandar a foto para um WS.
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

                byte[] binario = outputStream.toByteArray();
                String fotoString = Base64.encodeToString(binario, Base64.DEFAULT);

                Log.i("CadastrarVisualizarPet", "" + fotoString.length());
                imagem.setImageBitmap(bitmap);
                imagem.setBackground(null);
                pet.setFotoPet(fotoString);
            } catch (Exception i) {
                Log.e("CadastrarVisualizarPet", "Deu erro!!!");
            }
        }
    }

}