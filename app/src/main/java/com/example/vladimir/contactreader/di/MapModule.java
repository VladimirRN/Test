package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.model.MapPosition;
import com.example.vladimir.contactreader.model.SaveMapPosition;
import com.example.vladimir.contactreader.presenter.MapPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {
    @Provides
    @Singleton
    public MapPresenter getMapPresenter(SaveMapPosition saveMapPosition) {
        return new MapPresenter(saveMapPosition);
    }

    @Provides
    @Singleton
    public SaveMapPosition getMapPosition(MapPosition mapPosition) {
        return mapPosition;
    }
}
