package com.klimuz.hardwarewarehouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddItemActivity extends AppCompatActivity {

    private EditText editTextAddTitle;
    private EditText editTextAddQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextAddTitle = findViewById(R.id.editTextAddTitle);
        editTextAddQuantity = findViewById(R.id.editTextAddQuantity);
    }

    public void buttonCancelAddPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonOkAddPressed(View view) {
        String title = editTextAddTitle.getText().toString().trim();
        String quantity = editTextAddQuantity.getText().toString().trim();
        if (!title.isEmpty() && !quantity.isEmpty()) {
            int digit = Integer.parseInt(quantity);
            Equipment equipment = new Equipment(title, digit);
            if (!Globals.items.contains(equipment)) {
                Globals.items.add(equipment);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String alertAlreadyContains = getString(R.string.list_already_contains_overwrite);
                builder.setMessage(String.format(alertAlreadyContains, title))
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int index = getIndexByName(Globals.items, title);
                                Globals.items.set(index, equipment);
                                Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
        } else {
            String fill = getString(R.string.fill_all_fields);
            Toast.makeText(this, fill, Toast.LENGTH_LONG).show();
        }
    }

    private int getIndexByName(ArrayList<Equipment> itemList, String name) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}