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

import java.util.Arrays;
import java.util.List;

public class DetailsModel implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "TAG";
    private final String SORT_ORDER = ContactsContract.Data.MIMETYPE;
    private final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";

    private final String[] PROJECTION = {
            ContactsContract.Contacts.Data.MIMETYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME};
    private Context context;
    private LoaderManager loaderManager;
    private String[] selectionArgs = {""};

    public DetailsModel(Context context, LoaderManager loaderManager) {
        this.context = context;
        this.loaderManager = loaderManager;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "massiv oncreateloader " + Arrays.toString(selectionArgs));
        Log.d(TAG, "oncreate loader selection string =  " + SELECTION);
        return new CursorLoader(
                context,
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                SELECTION,
                selectionArgs,
                SORT_ORDER
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        AppDataBase db = App.getInstance().getDataBase();
        Log.d(TAG, "Load finish " + cursor.getCount());
        ContactDao contactDao = db.contactDao();
        List<Contact> listContact = contactDao.getAll();
        int idLoad = loader.getId();
        long x = listContact.get(idLoad).getId();
        Log.d(TAG, "id contact in loadfinish = " + x);
        if (cursor.moveToFirst()) {
            do {
                String mime = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                switch (mime) {
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                        String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        Log.d(TAG, "name and surname = " + name + " " + surname);
                        contactDao.updateName(name, (int) x);
                        contactDao.updateSurname(surname, (int) x);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d(TAG, "number= " + number);
                        contactDao.updatePhone(number, (int) x);
                        break;
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                        contactDao.updateEmail(address, (int) x);
                        Log.d(TAG, "address =" + address);
                        break;
                }

            } while (cursor.moveToNext());

        }
        onLoadCallback(loader.getId());
    }

    public void onLoadCallback(int idLoader) {
        Log.d(TAG, "id Loader = " + idLoader);
        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        List<Contact> listContact = contactDao.getAll();
        if (idLoader < listContact.size() - 1) {
            int id = idLoader + 1;
            String key = listContact.get(id).getLookupKey();
            selectionArgs[0] = key;
            loaderManager.restartLoader(id, null, this);
        } else {
            List<Contact> newListContact = contactDao.getAll();
            for (int i = 0; i < newListContact.size(); i++) {
                String phone = newListContact.get(i).getPhone();
                String l = newListContact.get(i).getLookupKey();
                String email = newListContact.get(i).getEmail();
                String name = newListContact.get(i).getName();
                //Log.d(TAG, "phone from db = " + phone + " lookyp = " + l + "email  = " + email + " " + name);
            }
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void startDetailLoader() {
        AppDataBase db = App.getInstance().getDataBase();
        ContactDao contactDao = db.contactDao();
        List<Contact> listContact = contactDao.getAll();
        Log.d(TAG, "list size =  " + listContact.size());
        String idd = listContact.get(0).getLookupKey();
        selectionArgs[0] = idd;
        loaderManager.initLoader(0, null, this);
    }
}


