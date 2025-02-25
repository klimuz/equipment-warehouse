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

import java.util.ArrayList;

public class IssueActivity extends AppCompatActivity {
    private TextView textViewIssueName;
    private Spinner spinnerIssue;
    private TextView textViewIssueTotal;
    private TextView textViewInStoreDigit;
    private EditText editTextIssueQuantity;
    private int position;
    private Equipment originalEquipment;
    private String originalName;
    private int originalTotalQuantity;
    private int originalIssueQuantity;
    private int selectedJobIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        textViewIssueName = findViewById(R.id.textViewIssueName);
        spinnerIssue = findViewById(R.id.spinnerIssue);
        textViewIssueTotal = findViewById(R.id.textViewIssueTotal);
        textViewInStoreDigit = findViewById(R.id.textViewInStoreDigit);
        editTextIssueQuantity = findViewById(R.id.editTextIssueQuantity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            position = bundle.getInt("position");
            originalEquipment = Globals.items.get(position);
            originalName = originalEquipment.getName();
            originalTotalQuantity = originalEquipment.getTotalQuantity();
            textViewIssueName.setText(originalName);
            textViewIssueTotal.setText(String.valueOf(originalTotalQuantity));
            originalIssueQuantity = originalEquipment.getJobsInfo(selectedJobIndex);
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

    public void ButtonAddJobPressed(View view) {
        Intent intent = new Intent(this, AddJobActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void buttonIssueCancelPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonIssueOkPressed(View view) {
        if (!Globals.jobs.isEmpty()){
            String issueQuantityString = editTextIssueQuantity.getText().toString();
            if (!issueQuantityString.isEmpty()) {
                int issueQuantityInt = Integer.parseInt(issueQuantityString);
                Globals.items.get(position).updateJobsInfo(selectedJobIndex, issueQuantityInt);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                String fillQuantity = getString(R.string.fill_quantity);
                Toast.makeText(this, fillQuantity, Toast.LENGTH_LONG).show();
            }
        } else {
            String addJobFirst = getString(R.string.add_job_first);
            Toast.makeText(this, addJobFirst, Toast.LENGTH_LONG).show();
        }
    }
}