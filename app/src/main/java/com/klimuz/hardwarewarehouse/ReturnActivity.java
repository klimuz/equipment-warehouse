package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReturnActivity extends AppCompatActivity {

    private EditText editTextReturnQuantity;
    private int itemPosition;
    private int selectedJobIndex = 0;
    private int returnQuantity;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        TextView textViewReturnName = findViewById(R.id.textViewReturnName);
        Spinner spinnerReturn = findViewById(R.id.spinnerReturn);
        editTextReturnQuantity = findViewById(R.id.editTextReturnQuantity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            itemPosition = bundle.getInt("position");
            String name = Globals.items.get(itemPosition).getName();
            textViewReturnName.setText(name);

        }
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Globals.jobs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerReturn.setAdapter(adapter);
        spinnerReturn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int jobPosition, long id) {
                selectedJobIndex = jobPosition;
                int selectedJobQuantity = Globals.items.get(itemPosition).getJobsInfo(jobPosition);
                editTextReturnQuantity.setText(String.valueOf(selectedJobQuantity));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void buttonCancelReturnPressed(View view) {
        goToMain();
    }

    public void buttonOkReturnPressed(View view) {
        String returnQuantityString = editTextReturnQuantity.getText().toString().trim();
        returnQuantity = Integer.parseInt(returnQuantityString);
        int inJob = Globals.items.get(itemPosition).getJobsInfo(selectedJobIndex);
        if (inJob >= returnQuantity) {
            Globals.items.get(itemPosition).returnToStock(selectedJobIndex, returnQuantity);
            goToMain();
        } else {
            String impossibleToReturn =
                    String.format(getString(R.string.it_is_impossible_to_return_more_than), inJob);
            Toast.makeText(this, impossibleToReturn, Toast.LENGTH_LONG).show();
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonRemoveJobPressed(View view) {
        Globals.removeJob(selectedJobIndex);
        adapter.notifyDataSetChanged();
    }
}