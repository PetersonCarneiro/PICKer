package com.br.picker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private List<Item> list;
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewPlaqueta, textViewTipo,textViewSituacao,textViewLocalizacao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlaqueta = itemView.findViewById(R.id.textViewPlaqueta);
            textViewTipo = itemView.findViewById(R.id.textViewType);
            textViewLocalizacao = itemView.findViewById(R.id.textViewLocale);
            textViewSituacao = itemView.findViewById(R.id.textViewStatus);

        }
    }
    public ItemAdapter(List<Item> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter,parent,false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, int position) {
        Item item = list.get(position);

        holder.textViewPlaqueta.setText(String.valueOf(item.getPlaqueta()));
        holder.textViewTipo.setText(item.getTipo());
        holder.textViewLocalizacao.setText(item.getLocalizacao());
        holder.textViewSituacao.setText(item.getSituacao());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
