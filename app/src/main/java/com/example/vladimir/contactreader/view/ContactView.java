package com.example.vladimir.contactreader.view;

import android.database.Cursor;

import com.arellomobile.mvp.MvpView;
import com.example.vladimir.contactreader.Contact;

import java.util.List;

public interface ContactView extends MvpView {
    void showContacts(List<String> list);
}
