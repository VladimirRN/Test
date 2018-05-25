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
import com.example.vladimir.contactreader.ContactObject;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;
import com.example.vladimir.contactreader.view.DetailsModelInterface;

import java.util.ArrayList;
import java.util.List;

public class DetailsModel implements LoaderManager.LoaderCallbacks<Cursor>, DetailsModelInterface {

    private static final String TAG = "TAG" ;
    private final String SORT_ORDER = ContactsContract.Data.MIMETYPE;
    private final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";
    private final String[] PROJECTION = {
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME};
    private final DetailsPresenter detailsPresenter;
    private Context context;
    private LoaderManager loaderManager;
    private String[] mSelectionArgs = {""};
    private ArrayList<String> list;
    private AppDataBase db;
    private int id;

    public DetailsModel(Context context, LoaderManager loaderManager, DetailsPresenter detailsPresenter) {
        this.context = context;
        this.loaderManager = loaderManager;
        this.detailsPresenter = detailsPresenter;
        loaderManager.initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                context,
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                SORT_ORDER
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
       // ArrayList<String> list = new ArrayList<>();
        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        if (cursor.moveToFirst()) {
            do {
                String mime = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                switch (mime) {
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                        String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        detailsPresenter.getDataName(name, surname);
                        contactDao.updateName(name, id);
                        contactDao.updateSurname(surname, id);
                        //list.add(name);

                        //list.add(surname);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactDao.updatePhone(number, id);
//                        Contact contact = contactDao.getById(id);
//                        Log.d(TAG, "phone from db " + contact.getPhone());
                        detailsPresenter.getDataPhone(number);
                        break;
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                        detailsPresenter.getDataEmail(address);
                        contactDao.updateEmail(address, id);
//                        Contact contact = contactDao.getById(id);
//                        Log.d(TAG, "phone from db " + contact.getEmail());
                        //list.add()
                        break;


                }

            } while (cursor.moveToNext());

        }
        Contact contact = contactDao.getById(id);
        Log.d(TAG, "phone from db " + contact.getPhone() + " email " + contact.getEmail()
        + " name " + contact.getName() + " surname " + contact.getSurname());


//        List<Contact> myList = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            myList.add(new Contact(cursor.getPosition(),cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)),
//                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME))));
//            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
//            String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
//Log.d(TAG, name + " " + surname);
//cursor.moveToNext();
//        }
//        cursor.close();
//        AppDataBase db = App.getInstance().getDataBase();
//        ContactDao contactDao = db.contactDao();
//        contactDao.insert(myList);
//        Log.d(TAG, "create database");
//        List<Contact> list = contactDao.getAll();
//        Log.d(TAG, "list size = " + list.size());
//        for(int i = 0; i < myList.size(); i++) {
//            System.out.println(myList.get(i));
//        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void putKey(int key) {

        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
       Contact contact = contactDao.getById(key);
       Log.d(TAG, "key in getByID " + key);
       id = key;
        mSelectionArgs[0] = contact.getLookupKey();
    }
}
