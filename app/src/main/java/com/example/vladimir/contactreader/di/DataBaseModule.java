package com.example.vladimir.contactreader.di;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.vladimir.contactreader.BuildConfig;
import com.example.vladimir.contactreader.app.AppDataBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Provides
    @Singleton
    public AppDataBase provideDataBase (Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, "database")
                .build();
    }
}
