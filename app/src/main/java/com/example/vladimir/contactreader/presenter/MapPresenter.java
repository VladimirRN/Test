package com.example.vladimir.contactreader.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.model.geocode.FeatureMember;
import com.example.vladimir.contactreader.model.geocode.GeoObject;
import com.example.vladimir.contactreader.model.geocode.GeoObjectCollection;
import com.example.vladimir.contactreader.model.geocode.GeoResponse;
import com.example.vladimir.contactreader.model.geocode.Response;
import com.example.vladimir.contactreader.model.network.GeocodeApi;
import com.example.vladimir.contactreader.model.SaveMapPosition;
import com.example.vladimir.contactreader.model.db.AppDataBase;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.model.db.ContactDao;
import com.example.vladimir.contactreader.model.network.RouteApi;
import com.example.vladimir.contactreader.model.route.RouteApiResponse;
import com.example.vladimir.contactreader.view.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapView> {
    private static final String TAG = "TAG";
    private final GeocodeApi geocodeApi;
    private SaveMapPosition positionInterface;
    private DisposableObserver<Void> disposableObserver;
    private RouteApi routeApi;
    private String endLocation;
    private String startLocation;
    private LatLng latLngStartLocation;
    private LatLng latlngEndLocation;
    private String contactNameStart;
    private String contactStartAdress;
    private String contactEndAdress;
    private String contactNameEnd;

    @Inject
    public MapPresenter(SaveMapPosition positionInterface, GeocodeApi geocodeApi, RouteApi routeApi) {
        this.positionInterface = positionInterface;
        this.geocodeApi = geocodeApi;
        this.routeApi = routeApi;
    }

    public void saveLanLng(LatLng latLng, Long id, String address) {
        disposableObserver = new DisposableObserver<Void>() {
            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "координаты сохранены в бд");
            }
        };
        positionInterface.savePosition(latLng, id, address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
        //TODO сделать отписку

    }

    public void mapReady(GoogleMap googleMap, Long id) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        contactDao.getContactById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Contact>() {
                    @Override
                    public void onSuccess(Contact contact) {
                        Log.d(TAG, "координаты выгружены");
                        if (contact.getLat() != null && contact.getLng() != null) {
                            double lat = contact.getLat();
                            double lng = contact.getLng();
                            LatLng latLngFromDB = new LatLng(lat, lng);
                            String address = contact.getAddress();
                            Log.d(TAG, "Adress = " + address);
                            String name = contact.getName();
                            getViewState().showMarker(googleMap, latLngFromDB, address, name);
                            Log.d(TAG, "latlng = " + latLngFromDB);
                        } else {
                            LatLng latlngDefault = new LatLng(57, 53);
                            String name = contact.getName();
                            String address = "Ижевск";
                            getViewState().showMarker(googleMap, latlngDefault, address, name);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "ошибка при выгрузке координат");
                        e.printStackTrace();
                    }
                });
    }


    public void showMarkerForListContacts(GoogleMap googleMap) {
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        contactDao.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Contact>>() {
                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        if (contacts.size() == 0){
                            
                        } else {
                            getViewState().showMarkers(googleMap, contacts);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void onMapClick(LatLng latLng) {
        String geocode = latLng.longitude + "," + latLng.latitude;
        geocodeApi.getPositionAddress(geocode, "json", "locality", "20.5,20,4")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<GeoResponse>() {
                    @Override
                    public void onSuccess(GeoResponse geoResponse) {
                        Response res = geoResponse.getResponse();
                        GeoObjectCollection geoObjectCollection = res.getGeoObjectCollection();
                        Log.d(TAG, res.toString());
                        List<FeatureMember> geoObjects = geoObjectCollection.getFeatureMember();
                        if (geoObjects.size() == 0) {
                            String position = "Адрес не определен";
                            getViewState().showMarkerWithGeocodePosition(position);
                            Log.d(TAG, "нет обьектов");

                        } else {
                            GeoObject geoObject = geoObjects.get(0).getGeoObject();
                            String position = geoObject.getDescription() + "," + geoObject.getName();
                            getViewState().showMarkerWithGeocodePosition(position);
                            Log.d(TAG, "position = " + position);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void showRoute(GoogleMap googleMap, long[] idRoute) {
        Long [] id = {idRoute[0], idRoute[1]};
        List<Long> idlist = new ArrayList<>(Arrays.asList(id));
        AppDataBase dataBase = App.getInstance().getDataBase();
        ContactDao contactDao = dataBase.contactDao();
        contactDao.getContactListById(idlist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Contact>>() {
                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        Log.d(TAG, "contact size = " + contacts.size());
                        if (contacts.size() != 0) {
                            double lngStartLocation = contacts.get(0).getLng();
                            double latStartLocation = contacts.get(0).getLat();
                            contactNameStart = contacts.get(0).getName();
                            contactStartAdress = contacts.get(0).getAddress();
                            latLngStartLocation = new LatLng(latStartLocation, lngStartLocation);
                            startLocation = latLngStartLocation.latitude + "," + latLngStartLocation.longitude;
                            double lngEndLocation = contacts.get(1).getLng();
                            double latEndLocation = contacts.get(1).getLat();
                            contactNameEnd = contacts.get(1).getName();
                            contactEndAdress = contacts.get(1).getAddress();
                            latlngEndLocation = new LatLng(latEndLocation, lngEndLocation);
                            endLocation = latlngEndLocation.latitude + "," + latlngEndLocation.longitude;
                            Log.d(TAG, "startlocation = " + contactEndAdress);

                            routeApi.getRoute(startLocation, endLocation, "AIzaSyAt7W2y05dP88dlV3Pr8UZ4twQYyrvwTO4")
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableSingleObserver<RouteApiResponse>() {
                                        @Override
                                        public void onSuccess(RouteApiResponse routeApiResponse) {
                                            Log.d(TAG, "запрос успешен");
                                            getViewState().showRoute(googleMap, routeApiResponse, latlngEndLocation, latLngStartLocation,
                                                    contactNameEnd, contactNameStart, contactStartAdress, contactEndAdress );
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            e.printStackTrace();
                                            Log.d(TAG, "ошибка при запросе");
                                        }
                                    });




                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        //TODO написать ResourceManager
//        routeApi.getRoute(startLocation, endLocation, "AIzaSyAt7W2y05dP88dlV3Pr8UZ4twQYyrvwTO4")
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableSingleObserver<RouteApiResponse>() {
//                    @Override
//                    public void onSuccess(RouteApiResponse routeApiResponse) {
//                        Log.d(TAG, "запрос успешен");
//                        getViewState().showRoute(googleMap, routeApiResponse, latlngEndLocation, latLngStartLocation,
//                                contactNameEnd, contactNameStart, contactStartAdress, contactEndAdress );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        Log.d(TAG, "ошибка при запросе");
//                    }
//                });

    }
}
