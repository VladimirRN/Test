package com.example.vladimir.contactreader.data;

import com.example.vladimir.contactreader.data.Contact;

import java.util.List;

import io.reactivex.Observable;

public interface ContactLoading {
    Observable<List<Contact>> getContacts();
    Observable<String> getDeatailsContact(Contact contact);
}
