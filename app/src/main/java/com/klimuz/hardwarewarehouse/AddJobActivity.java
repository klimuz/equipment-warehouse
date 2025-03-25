package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddJobActivity extends AppCompatActivity {

    private EditText editTextAddJobName;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        editTextAddJobName = findViewById(R.id.editTextAddJobName);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            position = bundle.getInt("position");
        }
    }

    public void buttonAddJobOkPressed(View view) {
        String jobName = editTextAddJobName.getText().toString().trim();
        if (!jobName.isEmpty()){
            Globals.createJob(jobName, this);
//            try (DatabaseManager databaseManager = new DatabaseManager(this)) {
//                databaseManager.saveDataToDatabase();
//            }
            Intent intent = new Intent(this, IssueActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        } else {
            String fill = getString(R.string.fill_job_name);
            Toast.makeText(this, fill, Toast.LENGTH_LONG).show();
        }
    }

    public void buttonAddJobCancelPressed(View view) {
        Intent intent = new Intent(this, IssueActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}