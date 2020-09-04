package com.camila.mypet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.camila.mypet.entities.Lembrete;
import com.camila.mypet.entities.Pet;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListaPetsAdapter extends BaseAdapter {

    Context context;
    List<Pet> pets;

    public ListaPetsAdapter(Context context, List<Pet> pets) {
        this.context = context;
        this.pets = pets;
    }

    @Override
    public int getCount() {
        return pets.size();
    }

    @Override
    public Object getItem(int position) {
        return pets.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewLinha = LayoutInflater.from(context).inflate(R.layout.linha_pet, parent, false);
        Pet pet = pets.get(position);
        TextView textView = viewLinha.findViewById(R.id.textTituloLinhaPet);
        textView.setText(pet.getNome());

        if (pet.getFotoPet() != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = BitmapFactory.decodeFile(pet.getFotoPet(), options);
            System.out.println("222222 "+ bitmap);

            ImageView imageView1 = (ImageView) viewLinha.findViewById(R.id.imageViewVerPet);

            System.out.println("33333 "+ imageView1);

            imageView1.setBackground(null);
            imageView1.setImageBitmap(bitmap);
        }
        return viewLinha;
    }

}
