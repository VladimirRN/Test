package com.example.vladimir.contactreader;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    public long id;

    public String name;

    public String surname;

    public String phone;

    public String email;

    public String geo;

    public String address;
}
