package com.example.mabiaat.offlinedata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Sales.class, Representative.class, Commission.class}, version = 1, exportSchema = false)
public abstract class Myappdatabas extends RoomDatabase {
    private static Myappdatabas INSTANCE;

    public abstract SalesDao salesDao();
    public abstract RepresentativesDao representativesDao();
    public abstract CommissionsDao commissionsDao();

    public static Myappdatabas getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                       Myappdatabas.class, "maindatabase")
                       .allowMainThreadQueries()
                       .build();
        }
        return INSTANCE;
    }

}
