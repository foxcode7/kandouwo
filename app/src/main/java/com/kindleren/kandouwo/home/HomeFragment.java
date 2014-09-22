package com.kindleren.kandouwo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.inject.Inject;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.search.SearchBookKeywordsActivity;

/**
 * Created by foxcoder on 14-9-22.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @Inject
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView() != null) {
            Button searchBookBtn = (Button) getView().findViewById(R.id.search_book_btn);
            searchBookBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_book_btn){
            Intent intent = new Intent();
            intent.setClass(getActivity(), SearchBookKeywordsActivity.class);
            startActivity(intent);
            return;
        }
    }
}
