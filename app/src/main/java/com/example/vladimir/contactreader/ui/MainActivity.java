package com.example.vladimir.contactreader.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.AppDataBase;
import com.example.vladimir.contactreader.ContactDao;
import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.presenter.MainPresenter;
import com.example.vladimir.contactreader.view.MainActivityView;


public class MainActivity extends MvpAppCompatActivity implements MainActivityView {

    private static final String TAG = "TAG";
    @InjectPresenter(type = PresenterType.GLOBAL, tag = "mainPresenter")
    MainPresenter mainPresenter;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    public final String INDEX = "index";
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "start");
        isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean permissionGranted = true;

       // AppDataBase db = App.getInstance().getDataBase();
        //ContactDao contactDao = db.contactDao();

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
            getSupportFragmentManager().beginTransaction().replace(R.id.contact_fragment, contactsFragment).commit();
        } else {
            ContactsFragment contactsFragment = new ContactsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, contactsFragment).commit();
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
    public void startDetailsFragmentForPhone(int itemKey) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(INDEX, itemKey);
        Log.d(TAG, "key in actitvity " + itemKey);
        detailsFragment.setArguments(args);
        transaction.replace(R.id.fragment_container, detailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void startDetailsFragmentForTablet(int itemKey) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(INDEX, itemKey);
        detailsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.details_fragment, detailsFragment).commit();
    }
}


