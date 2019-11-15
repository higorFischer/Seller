package com.team.seller.models;

import androidx.annotation.NonNull;

public class User extends Entity<User> {

    String Senha;
    String Email ;

    public User(String name, String email, String senha) {
        super(name);
        Senha = senha;
        Email = email;
    }

    public User() {
        super("");
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getLogin() {
        return Email;
    }

    public void setLogin(String email) {
        Email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return  "ID: " + ID + "\n"+
                "Name: " + Name + "\n"+
                "Email " + Email + "\n"+
                "Password: " + Senha + "\n";
    }

}
