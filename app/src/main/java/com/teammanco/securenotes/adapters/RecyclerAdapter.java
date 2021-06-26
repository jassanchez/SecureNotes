package com.teammanco.securenotes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.teammanco.securenotes.R;
import com.teammanco.securenotes.model.ItemList;
import com.teammanco.securenotes.model.Note;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>
            implements View.OnClickListener {
    private List<ItemList> items;
    private View.OnClickListener listener;

    public RecyclerAdapter(List<ItemList> items) {
        this.items = items;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view,parent,false);
        v.setOnClickListener(this);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerHolder holder, int position) {
        ItemList item = items.get(position);
        Note note = item.getNote();
        holder.txtTitulo.setText(note.getTitle());
        if(note.getSecurity() == 1){
            holder.txtTitulo.setTextColor(ContextCompat.getColor(holder.txtTitulo.getContext(), R.color.rosa_base));
            holder.txtDesc.setTextColor(ContextCompat.getColor(holder.txtDesc.getContext(), R.color.rosa_bajito));
            holder.txtDesc.setText("******PRIVATE CONTENT*****");
            holder.img.setImageResource(R.drawable.ic_lock_black_24dp);
            holder.img.setColorFilter(ContextCompat.getColor(holder.img.getContext(), R.color.rosa_bajito));
        }
        else{
            holder.txtTitulo.setTextColor(ContextCompat.getColor(holder.txtTitulo.getContext(), R.color.disabled_color));
            holder.txtDesc.setTextColor(ContextCompat.getColor(holder.txtDesc.getContext(), R.color.azul_base));
            holder.img.setImageResource(R.drawable.ic_note_icon2);
            holder.txtDesc.setText(note.getContent());
            holder.img.setColorFilter(ContextCompat.getColor(holder.img.getContext(), R.color.azul_base));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView txtTitulo;
        private TextView txtDesc;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgItem);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }
}
