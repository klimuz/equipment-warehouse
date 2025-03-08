package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

import android.Manifest;


public class SaveActivity extends AppCompatActivity {

    private Spinner spinnerCreateFile;
    private int selectedJobIndex = 0;
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        // Запрос разрешений
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
            }
        }

        spinnerCreateFile = findViewById(R.id.spinnerCreateFile);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Globals.jobs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCreateFile.setAdapter(adapter);
        spinnerCreateFile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedJobIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void buttonCreateAndSharePressed(View view) {
        String column0 = getString(R.string.tool);
        String column1 = getString(R.string.quantity);
        File file = ExcelUtils.createExcelFile(this, Globals.items, selectedJobIndex, column0, column1);
        Uri fileUri = FileProvider.getUriForFile(this, this.getPackageName() + ".fileprovider", file);
        if (file != null) {
            String positiveReport = String.format(getString(R.string.file_xls_saved), Globals.jobs.get(selectedJobIndex));
            Toast.makeText(SaveActivity.this, positiveReport, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.ms-excel");
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.excel_file));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.please_find_attached_the_excel_file));
            startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
        } else {
            String negativeReport = String.format(getString(R.string.file_xls_not_saved), Globals.jobs.get(selectedJobIndex));
            Toast.makeText(this, negativeReport, Toast.LENGTH_LONG).show();
        }
    }

    public void buttonCreateExitPressed(View view) {
        finishAffinity();
        System.exit(0);
    }
}