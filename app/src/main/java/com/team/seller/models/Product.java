package com.team.seller.models;

import androidx.annotation.NonNull;

public class Product extends Entity<Product> {

    String Size;
    String Description ;

    public Product(String name, String size, String description) {
        super(name);
        Size = size;
        Description = description;
    }

    public Product() {
        super("");
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        this.Size = size;
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
                "Size " + Size + "\n"+
                "Description: " + Description + "\n";
    }
}
