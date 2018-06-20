package com.example.vladimir.contactreader;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.vladimir.contactreader.di.AppModule;
import com.example.vladimir.contactreader.di.ContactComponent;
import com.example.vladimir.contactreader.di.ContactListModule;
import com.example.vladimir.contactreader.di.DaggerContactComponent;
import com.example.vladimir.contactreader.di.DetailsContactModule;
import com.example.vladimir.contactreader.model.db.AppDataBase;

public class App extends Application {
    public static App instance;

    private AppDataBase dataBase;
    private ContactComponent contactComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        instance = this;
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    private void initDagger() {
       contactComponent = DaggerContactComponent.builder()
               .appModule(new AppModule(this))
               .detailsContactModule(new DetailsContactModule())
               .contactListModule(new ContactListModule())
               .build();
       contactComponent.inject(this);
    }

    public ContactComponent getContactComponent() {
        if (contactComponent == null) {
            initDagger();
        }
        return contactComponent;
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDataBase() {
        return dataBase;
    }
}

