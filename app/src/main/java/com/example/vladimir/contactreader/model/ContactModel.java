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
            //ContactsContract.Contacts.Data.MIMETYPE,
           // ContactsContract.CommonDataKinds.Phone.NUMBER};
           // ContactsContract.CommonDataKinds.Email.DATA,
            //ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            //ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME};
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
                //SORT_ORDER
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
            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)),null,null,null,null,null,null,null));
                    //cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
                    //cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)),
                   // cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE))));
            // long name = Long.parseLong(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
            cursor.moveToNext();
        }

        //cursor.close();




        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        contactDao.insert(myList);
        Log.d(TAG, "create database");
        List<Contact> listContactfromDB = contactDao.getAll();
        Log.d(TAG, "list size = " + listContactfromDB.size());
        presenter.showContacts(listContactfromDB);
        Log.d(TAG, "list2 size in model " + listContactfromDB.size());
        for (int i = 0; i < listContactfromDB.size(); i++) {
            //Log.d(TAG, " " + listContactfromDB.get(i).getId());

        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
