package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.app.AppDataBase;
import com.example.vladimir.contactreader.data.MapPosition;
import com.example.vladimir.contactreader.network.geocode.GeocodeApi;
import com.example.vladimir.contactreader.data.SaveMapPosition;
import com.example.vladimir.contactreader.network.route.RouteApi;
import com.example.vladimir.contactreader.presentation.map.MapPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {
    @Provides
    @Singleton
    public MapPresenter getMapPresenter(SaveMapPosition saveMapPosition, AppDataBase appDataBase, GeocodeApi geocodeApi, RouteApi routeApi) {
        return new MapPresenter(saveMapPosition, appDataBase, geocodeApi, routeApi);
    }

    @Provides
    @Singleton
    public SaveMapPosition getMapPosition(MapPosition mapPosition) {
        return mapPosition;
    }
}
