package com.example.contactsapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {database.class}, version = 1)
public abstract class contact_database extends RoomDatabase {
    private static contact_database instance;
    public abstract DBDao dbDao();
    public static synchronized contact_database getInstance(Context context){

        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
            contact_database.class, "contact_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();

        }
        return instance;
    }
    private static Callback roomcallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private DBDao dbDao;

        private PopulateDbAsyncTask(contact_database db) {
            dbDao = db.dbDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dbDao.insert(new database("name 1","number 1"));
            dbDao.insert(new database("name 2","number 2"));
            dbDao.insert(new database("name 3","number 3"));
            return null;
        }
    }
}
