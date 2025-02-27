package com.klimuz.hardwarewarehouse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы Equipment
        db.execSQL("CREATE TABLE Equipment ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "totalQuantity INTEGER, "
                + "inStock INTEGER)");

        // Создание таблицы jobNames
        db.execSQL("CREATE TABLE jobNames ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT)");

        // Создание таблицы для связи jobsInfo и jobNames
        db.execSQL("CREATE TABLE jobsInfo ("
                + "equipmentId INTEGER, "
                + "jobNameId INTEGER, "
                + "quantity INTEGER, "
                + "FOREIGN KEY(equipmentId) REFERENCES Equipment(id), "
                + "FOREIGN KEY(jobNameId) REFERENCES jobNames(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление старых таблиц и создание новых при обновлении базы данных
        db.execSQL("DROP TABLE IF EXISTS Equipment");
        db.execSQL("DROP TABLE IF EXISTS jobNames");
        db.execSQL("DROP TABLE IF EXISTS jobsInfo");
        onCreate(db);
    }
}
