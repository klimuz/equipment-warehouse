package com.klimuz.hardwarewarehouse.google;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.Executors;

public class SyncDB {

    public static void syncDatabaseWithDrive(Context context) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Log.i("myLog", "Начало синхронизации базы данных...");

                // Скачивание базы данных
                DownloadDB.downloadDatabase(context);

                // Загрузка базы данных
                UploadDB.uploadDatabase(context);

                Log.i("myLog", "Синхронизация завершена.");
            } catch (Exception e) {
                Log.e("myLog", "Ошибка при синхронизации базы данных", e);
            }
        });
    }
}
