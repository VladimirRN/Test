package com.example.vladimir.contactreader.presentation.contacts;

import com.arellomobile.mvp.MvpView;
import com.example.vladimir.contactreader.data.Contact;

import java.util.List;

public interface ContactsView extends MvpView {
    void showContacts(List<Contact> list);
    void showProgress();
    void hideProgress();
}
