package com.example.b07_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_project.Model.ItemData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OP5Adapter extends RecyclerView.Adapter<OP5Adapter.OP5ViewHolder> {
    private ArrayList<ItemData> items_list;

    public static class OP5ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;
        public TextView quantity;

        public OP5ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.OP5_item_name);
            quantity = itemView.findViewById(R.id.OP5_quantity);
        }
    }

    public OP5Adapter(List<ItemData> items_list) { this.items_list = (ArrayList<ItemData>) items_list; }

    @NonNull
    @Override
    public OP5ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_item_ordered_card, parent, false);
        return new OP5ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OP5ViewHolder holder, int position) {
        ItemData current_item = items_list.get(position);
        holder.item_name.setText(current_item.getData().getName());
        holder.quantity.setText(String.valueOf(current_item.getQuantity()));
    }

    @Override
    public int getItemCount() { return items_list.size(); }

}
