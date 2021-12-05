package com.example.b07_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_project.Model.OrderData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OP4Adapter extends RecyclerView.Adapter<OP4Adapter.OP4ViewHolder> {
    private ArrayList<OrderData> orders_list;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class OP4ViewHolder extends RecyclerView.ViewHolder {
        public TextView order_id;

        public OP4ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            order_id = itemView.findViewById(R.id.OP4_order_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public OP4Adapter(ArrayList<OrderData> orders_list) { this.orders_list = orders_list; }

    @NonNull
    @Override
    public OP4ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_order_card, parent, false);
        return new OP4ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OP4ViewHolder holder, int position) {
        OrderData current_order = orders_list.get(position);
        holder.order_id.setText(current_order.GetOrderID());
    }

    @Override
    public int getItemCount() { return orders_list.size(); }

    public void filterList(ArrayList<OrderData> filtered_list) {
        orders_list = filtered_list;
        notifyDataSetChanged();
    }
}