package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.model.ContactLoad;
import com.example.vladimir.contactreader.model.ContactLoading;
import com.example.vladimir.contactreader.presenter.ContactPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactListModule {
    @Provides
    @Singleton
    public ContactPresenter getContactPresenter(ContactLoading contactLoading) {
        return new ContactPresenter(contactLoading);

    }

    @Provides
    @Singleton
    public ContactLoading getContactLoad(ContactLoad contactLoad) {
        return contactLoad;
    }
}
