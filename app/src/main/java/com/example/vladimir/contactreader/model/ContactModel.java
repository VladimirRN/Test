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
        loaderManager.initLoader(0, null, this);
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
        // presenter.showContacts(cursor);
        //List<Contact> list = new ArrayList<>();
        cursor.moveToFirst();
        List<Contact> myList = new ArrayList<>();
//        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
//            myList.add(new Contact(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)),
//                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))));
            myList.add(new Contact(Long.parseLong(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))));
             long name = Long.parseLong(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
           // Log.d(TAG, "id " + name);
//            String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
//Log.d(TAG, name + " " + surname);
            cursor.moveToNext();
        }

        cursor.close();
        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        contactDao.insert(myList);
        Log.d(TAG, "create database");
        List<Contact> list2 = contactDao.getAll();
        Log.d(TAG, "list size = " + list2.size());
        for (int i = 0; i < myList.size(); i++) {
            System.out.println(myList.get(i));
            String d = myList.get(i).getDisplayName();
            Log.d(TAG, d);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
