package com.example.vladimir.contactreader.view;

import com.arellomobile.mvp.MvpView;
import com.example.vladimir.contactreader.model.db.Contact;

import java.util.List;

public interface ContactView extends MvpView {
    void showContacts(List<Contact> list);
    void showProgress();
    void hideProgress();
}
