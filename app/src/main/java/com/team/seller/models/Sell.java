package com.team.seller.models;

public class Sell extends Entity<Sell> {

    double Price;
    double Latitude;
    double Longitude;

    public Sell(String name, double latitude, double longitude, double price) {
        super(name);
        Latitude = latitude;
        Longitude = longitude;
        Price = price;
    }

    public Sell() {
        super("");
    }

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
