package com.teammanco.securenotes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        holder.img.setImageResource(R.drawable.icon_note);
        holder.txtTitulo.setText(note.getTitle());
        if(note.getSecurity() == 1)
            holder.txtDesc.setText("******PRIVATE CONTENT*****");
        else
            holder.txtDesc.setText(note.getContent());
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
