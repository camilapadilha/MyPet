package com.camila.mypet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.camila.mypet.entities.Lembrete;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListaLembretesAdapter extends BaseAdapter {

    Context context;
    List<Lembrete> lembretes;

    public ListaLembretesAdapter(Context context, List<Lembrete> lembretes) {
        this.context = context;
        this.lembretes = lembretes;
    }

    @Override
    public int getCount() {
        return lembretes.size();
    }

    @Override
    public Object getItem(int position) {
        return lembretes.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewLinha = LayoutInflater.from(context).inflate(R.layout.linha_lembrete, parent, false);
        Lembrete lembrete = lembretes.get(position);
        TextView textView = viewLinha.findViewById(R.id.textTituloLinha);
        textView.setText(lembrete.getNome());

        TextView textViewData = viewLinha.findViewById(R.id.textDataLembrete);

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        textViewData.setText(formatoData.format(lembrete.getData()));

        return viewLinha;
    }

}
