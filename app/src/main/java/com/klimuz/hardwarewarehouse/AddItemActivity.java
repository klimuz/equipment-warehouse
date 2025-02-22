package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText editTextAddTitle;
    private EditText editTextNumberQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextAddTitle = findViewById(R.id.editTextAddTitle);
        editTextNumberQuantity = findViewById(R.id.editTextNumberQuantity);
    }

    public void buttonCancelAddPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonOkAddPressed(View view) {
    }
}