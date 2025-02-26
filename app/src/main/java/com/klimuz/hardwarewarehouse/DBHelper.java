package com.klimuz.hardwarewarehouse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "equipment_db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EQUIPMENT_TABLE = "CREATE TABLE Equipment (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "totalQuantity INTEGER, " +
                "inStock INTEGER)";
        db.execSQL(CREATE_EQUIPMENT_TABLE);

        String CREATE_JOBS_TABLE = "CREATE TABLE Jobs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "jobName TEXT";
        db.execSQL(CREATE_JOBS_TABLE);

        String CREATE_EQUIPMENT_JOBS_TABLE = "CREATE TABLE EquipmentJobs (" +
                "equipmentId INTEGER, " +
                "JOBiD INTEGER, " +
                "FOREIGN KEY (equipmentId) REFERENCES Equipment(id), " +
                "FOREIGN KEY (jobId) REFERENCES Jobs(id))";
        db.execSQL(CREATE_EQUIPMENT_JOBS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Equipment");
        db.execSQL("DROP TABLE IF EXISTS Jobs");
        db.execSQL("DROP TABLE IF EXISTS EquipmentJobs");
        onCreate(db);
    }
}
