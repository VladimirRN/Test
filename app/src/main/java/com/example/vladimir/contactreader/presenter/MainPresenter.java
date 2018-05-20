package com.example.vladimir.contactreader.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladimir.contactreader.view.MainActivityView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainActivityView> {
    private String TAG = "TAG";

    public void itemClickInPhone(String key){
        getViewState().startDetailsFragmentForPhone(key);
        Log.d(TAG, "presenter click");
    }

    public void itemClickInTablet(String key) {
        getViewState().startDetailsFragmentForTablet(key);
    }
}
