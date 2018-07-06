package com.example.vladimir.contactreader.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vladimir.contactreader.App;
import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.presenter.DetailsPresenter;
import com.example.vladimir.contactreader.view.DetailsView;

import javax.inject.Inject;
import javax.inject.Provider;


public class DetailsFragment extends MvpAppCompatFragment implements DetailsView {

    private static final String TAG = "TAG";
    @InjectPresenter
    DetailsPresenter detailsPresenter;

    @Inject
    public Provider<DetailsPresenter> contactpresenterProvider;

    @ProvidePresenter
    DetailsPresenter provideDetailsPresenter() {
        return contactpresenterProvider.get();
    }


    public final String INDEX = "index";
    private TextView phoneText;
    private TextView emailText;
    private TextView nameText;
    private TextView surnameText;
    private TextView addressText;
    private Button setLocation;

    private setLocation callback;
    private long key;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App app = (App) getActivity().getApplication();
        app.getContactComponent().inject(this);

        try {
            callback = (setLocation) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        phoneText = rootView.findViewById(R.id.textPhone);
        emailText = rootView.findViewById(R.id.textEmail);
        nameText = rootView.findViewById(R.id.textName);
        surnameText = rootView.findViewById(R.id.textFamily);
        addressText = rootView.findViewById(R.id.textAddress);
        setLocation = rootView.findViewById(R.id.setLocation);

        setLocation.setOnClickListener(view -> startMap(key));
        Bundle args = getArguments();
        if (args != null) {
             key = args.getLong(INDEX);
            detailsPresenter.getKeyItem(key);

        }
        return rootView;
    }

    public interface setLocation{
        void startMapForSingleContact(long id);
    }

    public void startMap(Long id){
        Log.d(TAG, "id = " + id);
        callback.startMapForSingleContact(id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setDetailsName(String name, String surname) {
        nameText.setText(name);
        surnameText.setText(surname);
    }

    @Override
    public void setDetailsPhone(String phone) {
        phoneText.setText(phone);
    }

    @Override
    public void setDetailsEmail(String email) {
        emailText.setText(email);
    }

    @Override
    public void setAddress(String address) {
        addressText.setText(address);
    }

    @Override
    public void onResume() {
        super.onResume();
       // Log.d(TAG, "onResume detailsFragment");
    }
}





