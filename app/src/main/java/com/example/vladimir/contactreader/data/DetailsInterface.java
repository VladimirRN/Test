package com.example.vladimir.contactreader.data;

public interface DetailsInterface {

    interface onDetailsListener {
        void getDataEmail(String email);

        void getDataName(String name, String surname);

        void getDataPhone(String phone);

        void getAddress(String address);
    }

    void getData(onDetailsListener onDetailsListener, Long key);
}
