package com.team.seller.commons;

import android.util.Base64;

import com.google.firebase.database.DataSnapshot;

import java.util.Random;

public interface IOnGetDataListener {
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}