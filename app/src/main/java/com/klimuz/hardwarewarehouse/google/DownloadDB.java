package com.klimuz.hardwarewarehouse.google;

import android.content.Context;
import android.util.Log;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.klimuz.hardwarewarehouse.Globals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

public class DownloadDB {

    private static final String FOLDER_ID = "1sICRM3OPxKSzJDwdx1HW1EQG73NLBScu";

    public static void downloadDatabase(Context context) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Инициализация Google Drive API
                Drive driveService = InitializeAPI.getDriveService(context);

                // Локальный путь базы данных
                String localDatabasePath = context.getDatabasePath("inventory.db").getPath();

                // Ищем файл inventory.db на Google Drive
                String fileId = getFileIdByNameInFolder(driveService, "inventory.db", FOLDER_ID);
                if (fileId == null) {
                    Log.e("myLog", "Файл inventory.db не найден на Google Drive.");
                    return;
                }

                // Скачивание файла
                try (FileOutputStream outputStream = new FileOutputStream(localDatabasePath)) {
                    driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
                    Globals.isDownloaded = true;
                    Log.i("myLog", "Файл inventory.db успешно скачан в " + localDatabasePath);
                }
            } catch (Exception e) {
                Log.e("myLog", "Ошибка при скачивании базы данных", e);
            }
        });
    }

    private static String getFileIdByNameInFolder(Drive driveService, String fileName, String folderId) throws IOException {
        FileList result = driveService.files().list()
                .setQ("name='" + fileName + "' and '" + folderId + "' in parents and trashed=false")
                .setFields("files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        return (files != null && !files.isEmpty()) ? files.get(0).getId() : null;
    }
}
