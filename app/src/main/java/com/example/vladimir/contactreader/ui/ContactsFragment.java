package com.example.vladimir.contactreader.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vladimir.contactreader.Contact;
import com.example.vladimir.contactreader.CustomAdapter;
import com.example.vladimir.contactreader.MyDecoration;
import com.example.vladimir.contactreader.R;
import com.example.vladimir.contactreader.presenter.ContactPresenter;
import com.example.vladimir.contactreader.presenter.MainPresenter;
import com.example.vladimir.contactreader.view.ContactView;
import com.example.vladimir.contactreader.view.MainActivityView;

import java.util.List;


public class ContactsFragment extends MvpAppCompatFragment implements
        ContactView, MainActivityView {

    private static final String TAG = "TAG";
    @InjectPresenter(type = PresenterType.GLOBAL, tag = "mainPresenter")
    MainPresenter mainPresenter;

    @InjectPresenter
    ContactPresenter contactPresenter;
    @ProvidePresenter
    ContactPresenter provideContactPresenter() {
        return new ContactPresenter(getContext(), getLoaderManager());
    }


    private CustomAdapter customAdapter;
    private boolean isTablet;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomAdapter.ItemClickListener itemClickListener = new CustomAdapter.ItemClickListener() {

            // Каким образом лучше организовать передачу данных из одного фрагмента в другой в MVP, общаться между
            // собой должны презенторы?, передавать каким то образом данные напрямую куда нужно?.

            // Сейчас у меня по клику на списке контактов  данные идут  через глобальный презентор MainPresenter от ContactFragment к Activity далее
            // через Bundle попадают в DetailsFragment и уже оттуда через DetailsPresenter в DetailsModel что как мне кажется неправильно.

            @Override
            public void onItemClick(int idItem) {
                isTablet = getResources().getBoolean(R.bool.isTablet);
                if (isTablet) {
                    mainPresenter.itemClickInTablet(idItem);
                } else {
                    mainPresenter.itemClickInPhone(idItem);
                }
            }
        };

        customAdapter = new CustomAdapter(itemClickListener);
        Log.d(TAG, "constructor ");
        recyclerView.setAdapter(customAdapter);
        MyDecoration myDecoration = new MyDecoration(getContext());
        recyclerView.addItemDecoration(myDecoration);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showContacts(List<Contact> contact) {
        customAdapter.setContacts(contact);

    }

    @Override
    public void startDetailsFragmentForPhone(int itemKey) {
    }

    @Override
    public void startDetailsFragmentForTablet(int itemKey) {
    }
}






