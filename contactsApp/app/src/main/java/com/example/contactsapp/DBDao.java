package com.example.contactsapp;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DBDao {

    @Insert
    void insert(database database);
    @Update
    void update(database database);
    @Delete
    void delete(database database);


    @Query("DELETE FROM contactsDB")
    void deletealldatabase();

    @Query("SELECT * FROM contactsDB ORDER BY name DESC ")
    LiveData<List<database>> getalldatabase();

}
