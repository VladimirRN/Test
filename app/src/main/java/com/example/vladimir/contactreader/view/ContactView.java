package com.example.vladimir.contactreader.view;

import android.database.Cursor;

import com.arellomobile.mvp.MvpView;

public interface ContactView extends MvpView {
    void showContacts(Cursor cursor);
}
