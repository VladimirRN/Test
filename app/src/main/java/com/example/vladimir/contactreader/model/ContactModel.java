package com.example.vladimir.contactreader.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.vladimir.contactreader.presenter.ContactPresenter;

public class ContactModel implements LoaderManager.LoaderCallbacks<Cursor> {
    private final String[] PROJECTION = {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
    private  Context context;
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
        presenter.showContacts(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
