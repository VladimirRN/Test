package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.model.DetailsInterface;
import com.example.vladimir.contactreader.model.DetailsRead;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailsContactModule {
    @Provides
    @Singleton
    public DetailsPresenter getDetailsPresenter(DetailsInterface detailsInterface) {
        return new DetailsPresenter(detailsInterface);
    }

    @Provides
    @Singleton
    public DetailsInterface getDetailsRead(DetailsRead detailsRead) {
        return  detailsRead;
    }

}
