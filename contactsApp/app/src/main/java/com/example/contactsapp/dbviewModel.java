package com.example.contactsapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class dbviewModel extends AndroidViewModel {

    private contact_repository repository;
    private LiveData<List<database>> allcontacts;
    public dbviewModel(@NonNull Application application) {
        super(application);
        repository=new contact_repository(application);
        allcontacts =repository.getAllDB();

    }
    public void insert(database database){
        repository.insert(database);
    }
    public void update(database database){
        repository.update(database);
    }
    public void delete(database database){
        repository.delete(database);
    }
    public void deleteAll(database database){
        repository.deleteallDB();
    }
    public LiveData<List<database>> getAllcontacts(){
        return allcontacts;
    }
}
