package com.klimuz.hardwarewarehouse.google;

import android.content.Context;
import android.util.Log;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.klimuz.hardwarewarehouse.R;

import java.io.InputStream;
import java.util.Collections;

public class InitializeAPI {
    private static Drive driveService;

    private static void initializeDriveService(Context context) {
        try {
            if (driveService == null) {
                JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                InputStream serviceAccountStream = context.getResources().openRawResource(R.raw.credentials);
                GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream)
                        .createScoped(Collections.singletonList(DriveScopes.DRIVE_FILE));
                driveService = new Drive.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        jsonFactory,
                        new HttpCredentialsAdapter(credentials))
                        .setApplicationName("Your Application Name")
                        .build();
                Log.i("myLog", "Google Drive API успешно инициализирован");
            }
        } catch (Exception e) {
            Log.e("myLog", "Ошибка при инициализации Google Drive API", e);
        }
    }

    public static Drive getDriveService(Context context) {
        initializeDriveService(context);
        return driveService;
    }
}


