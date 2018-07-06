package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.app.App;
import com.example.vladimir.contactreader.presentation.contact.ContactFragment;
import com.example.vladimir.contactreader.presentation.contacts.ContactsFragment;
import com.example.vladimir.contactreader.presentation.map.MapFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataBaseModule.class, ContactModule.class, ContactListModule.class, MapModule.class, NetworkModule.class})
public interface ContactComponent {
    void inject(ContactFragment detailsFragment);
    void inject(ContactsFragment contactsFragment);
    void inject(App app);
    void inject(MapFragment mapFragment);

}
