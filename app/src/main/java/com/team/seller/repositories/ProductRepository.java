package com.team.seller.repositories;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.team.seller.models.Product;
import com.team.seller.models.User;

public class ProductRepository extends BaseRepository<Product>{

    public ProductRepository() {
        super("Product");
    }

    @Override
    public Product ReadSnapShots(DataSnapshot snapshot) {
        return snapshot.getValue(Product.class);
    }
}