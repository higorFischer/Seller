package com.team.seller.models;

import com.team.seller.commons.IdGenerator;

import java.lang.reflect.ParameterizedType;

public class Entity<T> {

    String Name;
    String ID;
    Class<T> Type;

    public Entity(String name) {
        ID = IdGenerator.GeneratRandomID();
        Name = name;
        Type = (Class<T>)
                ((ParameterizedType)getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];

    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }
}
