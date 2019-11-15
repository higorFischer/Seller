package com.team.seller.repositories;

import com.google.firebase.database.DataSnapshot;
import com.team.seller.models.Sell;
import com.team.seller.models.User;

public class SellRepository extends BaseRepository<Sell>{

    public SellRepository() {
        super("Sell");
    }

    @Override
    public Sell ReadSnapShots(DataSnapshot snapshot){
        return snapshot.getValue(Sell.class);
    }
}
