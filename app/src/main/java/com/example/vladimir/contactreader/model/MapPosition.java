package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.ContactDao;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MapPosition implements SaveMapPosition {

    private String TAG = "TAG";

    @Inject
    public MapPosition() {
    }

    @Override
    public Observable<Void> savePosition(LatLng latLng, long id, String address) {
        return Observable.create(emitter -> {
            try {

                AppDataBase appDataBase = App.getInstance().getDataBase();
                ContactDao contactDao = appDataBase.contactDao();
                double lat = latLng.latitude;
                double lng = latLng.longitude;
                contactDao.updateLat(lat, (int) id);
                contactDao.updateLng(lng, (int) id);
                contactDao.updateAddress(address, (int) id);
            } catch (Exception e) {
                emitter.onError(e);
            } finally {
                emitter.onComplete();
            }
        });
    }
}


