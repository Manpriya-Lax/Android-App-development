package com.example.contactsapp;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contactsDB")
public class database {

    @PrimaryKey
    @NonNull
    private String number;

    private String name;

    public database(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

}