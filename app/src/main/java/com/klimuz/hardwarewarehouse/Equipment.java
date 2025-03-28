package com.klimuz.hardwarewarehouse;

import java.util.ArrayList;

public class Equipment {
    private String name;
    private int totalQuantity;
    private int inStock = 0;
    private ArrayList<Integer> jobsInfo = new ArrayList<>();

    public Equipment(String name, int totalQuantity) {
        this.name = name;
        this.totalQuantity = totalQuantity;
//        inStock = totalQuantity - getInUse();
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
        inStock = totalQuantity - getInUse();
        return inStock;
    }

    public void returnToStock(int jobIndex, int quantity){
        int inThisJob = getJobsInfo(jobIndex);
        if (quantity <= inThisJob){
            inThisJob -= quantity;
            jobsInfo.set(jobIndex, inThisJob);
            inStock -= quantity;
        }
    }

    public void  takeFromStock(int jobIndex, int quantity){
        ArrayList<Integer> arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
//        List<Integer> list = new
        int inThisJob = getJobsInfo(jobIndex);
        if (quantity <= inStock) {
            inThisJob += quantity;
            inStock -= quantity;
            jobsInfo.set(jobIndex, inThisJob);//inThisJob.jobIndex
        }
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

    public int getJobsInfo(int jobIndex) {
        int equipmentQuantity = 0;
        if (!jobsInfo.isEmpty() && jobIndex < jobsInfo.size()) {
            equipmentQuantity = jobsInfo.get(jobIndex);
        }
        return equipmentQuantity;
    }


    public ArrayList<Integer> getJobsList(){
        return jobsInfo;
    }



    public void setJobsList(ArrayList<Integer> newJobsInfo){
        jobsInfo = newJobsInfo;
    }

    public void removeJob(int index){
        int quantity = jobsInfo.get(index);
        inStock += quantity;
        jobsInfo.remove(index);
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
