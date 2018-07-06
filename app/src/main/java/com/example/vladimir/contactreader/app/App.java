package com.example.vladimir.contactreader.app;

import android.app.Application;

import com.example.vladimir.contactreader.di.AppModule;
import com.example.vladimir.contactreader.di.ContactComponent;
import com.example.vladimir.contactreader.di.DaggerContactComponent;

public class App extends Application {

    private ContactComponent contactComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        contactComponent = DaggerContactComponent.builder()
                .appModule(new AppModule(this))
                .build();
        contactComponent.inject(this);
    }

    public ContactComponent getContactComponent() {
        if (contactComponent == null) {
            initDagger();
        }
        return contactComponent;
    }
}

