package com.example.vladimir.contactreader.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.AppDataBase;
import com.example.vladimir.contactreader.Contact;
import com.example.vladimir.contactreader.ContactDao;
import com.example.vladimir.contactreader.presenter.ContactPresenter;

import java.util.ArrayList;
import java.util.List;

public class ContactModel implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "TAG";
    private final String SORT_ORDER = ContactsContract.Data.MIMETYPE;
    private final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
    private Context context;
    private LoaderManager loaderManager;
    private ContactPresenter presenter;


    public ContactModel(Context context, LoaderManager loaderMan, ContactPresenter contactPresenter) {
        this.context = context;
        this.presenter = contactPresenter;
        this.loaderManager = loaderMan;
        loaderManager.initLoader(9000, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                context,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        List<Contact> myList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            myList.add(new Contact(Long.parseLong(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)), null, null, null, null, null, null, null));
            cursor.moveToNext();
        }

        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        Log.d(TAG, "create database");
        List<Contact> listContactfromDB = contactDao.getAll();
        if (listContactfromDB.size() != 0) {
            contactDao.delete(listContactfromDB);
            contactDao.insert(myList);
        } else {
            contactDao.insert(myList);
        }
        List<Contact> listContactfromDBnew = contactDao.getAll();
        Log.d(TAG, "list size = " + listContactfromDB.size());
        List<String> listDisplayName = new ArrayList<>();
        for (int i = 0; i < listContactfromDBnew.size(); i++) {
            listDisplayName.add(listContactfromDBnew.get(i).getDisplayName());
        }
        presenter.showContacts(listDisplayName);
        Log.d(TAG, "list2 size in model " + listContactfromDBnew.size());
        DetailsModel detailsModel = new DetailsModel(context, loaderManager);
        detailsModel.startDetailLoader();
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
