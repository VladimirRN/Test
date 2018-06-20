package com.example.vladimir.contactreader.model;

public interface DetailsInterface {

    interface onDetailsListener {
        void getDataEmail(String email);

        void getDataName(String name, String surname);

        void getDataPhone(String phone);
    }

    void getData(onDetailsListener onDetailsListener, Long key);
}
