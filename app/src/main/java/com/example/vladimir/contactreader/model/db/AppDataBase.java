package com.example.vladimir.contactreader.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{
    public abstract ContactDao contactDao();
}
