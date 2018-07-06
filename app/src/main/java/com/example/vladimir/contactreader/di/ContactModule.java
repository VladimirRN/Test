package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.data.DetailsInterface;
import com.example.vladimir.contactreader.data.DetailsRead;
import com.example.vladimir.contactreader.presentation.contact.ContactPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactModule {
    @Provides
    @Singleton
    public ContactPresenter getDetailsPresenter(DetailsInterface detailsInterface) {
        return new ContactPresenter(detailsInterface);
    }

    @Provides
    @Singleton
    public DetailsInterface getDetailsRead(DetailsRead detailsRead) {
        return  detailsRead;
    }

}
