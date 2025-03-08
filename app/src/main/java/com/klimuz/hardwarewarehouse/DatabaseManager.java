package com.klimuz.hardwarewarehouse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<String> jobs;

    public DatabaseManager(Context context) {
        this.dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
        this.jobs = new ArrayList<>(); // Инициализация jobs внутри конструктора
    }
    // Метод для сохранения jobs
    public void saveJobs(ArrayList<String> jobs) {
        db.execSQL("DELETE FROM " + DBHelper.TABLE_JOB_NAMES);
        for (String job : jobs) {
            saveJobName(job);
        }
    }
    // Метод для сохранения отдельного jobName
    private void saveJobName(String jobName) {
        ContentValues values = new ContentValues();
        values.put("name", jobName);
        db.insert(DBHelper.TABLE_JOB_NAMES, null, values);
    }
    // Метод для сохранения оборудования
    public void saveEquipment(ArrayList<Equipment> items) {
        db.execSQL("DELETE FROM " + DBHelper.TABLE_JOBS_INFO);
        db.execSQL("DELETE FROM " + DBHelper.TABLE_EQUIPMENT);
        for (int i = 0; i < items.size(); i++) {
            Equipment equipment = items.get(i);
            ContentValues values = new ContentValues();
            values.put("name", equipment.getName());
            values.put("totalQuantity", equipment.getTotalQuantity());
            long equipmentId = db.insert(DBHelper.TABLE_EQUIPMENT, null, values);

            saveJobsInfo(equipmentId, equipment.getJobsList());
        }
    }
    // Метод для сохранения jobsInfo
    private void saveJobsInfo(long equipmentId, ArrayList<Integer> jobsInfo) {
        for (int i = 0; i < jobsInfo.size(); i++) {
            int jobNameId = i;//getJobNameId(jobs.get(i));
            ContentValues jobValues = new ContentValues();
            jobValues.put("equipmentId", equipmentId);
            jobValues.put("jobNameId", jobNameId);
            jobValues.put("quantity", jobsInfo.get(i));
            db.insert("jobsInfo", null, jobValues);
        }
    }
    // Метод для чтения оборудования
    public ArrayList<Equipment> getEquipmentList() {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        Cursor cursor = db.query("Equipment", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int totalQuantityIndex = cursor.getColumnIndex("totalQuantity");

            if (idIndex >= 0 && nameIndex >= 0 && totalQuantityIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                int totalQuantity = cursor.getInt(totalQuantityIndex);
                Equipment equipment = new Equipment(name, totalQuantity);

                Cursor jobCursor = db.query("jobsInfo", new String[]{"jobNameId", "quantity"}, "equipmentId=?", new String[]{String.valueOf(id)}, null, null, null);
                ArrayList<Integer> jobsInfo = new ArrayList<>();
                while (jobCursor.moveToNext()) {
                    int jobNameIdIndex = jobCursor.getColumnIndex("jobNameId");
                    int quantityIndex = jobCursor.getColumnIndex("quantity");

                    if (jobNameIdIndex >= 0 && quantityIndex >= 0) {
                        int quantity = jobCursor.getInt(quantityIndex);
                        jobsInfo.add(quantity);
                    } else {
                        if (jobNameIdIndex < 0) {
                            Log.d("DatabaseManager", "Column 'jobNameId' not found in jobsInfo table.");
                        }
                        if (quantityIndex < 0) {
                            Log.d("DatabaseManager", "Column 'quantity' not found in jobsInfo table.");
                        }
                    }
                }
                jobCursor.close();
                equipment.setJobsList(jobsInfo);

                equipmentList.add(equipment);
            } else {
                if (idIndex < 0) {
                    Log.d("DatabaseManager", "Column 'id' not found in Equipment table.");
                }
                if (nameIndex < 0) {
                    Log.d("DatabaseManager", "Column 'name' not found in Equipment table.");
                }
                if (totalQuantityIndex < 0) {
                    Log.d("DatabaseManager", "Column 'totalQuantity' not found in Equipment table.");
                }
            }
        }
        cursor.close();
        return equipmentList;
    }
    // Метод для чтения jobNames
    public ArrayList<String> getJobs() {
        ArrayList<String> jobs = new ArrayList<>();
        Cursor cursor = db.query("jobNames", new String[]{"name"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex("name");
            if (nameIndex >= 0) {
                String jobName = cursor.getString(nameIndex);
                jobs.add(jobName);
            } else {
                Log.d("DatabaseManager", "Column 'name' not found in jobNames table.");
            }
        }
        cursor.close();
        return jobs;
    }
    // Функция сброса базы данных
    public void resetDatabase() {
        dbHelper.resetDatabase();
    }

}



