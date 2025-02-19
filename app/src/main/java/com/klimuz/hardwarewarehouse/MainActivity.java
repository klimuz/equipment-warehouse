package com.klimuz.hardwarewarehouse;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItems;
    private ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        items.add(new Item("console x32", 3, "", "", "", "",
                "", "", "", "", "", "",
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0));
        items.add(new Item("shureSM58", 20, "", "", "", "",
                "", "", "", "", "", "",
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0));
        items.add(new Item("cableXLR", 100, "", "", "", "",
                "", "", "", "", "", "",
                60, 0, 0, 0, 0, 0,
                0, 0, 0, 0));
        items.add(new Item("console CL5", 2, "Yulduz DDN", "", "", "",
                "", "", "", "", "", "",
                1, 0, 0, 0, 0, 0,
                0, 0, 0, 0));
        ItemsAdapter adapter = new ItemsAdapter(items);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(adapter);
    }

    public void buttonAddPressed(View view) {
    }

    public void buttonUndoPressed(View view) {
    }
}