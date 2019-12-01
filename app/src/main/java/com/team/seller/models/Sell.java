package com.team.seller.models;

import java.util.Date;

public class Sell extends Entity<Sell> {

    double Price;
    double Quantity;
    double Latitude;
    double Longitude;
    Date Date;
    String ProductID;
    String SellerID;

    public String getSellerID() {
        return SellerID;
    }

    public void setSellerID(String sellerID) {
        SellerID = sellerID;
    }

    public Sell(String productID, String sellerID, double latitude, double longitude, double price, double quaantity, Date date) {
        super(null);
        ProductID = productID;
        SellerID = sellerID;
        Date = date;
        Quantity = quaantity;
        Latitude = latitude;
        Longitude = longitude;
        Price = price;
    }

    public double getQuantity() {
        return Quantity;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public int getMonthDate() {
        return Date.getMonth();
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public Sell() {
        super("");
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    @Override
    public String getName() { return null; }

    @Override
    public void setName(String name) {}


    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
