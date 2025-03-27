package com.klimuz.hardwarewarehouse;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class Globals {
    public static ArrayList<Equipment> items = new ArrayList<>();
    public static ArrayList<String> jobs = new ArrayList<>();

    public static Boolean isDownloaded = false;
    public static Boolean isUploaded = false;

    private static final MutableLiveData<Boolean> observedBoolean = new MutableLiveData<>(false);
    public static LiveData<Boolean> getObservedBoolean() {
        return observedBoolean;
    }
    public static void setBooleanValue(boolean value) {
        observedBoolean.setValue(value);
    }

    public static void createJob(String name, Context context) {
        if (!jobs.contains(name)) {
            jobs.add(name);
            for (Equipment equipment : items) {
                equipment.setJobsInfo(0);
            }
        } else {
            String alreadyContains = String.format(context.getString(R.string.list_already_contains), name);
            Toast.makeText(context, alreadyContains, Toast.LENGTH_LONG).show();
        }
    }

    public static void removeJob(int jobIndex) {
        jobs.remove(jobIndex);
        for (Equipment equipment : items){
            equipment.removeJob(jobIndex);
        }
    }
    public static void removeEquipment(int index, Context context){
        items.remove(index);
        DatabaseManager databaseManager = new DatabaseManager(context);
            databaseManager.saveDataToDatabase();
    }
    public static Boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }
}


