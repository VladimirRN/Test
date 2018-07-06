package com.example.vladimir.contactreader.presentation.map;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vladimir.contactreader.app.App;
import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.data.Contact;
import com.example.vladimir.contactreader.data.route.EndLocation;
import com.example.vladimir.contactreader.data.route.Leg;
import com.example.vladimir.contactreader.data.route.Route;
import com.example.vladimir.contactreader.data.route.RouteApiResponse;
import com.example.vladimir.contactreader.data.route.StartLocation;
import com.example.vladimir.contactreader.data.route.Step;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

public class  MapFragment extends MvpAppCompatFragment implements com.example.vladimir.contactreader.presentation.map.MapView {

    private static final String ROUTE = "ROUTE";
    @InjectPresenter
    MapPresenter mapPresenter;

    @Inject
    public Provider<MapPresenter> mapPresenterProvider;
    private String address;
    private String contactName;
    private StartLocation startLocation;
    private ArrayList<EndLocation> routerPoints = new ArrayList<>();

    @ProvidePresenter
    MapPresenter provideMapPresenter() {
        return mapPresenterProvider.get();
    }

    private static final String TAG = "TAG";
    private GoogleMap map;
    private MapView mapView;
    private Long id;
    private final String MAP = "map";
    private OnInteractionListener onInteractionListener;
    private LatLng geo;
    private long[] idRoute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.setClickable(true);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        Bundle args = getArguments();
        if (args != null) {
            id = args.getLong(MAP);
            Log.d(TAG, "id single marker = " + id );
            idRoute = args.getLongArray(ROUTE);
            //Log.d(TAG, "" + idRoute.toString());
        }

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(googleMap -> {
           // Log.d(TAG, "mapready");
            if (args == null) {
                mapPresenter.showMarkerForListContacts(googleMap);
            } else if (id != 0) {
                mapPresenter.mapReady(googleMap, id);
                Log.d(TAG, "старт карты позиции");
            } else {
                mapPresenter.showRoute(googleMap, idRoute);
                Log.d(TAG, "старт карты маршрута");
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App app = (App) getActivity().getApplication();
        app.getContactComponent().inject(this);

        try {
            onInteractionListener = (OnInteractionListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void showMarker(GoogleMap googleMap, LatLng latLng, String address, String name) {
        Log.d(TAG, "showMarker moxy ");
        contactName = name;
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        LatLng izhevsk = new LatLng(57, 53);
        if (latLng != null) {
            map.addMarker(new MarkerOptions().position(latLng).title(name).snippet(address));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(5).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            map.addMarker(new MarkerOptions().position(izhevsk).title("Marker Title").snippet("Marker Description"));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(izhevsk).zoom(5).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapPresenter.onMapClick(latLng);
                Log.d(TAG, "onMapClick");
                geo = latLng;
                map.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(geo);
                map.addMarker(new MarkerOptions().position(geo).title(contactName));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(geo, map.getCameraPosition().zoom);
                map.animateCamera(cameraUpdate);
            }
        });
    }

    @Override
    public void showMarkers(GoogleMap googleMap, List<Contact> contactList) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Contact contact : contactList) {
            LatLng latLng = new LatLng(contact.getLat(), contact.getLng());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(contact.getDisplayName());
            markerOptions.position(latLng);
            markerOptions.snippet(contact.getAddress());
            googleMap.addMarker(markerOptions);
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void showMarkerWithGeocodePosition(String positionAddress) {
        Toast.makeText(getContext(), positionAddress, Toast.LENGTH_SHORT).show();
        address = positionAddress;
        map.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(geo);
        map.addMarker(new MarkerOptions().position(geo).title(contactName).snippet(positionAddress));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(geo, map.getCameraPosition().zoom);
        map.animateCamera(cameraUpdate);
    }

    @Override
    public void showRoute(GoogleMap googleMap, RouteApiResponse routeApiResponse, LatLng end, LatLng start,
                          String nameEnd, String nameStart, String startAddress, String endAddress) {
        Log.d(TAG, "Latlng end =" + end + " LatLng start = " + start + " namestatr =  " + nameStart + " adressStart = " + startAddress);
        String status = routeApiResponse.getStatus();
        //Toast.makeText(getContext(), "status" + status, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "status = " + status);
        if (!status.equals("OK")) {
            showPreviewFragment();
            Toast.makeText(getContext(), "Невозможно построить маршрут", Toast.LENGTH_SHORT).show();
           // Toast.makeText(getContext(), "status" + status, Toast.LENGTH_SHORT).show();
        } else {
            List<Route> routes = routeApiResponse.getRoutes();
            Route route = routes.get(0);
            List<Leg> legs = route.getLegs();
            Leg leg = legs.get(0);
            List<Step> steps = leg.getSteps();
            startLocation = steps.get(0).getStartLocation();
            for (Step step : steps) {
                routerPoints.add(step.getEndLocation());
            }
        }
        googleMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(start);
        builder.include(end);
        LatLngBounds bounds = builder.build();
        googleMap.addMarker(new MarkerOptions().position(start).title(nameStart).snippet(startAddress));
        googleMap.addMarker(new MarkerOptions().position(end).title(nameEnd).snippet(endAddress));

        PolylineOptions polylineOptions = new PolylineOptions();
        if (startLocation != null & routerPoints != null) {
            polylineOptions.add(new LatLng(startLocation.getLat(), startLocation.getLng()));
            Log.d(TAG, "startLocation lat = " + startLocation.getLat() + " startlocationLng = " + startLocation.getLng());
            for (EndLocation point: routerPoints) {
                polylineOptions.add(new LatLng(point.getLat(), point.getLng()));
            }
        }
        polylineOptions.width(10);
        polylineOptions.color(Color.BLACK);
        googleMap.addPolyline(polylineOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        googleMap.animateCamera(cameraUpdate);
    }

    public interface OnInteractionListener {
        void popBackStack();
    }

    public void showPreviewFragment() {
        onInteractionListener.popBackStack();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (id != null && id != 0) {
            getActivity().getMenuInflater().inflate(R.menu.map_menu, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buttonSave: {
                if (geo != null){
                    mapPresenter.saveLanLng(geo, id, address);
                    showPreviewFragment();
                    Log.d(TAG, "адресс на сохранение = " + geo);
                    Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Выберите местоположение для контакта", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
