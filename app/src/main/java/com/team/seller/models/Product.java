package com.team.seller.models;

import androidx.annotation.NonNull;

public class Product extends Entity<Product> {

    String Description ;

    public Product(String name, String description) {
        super(name);
        Description = description;
    }

    public Product() {
        super("");
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return  "ID: " + ID + "\n"+
                "Product: " + Name + "\n"+
                "Description: " + Description + "\n";
    }
}
