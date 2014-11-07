package com.kindleren.kandouwo.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;

/**
 * Created by foxcoder on 14-11-7.
 */
public class LocalShelfFragment extends BaseFragment {

    @Inject
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_shelf, container, false);
    }
}
