package com.klimuz.hardwarewarehouse;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class Globals {
    public static final ArrayList<Equipment> items = new ArrayList<>();
    public static final ArrayList<String> jobs = new ArrayList<>();

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

    public static void removeJobs() {
        ArrayList<Integer> indicesToRemove = new ArrayList<>();
        int counter = 0;
        if (!jobs.isEmpty()) {
            for (int j = 0; j < jobs.size(); j++) {
                for (Equipment equipment : items) {
                    counter += equipment.getJobsList().get(j);
                }
                if (counter == 0) {
                    indicesToRemove.add(j);
                }
                counter = 0;
            }
            for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
                int index = indicesToRemove.get(i);
                jobs.remove(index);
                for (Equipment equipment : items)
                    equipment.removeJob(index);
            }
        }
    }
}


