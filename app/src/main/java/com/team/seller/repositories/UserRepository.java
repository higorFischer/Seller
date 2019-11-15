package com.team.seller.repositories;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.team.seller.models.User;

public class UserRepository extends BaseRepository<User>{

    public UserRepository() {
        super("User");
    }

    @Override
    public User ReadSnapShots(DataSnapshot snapshot){
        return snapshot.getValue(User.class);
    }
}
