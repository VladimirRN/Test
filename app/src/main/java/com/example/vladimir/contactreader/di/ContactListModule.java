package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.app.AppDataBase;
import com.example.vladimir.contactreader.data.ContactLoad;
import com.example.vladimir.contactreader.data.ContactLoading;
import com.example.vladimir.contactreader.presentation.contacts.ContactsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactListModule {
    @Provides
    @Singleton
    public ContactsPresenter getContactPresenter(ContactLoading contactLoading, AppDataBase appDataBase) {
        return new ContactsPresenter(contactLoading, appDataBase);

    }

    @Provides
    @Singleton
    public ContactLoading getContactLoad(ContactLoad contactLoad) {
        return contactLoad;
    }
}
