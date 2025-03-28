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

public class IssueActivity extends AppCompatActivity {
    private EditText editTextIssueQuantity;
    private int position;
    private Equipment originalEquipment;
    private int selectedJobIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        TextView textViewIssueName = findViewById(R.id.textViewIssueName);
        Spinner spinnerIssue = findViewById(R.id.spinnerIssue);
        TextView textViewIssueTotal = findViewById(R.id.textViewIssueTotal);
        TextView textViewInStoreDigit = findViewById(R.id.textViewInStoreDigit);
        editTextIssueQuantity = findViewById(R.id.editTextIssueQuantity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            position = bundle.getInt("position");
            originalEquipment = Globals.items.get(position);
            String originalName = originalEquipment.getName();
            int originalTotalQuantity = originalEquipment.getTotalQuantity();
            textViewIssueName.setText(originalName);
            textViewIssueTotal.setText(String.valueOf(originalTotalQuantity));
            int originalIssueQuantity = originalEquipment.getJobsInfo(selectedJobIndex);
            editTextIssueQuantity.setText(String.valueOf(originalIssueQuantity));
            textViewInStoreDigit.setText(String.valueOf(Globals.items.get(position).getInStock()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Globals.jobs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerIssue.setAdapter(adapter);
        spinnerIssue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedJobIndex = position;
                int selectedJobQuantity = originalEquipment.getJobsInfo(selectedJobIndex);
                editTextIssueQuantity.setText(String.valueOf(selectedJobQuantity));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void buttonAddJobPressed(View view) {
        Intent intent = new Intent(this, AddJobActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void buttonIssueCancelPressed(View view) {
        goToMain();
    }

    public void buttonIssueOkPressed(View view) {
        if (!Globals.jobs.isEmpty()){
            String issueQuantityString = editTextIssueQuantity.getText().toString().trim();
            if (!issueQuantityString.isEmpty()) {
                int issueQuantityInt = Integer.parseInt(issueQuantityString);

                int inStock = Globals.items.get(position).getInStock();
                if (issueQuantityInt <= inStock){
                    Globals.items.get(position).takeFromStock(selectedJobIndex, issueQuantityInt);
//                    try (DatabaseManager databaseManager = new DatabaseManager(this)) {
//                        databaseManager.saveDataToDatabase();
//                    }
                    goToMain();
                } else {
                    String impossible = String.format(getString(R.string.it_is_impossible_to_take_more_than), inStock);
                    Toast.makeText(this, impossible, Toast.LENGTH_LONG).show();
                }
            } else {
                String fillQuantity = getString(R.string.fill_quantity);
                Toast.makeText(this, fillQuantity, Toast.LENGTH_LONG).show();
            }
        } else {
            String addJobFirst = getString(R.string.add_job_first);
            Toast.makeText(this, addJobFirst, Toast.LENGTH_LONG).show();
        }
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}