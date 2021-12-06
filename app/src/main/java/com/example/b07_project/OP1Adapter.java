package com.example.b07_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_project.Model.ItemDescriptionData;

import java.util.ArrayList;
import java.util.List;


public class OP1Adapter extends RecyclerView.Adapter<OP1Adapter.OP1ViewHolder> {
    private List<ItemDescriptionData> items_list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class OP1ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;
        public TextView item_brand;
        public TextView item_price;

        public OP1ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            item_name = itemView.findViewById(R.id.shop_item_name);
            item_brand = itemView.findViewById(R.id.shop_item_brand);
            item_price = itemView.findViewById(R.id.shop_item_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public OP1Adapter(List<ItemDescriptionData> items_list) {
        this.items_list = items_list;
    }

    @NonNull
    @Override
    public OP1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_item_card, parent, false);
        return new OP1ViewHolder(v, listener);

    }

    @Override
    public void onBindViewHolder(@NonNull OP1ViewHolder holder, int position) {
        ItemDescriptionData current_item = items_list.get(position);

        holder.item_name.setText(current_item.getName());
        holder.item_brand.setText(current_item.getBrand());
        holder.item_price.setText(String.valueOf(current_item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    public void filterList(ArrayList<ItemDescriptionData> filtered_list) {
        items_list = filtered_list;
        notifyDataSetChanged();
    }
}

