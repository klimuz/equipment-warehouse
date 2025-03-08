package com.klimuz.hardwarewarehouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        if (!newName.isEmpty()) {
            if (!Globals.items.contains(newName)) {
                String newTotalQuantityString = editTextEditQuantity.getText().toString().trim();
                if (!newTotalQuantityString.isEmpty()) {
                    int newTotalQuantityInt = Integer.parseInt(newTotalQuantityString);
                    originalEquipment.setName(newName);
                    originalEquipment.setTotalQuantity(newTotalQuantityInt);
                    Globals.items.set(position, originalEquipment);
                    goToMain();
                } else {
                    String fillAllFields = getString(R.string.fill_all_fields);
                    Toast.makeText(this, fillAllFields, Toast.LENGTH_LONG).show();
                }
            } else {
                String alert = String.format(getString(R.string.list_already_contains_s_overwrite), newName);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(alert)
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String newTotalQuantityString = editTextEditQuantity.getText().toString().trim();
                                if (!newTotalQuantityString.isEmpty()) {
                                    int newTotalQuantityInt = Integer.parseInt(newTotalQuantityString);
                                    originalEquipment.setName(newName);
                                    originalEquipment.setTotalQuantity(newTotalQuantityInt);
                                    Globals.items.set(position, originalEquipment);
                                    goToMain();
                                } else {
                                    String fillAllFields = getString(R.string.fill_all_fields);
                                    Toast.makeText(EditActivity.this, fillAllFields, Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }

    }

    public void buttonRemoveEditPressed(View view) {
        String areYouSure = getString(R.string.are_you_sure);
        removeShowDialog(areYouSure);
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void removeShowDialog(String dialogText){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogText)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Globals.removeEquipment(position,EditActivity.this);
                        goToMain();
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

    private void containshowDialog(String dialogText){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogText)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Globals.removeEquipment(position,EditActivity.this);
                        goToMain();
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