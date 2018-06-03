package com.example.vladimir.contactreader.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Contact {
    public Contact(@NonNull Long id, String displayName, String lookupKey, String name, String surname, String phone, String email, String geo, String address, String mimetype) {
        this.id = id;
        this.displayName = displayName;
        this.lookupKey = lookupKey;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.geo = geo;
        this.address = address;
        this.mimetype = mimetype;
    }

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "displayName")
    private String displayName;
    @ColumnInfo(name = "lookupKey")
    private String lookupKey;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "surname")
    private String surname;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "geo")
    private String geo;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "mimetype")
    private String mimetype;


    public String getLookupKey() {
        return lookupKey;
    }

    private void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }

    private String getMimetype() {
        return mimetype;
    }

    private void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    private void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    private void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    private void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    private void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private String getGeo() {
        return geo;
    }

    private void setGeo(String geo) {
        this.geo = geo;
    }

    private String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }
}
