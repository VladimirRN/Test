package com.example.vladimir.contactreader.model;

import com.example.vladimir.contactreader.model.db.Contact;

import java.util.List;

import io.reactivex.Observable;

public interface ContactLoading {
    Observable<List<Contact>> getContacts();
    Observable<String> getDeatailsContact(Contact contact);
}
