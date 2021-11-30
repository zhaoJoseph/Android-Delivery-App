package com.example.b07_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import AppClasses.Order;

public class OP4Adapter extends RecyclerView.Adapter<OP4Adapter.OP4ViewHolder> {
    private ArrayList<Order> orders_list;


    public static class OP4ViewHolder extends RecyclerView.ViewHolder {
        public TextView order_id;

        public OP4ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.OP4_order_id);
        }
    }

    public OP4Adapter(ArrayList<Order> orders_list) { this.orders_list = orders_list; }

    @NonNull
    @Override
    public OP4ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_order_card, parent, false);
        return new OP4ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OP4ViewHolder holder, int position) {
        Order current_order = orders_list.get(position);

        holder.order_id.setText(String.valueOf(current_order.getID()));
    }

    @Override
    public int getItemCount() { return orders_list.size(); }
}
