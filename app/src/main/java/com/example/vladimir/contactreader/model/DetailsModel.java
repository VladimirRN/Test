package com.example.vladimir.contactreader.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.vladimir.contactreader.presenter.DetailsPresenter;
import com.example.vladimir.contactreader.view.DetailsModelInterface;

public class DetailsModel implements LoaderManager.LoaderCallbacks<Cursor>, DetailsModelInterface {

    private final String SORT_ORDER = ContactsContract.Data.MIMETYPE;
    private final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";
    private final String[] PROJECTION = {
                    ContactsContract.Contacts.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Email.DATA,
                    ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                    ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME};
    private final DetailsPresenter detailsPresenter;
    private  Context context;
    private LoaderManager loaderManager;
    private String[] mSelectionArgs = {""};

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
        if (cursor.moveToFirst()) {
            do {
                String mime = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                switch (mime) {
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                        String surname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        detailsPresenter.getDataName(name, surname);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        detailsPresenter.getDataPhone(number);
                        break;
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        String address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                        detailsPresenter.getDataEmail(address);
                        break;
                }

            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void putKey(String key) {
        mSelectionArgs[0] = key;
    }
}
