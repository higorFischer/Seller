package com.team.seller.models;

import androidx.annotation.NonNull;

import java.security.acl.Group;

public class Report extends Entity<Report> {

    String Grouper;
    double Quantity;
    double Total;

    public Report(String grouper, double quantity, double total) {
        super("");
        Grouper = grouper;
        Quantity = quantity;
        Total = total;
    }

    public String getGrouper() {
        return Grouper;
    }

    public void setGrouper(String grouper) {
        Grouper = grouper;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    @NonNull
    @Override
    public String toString() {
        return  "Name      : " + Grouper + "\n"+
                "Quantity  : " + Quantity + "\n"+
                "Total        : R$ " + Total + "\n";
    }

}
