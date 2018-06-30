package com.example.vladimir.contactreader.di;

import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.ui.ContactsFragment;
import com.example.vladimir.contactreader.ui.DetailsFragment;
import com.example.vladimir.contactreader.ui.MapFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DetailsContactModule.class, ContactListModule.class, MapModule.class})
public interface ContactComponent {
    void inject(DetailsFragment detailsFragment);
    void inject(ContactsFragment contactsFragment);
    void inject(App app);
    void inject(MapFragment mapFragment);
}
