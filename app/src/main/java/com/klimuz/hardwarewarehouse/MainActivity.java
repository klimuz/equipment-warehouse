package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItems;
    private EquipmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewItems = findViewById(R.id.recyclerViewItems);

        if (Globals.items.isEmpty()) {
            Globals.arrayListsFromDB(this);
        }

        Log.i("myLog", String.valueOf(Globals.items.size()));

        SharedPreferences preferences = getSharedPreferences("RecyclerViewState", MODE_PRIVATE);
        if (preferences != null) {
            int scrollPosition = preferences.getInt("scrollPosition", 0);

            adapter = new EquipmentAdapter(Globals.items);
            recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewItems.setAdapter(adapter);
            recyclerViewItems.scrollToPosition(scrollPosition);
            adapter.setOnEquipmentClickListener(new EquipmentAdapter.OnEquipmentClickListener() {
                @Override
                public void onEquipmentClick(int position) {

                }

                @Override
                public void onLongClick(int position) {
                    openEditActivity(position);
                }
            });

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    switch (direction) {
                        case 4:
                            openIssueActivity(viewHolder.getAdapterPosition());
                            break;
                        case 8:
                            openReturnActivity(viewHolder.getAdapterPosition());
                            break;
                    }
                }
            });
            itemTouchHelper.attachToRecyclerView(recyclerViewItems);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerViewItems.getLayoutManager();
        assert layoutManager != null;
        int scrollPosition = layoutManager.findFirstVisibleItemPosition();
        SharedPreferences preferences = getSharedPreferences("RecyclerViewState", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("scrollPosition", scrollPosition);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("RecyclerViewState", MODE_PRIVATE);
        int scrollPosition = preferences.getInt("scrollPosition", 0);
        recyclerViewItems.scrollToPosition(scrollPosition);
        Globals.arraylistsToDB(this);

    }

    private void openEditActivity(int position) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void openIssueActivity(int position) {
        Intent intent = new Intent(this, IssueActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void openReturnActivity(int position) {
        if (Globals.items.get(position).getInUse() != 0) {
            Intent intent = new Intent(this, ReturnActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        } else {
            String nothingToReturn = getString(R.string.nothing_to_return);
            Toast.makeText(this, nothingToReturn, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void buttonAddPressed(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    public void buttonSavePressed(View view) {
        Globals.arraylistsToDB(this);
        if (Globals.jobs.isEmpty()) {
            String nothingToSave = getString(R.string.nothing_to_save);
            Toast.makeText(this, nothingToSave, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, SaveActivity.class);
            startActivity(intent);
        }
    }

    public void buttonDeletePressed(View view) {
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.resetDatabase();
        Globals.items.clear();
        Globals.jobs.clear();
        adapter.notifyDataSetChanged();
    }
}