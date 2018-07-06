package com.example.vladimir.contactreader.data;

import com.example.vladimir.contactreader.app.App;
import com.example.vladimir.contactreader.app.AppDataBase;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MapPosition implements SaveMapPosition {

    private final AppDataBase database;
    private String TAG = "TAG";

    @Inject
    public MapPosition(AppDataBase appDataBase) {
        this.database = appDataBase;
    }

    @Override
    public Observable<Void> savePosition(LatLng latLng, long id, String address) {
        return Observable.create(emitter -> {
            try {
                double lat = latLng.latitude;
                double lng = latLng.longitude;
                database.getContactDao().updateLat(lat, (int) id);
                database.getContactDao().updateLng(lng, (int) id);
                database.getContactDao().updateAddress(address, (int) id);
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        });
    }
}


