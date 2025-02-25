package com.klimuz.hardwarewarehouse;

import java.util.ArrayList;

public class Globals {
    public static final ArrayList<Equipment> items = new ArrayList<>();
    public static final ArrayList<String> jobs = new ArrayList<>();

    public static void createJob(String name) {
        jobs.add(name);
        for (Equipment equipment : items) {
            equipment.setJobsInfo(0);
        }
    }

    public static void removeJobs() {
        ArrayList<Integer> indicesToRemove = new ArrayList<>();
        if (!items.isEmpty()) {
            int size = items.get(0).getJobsList().size();
            for (int i = 0; i < size; i++) {
                boolean allZeros = true;
                for (Equipment equipment : items) {
                    if (equipment.getJobsList().get(i) != 0) {
                        allZeros = false;
                        break;
                    }
                }
                if (allZeros) {
                    indicesToRemove.add(i);
                }
            }
            for (Equipment equipment : items) {
                for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
                    equipment.removeJob(indicesToRemove.get(i));
                }
            }
        }
    }
}


