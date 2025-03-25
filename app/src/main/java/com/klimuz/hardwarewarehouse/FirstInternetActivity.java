package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.klimuz.hardwarewarehouse.google.DownloadDB;

public class FirstInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_internet);

    }

    public void buttonWorkOfflinePressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonRetryPressed(View view) {
        DownloadDB.downloadDatabase(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Globals.isDownloaded) {
                    Intent intent = new Intent(FirstInternetActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String notLoaded = getString(R.string.not_loaded);
                    Toast.makeText(FirstInternetActivity.this, notLoaded, Toast.LENGTH_LONG).show();
                }

            }
        }, 5000);
    }
}