package com.example.vladimir.contactreader;

public class ContactObject {

    private int id;

    public ContactObject(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    private String displayName;
    private String name;
    private String surname;
    private String prone;
    private String email;
    private String geo;
    private String address;

    public ContactObject(int id,
                         String displayName,
                         String name,
                         String surname,
                         String prone,
                         String email,
                         String geo,
                         String address) {
        this.id = id;
        this.displayName = displayName;
        this.name = name;
        this.surname = surname;
        this.prone = prone;
        this.email = email;
        this.geo = geo;
        this.address = address;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
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

    public String getProne() {
        return prone;
    }

    public void setProne(String prone) {
        this.prone = prone;
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
}
