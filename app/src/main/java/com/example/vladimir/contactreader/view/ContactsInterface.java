package com.example.vladimir.contactreader.view;

import java.util.List;

import io.reactivex.Observable;

public interface ContactsInterface {
    public Observable<List<String>> getContacts();
}
