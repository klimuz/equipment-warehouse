package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private EditText editTextEditTitle;
    private EditText editTextEditQuantity;
    private int position;
    private String originalName;
    private int originalTotalQuantity;
    Equipment originalEquipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextEditTitle = findViewById(R.id.editTextEditTitle);
        editTextEditQuantity = findViewById(R.id.editTextEditQuantity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            position = bundle.getInt("position");
            originalEquipment = Globals.items.get(position);
            originalName = originalEquipment.getName();
            originalTotalQuantity = originalEquipment.getTotalQuantity();
            editTextEditTitle.setText(originalName);
            editTextEditQuantity.setText(String.valueOf(originalTotalQuantity));
        }
    }

    public void buttonCancelEditPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonOkEditPressed(View view) {
        String newName = editTextEditTitle.getText().toString().trim();
        String newTotalQuantityString = editTextEditQuantity.getText().toString().trim();
        int newTotalQuantityInt = Integer.parseInt(newTotalQuantityString);
        originalEquipment.setName(newName);
        originalEquipment.setTotalQuantity(newTotalQuantityInt);
        Globals.items.set(position, originalEquipment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonRemoveEditPressed(View view) {
        Globals.items.remove(position);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}