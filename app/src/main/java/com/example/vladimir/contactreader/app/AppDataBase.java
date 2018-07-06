package com.example.vladimir.contactreader.app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.example.vladimir.contactreader.data.Contact;
import com.example.vladimir.contactreader.data.ContactDao;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase{
    @NonNull
    public abstract ContactDao getContactDao();
}
