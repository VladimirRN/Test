package com.example.vladimir.contactreader;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity
public class Contact {
    public Contact(@NonNull Long id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public Long id;

//    public Contact(String id, String displayName) {
//        this.id = id;
//        this.displayName = displayName;
//    }

//    public Contact(String displayName) {
//        this.displayName = displayName;
//    }

    public String displayName;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String name;

    public String surname;

    public String phone;

    public String email;

    public String geo;

    public String address;


}
