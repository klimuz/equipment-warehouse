package com.klimuz.hardwarewarehouse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EQUIPMENT = "Equipment";
    public static final String TABLE_JOB_NAMES = "jobNames";
    public static final String TABLE_JOBS_INFO = "jobsInfo";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы Equipment
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EQUIPMENT + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "totalQuantity INTEGER)");

        // Создание таблицы jobNames
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_JOB_NAMES + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT)");

        // Создание таблицы для связи jobsInfo и jobNames
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_JOBS_INFO + " ("
                + "equipmentId INTEGER, "
                + "jobNameId INTEGER, "
                + "quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление старых таблиц и создание новых при обновлении базы данных
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_NAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS_INFO);
        onCreate(db);
    }

    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EQUIPMENT);
        db.execSQL("DELETE FROM " + TABLE_JOB_NAMES);
        db.execSQL("DELETE FROM " + TABLE_JOBS_INFO);
        db.execSQL("VACUUM"); // Опционально
        db.close();
    }
}
