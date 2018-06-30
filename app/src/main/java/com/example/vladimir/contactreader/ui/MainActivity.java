package com.example.vladimir.contactreader.ui;

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


public class MainActivity extends AppCompatActivity implements ContactsFragment.ClickOnItem, DetailsFragment.setLocation
, MapFragment.OnInteractionListener{

    private static final String TAG = "TAG";
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
        //TODO добавить пермишены на локацию
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionGranted = false;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        if (savedInstanceState == null) {
            if (permissionGranted) {
                startFragment();
            }
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
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(INDEX, id);
        detailsFragment.setArguments(args);
        fm.beginTransaction().replace(R.id.details_fragment, detailsFragment, "tablet").commit();
    }

    @Override
    public void itemCLickInPhone(long id) {
       // fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putLong(INDEX, id);
        detailsFragment.setArguments(args);
        transaction.replace(R.id.fragment_container, detailsFragment, "phone");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startMapForListContacts() {
        //fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MapFragment mapFragment = new MapFragment();
        transaction.replace(R.id.fragment_container, mapFragment, "mapAllContact");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startMapForSingleContact(long id) {
        Log.d(TAG, "id in activity = " + id);
        //fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MapFragment mapFragment = new MapFragment();
        Bundle args = new Bundle();
        args.putLong(MAP, id);
        mapFragment.setArguments(args);
        transaction.replace(R.id.fragment_container, mapFragment, "map");
        transaction.addToBackStack(null);
        transaction.commit();
        // mapFragment = (MapFragment) fm.findFragmentByTag("map");
        // mapFragment.deletePosition();
     //   fm.executePendingTransactions();
    }

    @Override
    public void popBackStack() {
        fm.popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Log.d(TAG, "onBack not fragment");
//        MapFragment mapFragment = (MapFragment) fm.findFragmentByTag("map");
//            mapFragment.deletePosition();
//            Log.d(TAG, "onBack");
          //  fm.popBackSta;

    }
}


