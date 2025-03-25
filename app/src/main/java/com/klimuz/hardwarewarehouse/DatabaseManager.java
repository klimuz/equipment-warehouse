package com.klimuz.hardwarewarehouse;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String DATABASE_NAME = "inventory.db";
    private final Context context;

    // Конструктор
    public DatabaseManager(Context context) {
        this.context = context;
    }

    /**
     * Функция для сохранения данных в SQLite.
     * Использует Globals.items и Globals.jobs.
     */
    public void saveDataToDatabase() {
        SQLiteDatabase db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        // Создание таблиц, если они еще не существуют
        db.execSQL("CREATE TABLE IF NOT EXISTS equipment (name TEXT, totalQuantity INTEGER, jobsList TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS jobs (name TEXT)");

        // Очистка таблиц
        db.execSQL("DELETE FROM equipment");
        db.execSQL("DELETE FROM jobs");

        // Сохранение jobs
        for (String job : Globals.jobs) {
            ContentValues jobValues = new ContentValues();
            jobValues.put("name", job);
            db.insert("jobs", null, jobValues);
        }

        // Сохранение equipment
        for (Equipment equipment : Globals.items) {
            ContentValues equipmentValues = new ContentValues();
            equipmentValues.put("name", equipment.getName());
            equipmentValues.put("totalQuantity", equipment.getTotalQuantity());
            equipmentValues.put("jobsList", equipment.getJobsList().toString()); // Прямое преобразование списка в строку
            db.insert("equipment", null, equipmentValues);
        }

        db.close();
    }

    /**
     * Функция для восстановления данных из SQLite.
     * Заполняет Globals.items и Globals.jobs.
     */
    public void restoreDataFromDatabase() {
        SQLiteDatabase db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        // Чтение jobs
        Cursor jobsCursor = db.rawQuery("SELECT name FROM jobs", null);
        Globals.jobs.clear();
        while (jobsCursor.moveToNext()) {
            Globals.jobs.add(jobsCursor.getString(0));
        }
        jobsCursor.close();

        // Чтение equipment
        Cursor equipmentCursor = db.rawQuery("SELECT name, totalQuantity, jobsList FROM equipment", null);
        Globals.items.clear();
        while (equipmentCursor.moveToNext()) {
            String name = equipmentCursor.getString(0);
            int totalQuantity = equipmentCursor.getInt(1);
            String jobsListString = equipmentCursor.getString(2);

            // Преобразование строки jobsList обратно в ArrayList<Integer>
            ArrayList<Integer> jobsInfo = new ArrayList<>();
            for (String job : jobsListString.replace("[", "").replace("]", "").split(",\\s*")) {
                try {
                    if (job != null && !job.isEmpty()) {
                        jobsInfo.add(Integer.parseInt(job.trim()));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Некорректное значение при преобразовании: " + job);
                }
            }

            // Создание объекта Equipment и установка jobsInfo через метод setJobsList
            Equipment equipment = new Equipment(name, totalQuantity);
            equipment.setJobsList(jobsInfo); // Установка jobsInfo
            Globals.items.add(equipment);
        }
        equipmentCursor.close();

        db.close();
    }
}





