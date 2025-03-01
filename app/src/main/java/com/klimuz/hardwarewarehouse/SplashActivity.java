package com.klimuz.hardwarewarehouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Показываем splash screen в течение 3 секунд (3000 мс)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Переход к MainActivity после показа splash screen
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
