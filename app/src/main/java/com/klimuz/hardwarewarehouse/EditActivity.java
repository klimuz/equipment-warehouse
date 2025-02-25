package com.klimuz.hardwarewarehouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
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
        goToMain();
    }

    public void buttonOkEditPressed(View view) {
        String newName = editTextEditTitle.getText().toString().trim();
        String newTotalQuantityString = editTextEditQuantity.getText().toString().trim();
        int newTotalQuantityInt = Integer.parseInt(newTotalQuantityString);
        originalEquipment.setName(newName);
        originalEquipment.setTotalQuantity(newTotalQuantityInt);
        Globals.items.set(position, originalEquipment);
        goToMain();
    }

    public void buttonRemoveEditPressed(View view) {
        String areYouSure = getString(R.string.are_you_sure);
        showDialog(areYouSure);
        Globals.items.remove(position);
        goToMain();
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showDialog(String dialogText){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogText)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}