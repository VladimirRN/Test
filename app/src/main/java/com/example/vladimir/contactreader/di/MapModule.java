package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.model.MapPosition;
import com.example.vladimir.contactreader.model.network.GeocodeApi;
import com.example.vladimir.contactreader.model.SaveMapPosition;
import com.example.vladimir.contactreader.model.network.RouteApi;
import com.example.vladimir.contactreader.presenter.MapPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {
    @Provides
    @Singleton
    public MapPresenter getMapPresenter(SaveMapPosition saveMapPosition, GeocodeApi geocodeApi, RouteApi routeApi) {
        return new MapPresenter(saveMapPosition, geocodeApi, routeApi);
    }

    @Provides
    @Singleton
    public SaveMapPosition getMapPosition(MapPosition mapPosition) {
        return mapPosition;
    }
}
