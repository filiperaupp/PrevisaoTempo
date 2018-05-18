package com.example.filip.previsaotempo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PrevisaoAdapter extends ArrayAdapter<Previsao> {

    public PrevisaoAdapter (@NonNull Context context, @NonNull List<Previsao> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Previsao previsaoAtual= getItem(position);

        View listPrevisoes = convertView;
        if(convertView==null) {
            listPrevisoes= LayoutInflater.from(getContext()).inflate(R.layout.previsao_item,null);

        }
        TextView txtData= listPrevisoes.findViewById(R.id.txtData);
        TextView txtDescricao= listPrevisoes.findViewById(R.id.txtDescricao);
        TextView txtMin= listPrevisoes.findViewById(R.id.txtMin);
        TextView txtMax= listPrevisoes.findViewById(R.id.txtMax);
        txtData.setText(previsaoAtual.getData());
        txtDescricao.setText(previsaoAtual.getDescricao());
        txtMin.setText(previsaoAtual.getMin());
        txtMax.setText(previsaoAtual.getMax());

        return listPrevisoes;
    }

}
