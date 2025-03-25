package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.klimuz.hardwarewarehouse.google.DownloadDB;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Globals.isInternetAvailable(this)){
            DownloadDB.downloadDatabase(this);
        } else {
            Intent intent = new Intent(this, FirstInternetActivity.class);
            startActivity(intent);
        }

        // Показываем splash screen в течение 3 секунд (3000 мс)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (Globals.isDownloaded) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, FirstInternetActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
