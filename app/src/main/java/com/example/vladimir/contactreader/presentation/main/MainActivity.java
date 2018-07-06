package com.example.vladimir.contactreader.presentation.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.presentation.contact.ContactFragment;
import com.example.vladimir.contactreader.presentation.contacts.ContactsFragment;
import com.example.vladimir.contactreader.presentation.map.MapFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ContactsFragment.ClickOnItem, ContactFragment.setLocation
, MapFragment.OnInteractionListener {

    private static final String TAG = "TAG";
    private static final String ROUTE = "ROUTE";
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    public final String INDEX = "index";
    public final String MAP = "map";
    private boolean isTablet;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean permissionGranted = true;
        fm = getSupportFragmentManager();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionGranted = false;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        if (savedInstanceState == null && permissionGranted) {
                startFragment();
        }
    }

    public void startFragment() {
        if (isTablet) {
            ContactsFragment contactsFragment = new ContactsFragment();
            fm.beginTransaction().replace(R.id.contact_fragment, contactsFragment, "tabletList").commit();
        } else {
            ContactsFragment contactsFragment = new ContactsFragment();
            fm.beginTransaction().replace(R.id.fragment_container, contactsFragment, "phoneList").commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startFragment();
                }
            }
        }
    }

    @Override
    public void itemClickInTablet(long id) {
        ContactFragment detailsFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putLong(INDEX, id);
        detailsFragment.setArguments(args);
        fm.beginTransaction().replace(R.id.details_fragment, detailsFragment, "tablet").commit();
    }

    @Override
    public void itemCLickInPhone(long id) {
        FragmentTransaction transaction = fm.beginTransaction();
        ContactFragment detailsFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putLong(INDEX, id);
        detailsFragment.setArguments(args);
        transaction.replace(R.id.fragment_container, detailsFragment, "phone");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startMapForListContacts() {
        FragmentTransaction transaction = fm.beginTransaction();
        MapFragment mapFragment = new MapFragment();
        transaction.replace(R.id.fragment_container, mapFragment, "mapAllContact");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startRouteMap(ArrayList<Long> listIdForRoute) {
        FragmentTransaction transaction = fm.beginTransaction();
        MapFragment mapFragment = new MapFragment();
        Bundle args = new Bundle();
        long [] idRoute = new long[listIdForRoute.size()];
        int i = 0;
        for (Long e : listIdForRoute) {
            idRoute[i++] = e;
        }
        args.putLongArray(ROUTE, idRoute );
        mapFragment.setArguments(args);
        transaction.replace(R.id.fragment_container, mapFragment, "map");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startMapForSingleContact(long id) {
        Log.d(TAG, "id in activity = " + id);
        FragmentTransaction transaction = fm.beginTransaction();
        MapFragment mapFragment = new MapFragment();
        Bundle args = new Bundle();
        args.putLong(MAP, id);
        mapFragment.setArguments(args);
        transaction.replace(R.id.fragment_container, mapFragment, "map");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void popBackStack() {
        fm.popBackStack();
    }

}


