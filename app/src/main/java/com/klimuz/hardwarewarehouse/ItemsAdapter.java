package com.klimuz.hardwarewarehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private ArrayList<Item> items;

    public ItemsAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_item, viewGroup, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int i) {
        Item item = items.get(i);
        itemsViewHolder.textViewItemName.setText(item.getTitle());
        int commonQuantity = item.getQuantity();
        itemsViewHolder.textViewCommonNumber.setText(String.format("%s", commonQuantity));
        int outNumber = item.getWork1quantity() + item.getWork2quantity() + item.getWork3quantity() + item.getWork4quantity()
                + item.getWork5quantity() + item.getWork6quantity() + item.getWork7quantity() + item.getWork8quantity()
                + item.getWork9quantity() + item.getWork10quantity();
        int storeNumber = commonQuantity - outNumber;
        itemsViewHolder.textViewStoreNumber.setText(String.format("%s", storeNumber));
        itemsViewHolder.textViewOutsideNumber.setText(String.format("%s", outNumber));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewItemName;
        private TextView textViewCommonNumber;
        private TextView textViewStoreNumber;
        private TextView textViewOutsideNumber;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewCommonNumber = itemView.findViewById(R.id.textViewCommonNumber);
            textViewStoreNumber = itemView.findViewById(R.id.textViewStoreNumber);
            textViewOutsideNumber = itemView.findViewById(R.id.textViewOutsideNumber);
        }
    }
}
