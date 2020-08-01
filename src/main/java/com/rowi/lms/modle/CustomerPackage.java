/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.modle;

/**
 *
 * @author ROSHAN
 */
public class CustomerPackage {
    private int indexNo;
    private int cpu;
    private Customer customer;
    private VehiclePackage vehiclePackage;

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public VehiclePackage getVehiclePackage() {
        return vehiclePackage;
    }

    public void setVehiclePackage(VehiclePackage vehiclePackage) {
        this.vehiclePackage = vehiclePackage;
    }
    
}
