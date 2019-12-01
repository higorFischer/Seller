package com.team.seller.repositories;
import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team.seller.commons.IOnGetDataListener;
import com.team.seller.models.Entity;

import java.util.ArrayList;
import java.util.Map;

public abstract class BaseRepository<T extends Entity>  {

    protected DatabaseReference DatabaseReference = FirebaseDatabase.getInstance().getReference();
    protected DatabaseReference DataBase;
    protected ArrayList<T> Entites = new ArrayList<T>();


    public BaseRepository(String table) {
        DataBase = DatabaseReference.child(table);
    }

    public ArrayList<T> getEntites() { return Entites; }

    public abstract T ReadSnapShots(DataSnapshot snapshot);

    public void Create(final T entity, final IOnGetDataListener listener){
        listener.onStart();

        DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataBase.child(entity.getID()).setValue(entity);
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }

    public void Read(final IOnGetDataListener listener){
        listener.onStart();
        DataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Entites = new ArrayList();

                for(DataSnapshot entity: dataSnapshot.getChildren())
                    Entites.add(ReadSnapShots(entity));

                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }

    public void ReadByName(final String Name, final IOnGetDataListener listener){

        listener.onStart();
        DataBase.orderByChild("name").equalTo(Name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Entites = new ArrayList();

                for(DataSnapshot entity: dataSnapshot.getChildren())
                    Entites.add(ReadSnapShots(entity));

                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }

    public void ReadByID(final String ID, final IOnGetDataListener listener){

        listener.onStart();
        DataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Entites = new ArrayList();

                if(dataSnapshot.hasChild(ID)){
                    ReadSnapShots(dataSnapshot.child(ID));
                    listener.onSuccess(dataSnapshot);
                } else {
                    listener.onFailure();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }


    public void Update(final T entity){
        DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(entity.getID())){
                    DataBase.child(entity.getID()).removeValue();
                    DataBase.child(entity.getID()).setValue(entity);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Delete(final T entity, final IOnGetDataListener listener){

        listener.onStart();
        DatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(entity.getID()));
                    DataBase.child(entity.getID()).removeValue();

                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }
}
