package com.example.contactsapp;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
public class contact_repository {
    private DBDao dbDao;
    private LiveData<List<database>> allDB;

    public contact_repository (Application application){
        contact_database database = contact_database.getInstance(application);
        dbDao = database.dbDao();
        allDB =dbDao.getalldatabase();
    }

    public void insert(database database){
        new InsertDBAsyncTask(dbDao).execute(database);

    }

    public void update(database database){
        new updateDBAsyncTask(dbDao).execute(database);

    }

    public void delete(database database){
        new deleteDBAsyncTask(dbDao).execute(database);

    }

    public void deleteallDB( ){
        new deleteAllDBAsyncTask(dbDao).execute();

    }
    public LiveData<List<database>> getAllDB(){
        return allDB;
    }

    private static class InsertDBAsyncTask extends AsyncTask<database, Void, Void> {
        private DBDao dbdao;

        private InsertDBAsyncTask(DBDao dbDao) {
            this.dbdao = dbDao;
        }

        @Override
        protected Void doInBackground(database... db) {
            dbdao.insert(db[0]);
            return null;
        }
    }

    private static class updateDBAsyncTask extends AsyncTask<database, Void, Void> {
        private DBDao dbdao;

        private updateDBAsyncTask(DBDao dbDao) {
            this.dbdao = dbDao;
        }

        @Override
        protected Void doInBackground(database... db) {
            dbdao.update(db[0]);
            return null;
        }
    }
    private static class deleteDBAsyncTask extends AsyncTask<database, Void, Void> {
        private DBDao dbdao;

        private deleteDBAsyncTask(DBDao dbDao) {
            this.dbdao = dbDao;
        }

        @Override
        protected Void doInBackground(database... db) {
            dbdao.delete(db[0]);
            return null;
        }
    }

    private static class deleteAllDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private DBDao dbdao;

        private deleteAllDBAsyncTask(DBDao dbDao) {
            this.dbdao = dbDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dbdao.deletealldatabase();
            return null;
        }
    }
}
