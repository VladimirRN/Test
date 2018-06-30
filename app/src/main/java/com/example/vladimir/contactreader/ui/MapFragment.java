package com.example.vladimir.contactreader.ui;

import android.content.Context;
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
import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.model.db.Contact;
import com.example.vladimir.contactreader.presenter.MapPresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

public class MapFragment extends MvpAppCompatFragment implements com.example.vladimir.contactreader.view.MapView {

    @InjectPresenter
    MapPresenter mapPresenter;

    @Inject
    public Provider<MapPresenter> mapPresenterProvider;

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
    private LatLng position;

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
        }

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(googleMap -> {
            Log.d(TAG, "mapready");
            if (id != null) {
                mapPresenter.mapReady(googleMap, id);
            } else {
                mapPresenter.showMarkerForListContact(googleMap);
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
    public void showMarker(GoogleMap googleMap, LatLng latLng) {
        Log.d(TAG, "showMarker moxy ");
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        LatLng izhevsk = new LatLng(57, 53);
        if (latLng != null) {
            map.addMarker(new MarkerOptions().position(latLng).title("Marker Title").snippet("Marker Description"));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(2).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            map.addMarker(new MarkerOptions().position(izhevsk).title("Marker Title").snippet("Marker Description"));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(izhevsk).zoom(2).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TAG, "onMapClick");
                map.clear();
                position = latLng;
                map.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                map.addMarker(markerOptions);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, map.getCameraPosition().zoom);
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
            googleMap.addMarker(markerOptions);
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0);
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
        if (id != null) {
            getActivity().getMenuInflater().inflate(R.menu.map_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buttonSave: {
                mapPresenter.saveLanLng(position, id);
                showPreviewFragment();
                Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
