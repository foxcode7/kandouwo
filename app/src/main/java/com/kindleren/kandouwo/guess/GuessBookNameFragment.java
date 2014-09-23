package com.kindleren.kandouwo.guess;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.inject.Inject;
import com.kandouwo.model.datarequest.Request;
import com.kandouwo.model.datarequest.douban.DoubanBookInfo;
import com.kandouwo.model.datarequest.douban.DoubanSearchBook;
import com.kandouwo.model.datarequest.guess.GuessBookNameRuquest;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.base.RequestLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuezhangbin on 14-9-22.
 */
public class GuessBookNameFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<DoubanSearchBook> {

    @Inject
    private LayoutInflater inflater;
    private Picasso picasso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guess_book_name, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<DoubanSearchBook> onCreateLoader(int id, Bundle args) {
        return new RequestLoader(getActivity(), new GuessBookNameRuquest("莫言"), Request.Origin.NET);
    }

    @Override
    public void onLoadFinished(Loader<DoubanSearchBook> loader, DoubanSearchBook data) {
        if (data != null) {
            List<DoubanBookInfo> books = data.getBooks();
            List<String> images = new ArrayList<String>();
            int i=0;
            for (DoubanBookInfo book : books) {
                String imageUrl = book.getImages().getLarge();
                images.add(i,imageUrl);
                i++;
            }

            String imageUrl = images.get(0);
            ImageView urlImage = (ImageView)getView().findViewById(R.id.imageurl);
            picasso.with(getActivity()).load(imageUrl).into(urlImage);
        }
    }

    @Override
    public void onLoaderReset(Loader<DoubanSearchBook> loader) {

    }
}
