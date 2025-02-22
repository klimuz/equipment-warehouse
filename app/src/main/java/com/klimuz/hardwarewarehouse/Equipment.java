package com.klimuz.hardwarewarehouse;

import java.util.ArrayList;

public class Equipment {
    private String name;
    private int totalQuantity;
    private ArrayList<Integer> jobsInfo = new ArrayList<>();

    public Equipment(String name, int totalQuantity) {
        this.name = name;
        this.totalQuantity = totalQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getInStock() {
        return totalQuantity - getInUse();
    }

    public int getInUse() {
        int inUse = 0;
        if (!jobsInfo.isEmpty()) {
            for (Integer q : jobsInfo) {
                inUse += q;
            }
        }
        return inUse;
    }

    public void setJobsInfo(int equipmentQuantity){
        jobsInfo.add(equipmentQuantity);
    }

    public int getJobsInfo(int jobIndex){
        int equipmentQuantity = 0;
        if (!jobsInfo.isEmpty()) {
            equipmentQuantity = jobsInfo.get(jobIndex);
        }
        return equipmentQuantity;
    }

    public void updateJobsInfo(int jobIndex, int equipmentQuantity) {
        jobsInfo.set(jobIndex, equipmentQuantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Equipment equipment = (Equipment) obj;
        return name.equals(equipment.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
