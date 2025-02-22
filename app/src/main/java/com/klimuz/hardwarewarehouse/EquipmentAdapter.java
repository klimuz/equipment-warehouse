package com.klimuz.hardwarewarehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {

    private ArrayList<Equipment> equipmentList;

    public EquipmentAdapter(ArrayList<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_equipment, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Equipment equipment = equipmentList.get(position);
        holder.textViewName.setText(equipment.getName());
        holder.textViewTotalQuantity.setText(String.format("%s", equipment.getTotalQuantity()));
        holder.textViewInStock.setText(String.format("%s", equipment.getInStock()));
        holder.textViewInUse.setText(String.format("%s", equipment.getInUse()));
    }

    @Override
    public int getItemCount() {
        return equipmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textViewTotalQuantity, textViewInStock, textViewInUse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewTotalQuantity = itemView.findViewById(R.id.textViewTotalQuantity);
            textViewInStock = itemView.findViewById(R.id.textViewInStock);
            textViewInUse = itemView.findViewById(R.id.textViewInUse);
        }
    }

}
