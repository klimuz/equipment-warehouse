package com.klimuz.hardwarewarehouse.google;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.klimuz.hardwarewarehouse.Globals;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class UploadDB {

    private static final String FOLDER_ID = "1sICRM3OPxKSzJDwdx1HW1EQG73NLBScu";

    public static void uploadDatabase(Context context) {
        new Thread(() -> {
            try {
                Log.i("myLog", "Начало загрузки базы данных в фоновом потоке.");
                // Проверим текущий поток
                Log.i("myLog", "Текущий поток: " + Thread.currentThread().getName());

                // Инициализация Google Drive API
                Drive driveService = InitializeAPI.getDriveService(context);

                // Путь к локальной базе данных
                String localDatabasePath = context.getDatabasePath("inventory.db").getPath();
                java.io.File databaseFile = new java.io.File(localDatabasePath);
                if (!databaseFile.exists()) {
                    Log.e("myLog", "Файл базы данных не найден: " + localDatabasePath);
                    return;
                }

                // Проверка существования файла на Google Drive
                String existingFileId = getFileIdByNameInFolder(driveService, "inventory.db", FOLDER_ID);
                if (existingFileId != null) {
                    // Удаляем старый файл
                    driveService.files().delete(existingFileId).execute();
                    Log.i("myLog", "Старый файл inventory.db удалён.");
                }

                // Создание метаданных для нового файла
                File fileMetadata = new File();
                fileMetadata.setName("inventory.db");
                fileMetadata.setParents(Collections.singletonList(FOLDER_ID));

                // Загрузка нового файла
                FileContent mediaContent = new FileContent("application/x-sqlite3", databaseFile);
                File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                        .setFields("id")
                        .execute();

                Log.i("myLog", "Файл inventory.db успешно загружен. File ID: " + uploadedFile.getId());
                Globals.isUploaded = true;
            } catch (Exception e) {
                Log.e("myLog", "Ошибка при загрузке базы данных", e);
            }
        }).start();
    }

    private static String getFileIdByNameInFolder(Drive driveService, String fileName, String folderId) throws IOException {
        List<File> files = driveService.files().list()
                .setQ("name='" + fileName + "' and '" + folderId + "' in parents and trashed=false")
                .setFields("files(id, name)")
                .execute()
                .getFiles();

        return (files != null && !files.isEmpty()) ? files.get(0).getId() : null;
    }
}
