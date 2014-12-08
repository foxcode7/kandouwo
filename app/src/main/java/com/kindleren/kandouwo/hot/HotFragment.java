package com.kindleren.kandouwo.hot;

<<<<<<< HEAD
=======
import android.os.AsyncTask;
>>>>>>> Revert d4cd5b8..4a13dad
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.inject.Inject;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.base.widget.HorizontalListView;
import com.kindleren.kandouwo.common.config.BaseConfig;
import com.kindleren.kandouwo.utils.ListUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import roboguice.inject.InjectView;
<<<<<<< HEAD
=======
import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
>>>>>>> Revert d4cd5b8..4a13dad

/**
 * Created by foxcoder on 14-9-22.
 */
<<<<<<< HEAD
public class HotFragment extends BaseFragment implements View.OnClickListener {
=======
public class HotFragment extends BaseFragment implements
        OnRefreshListener, View.OnClickListener {
>>>>>>> Revert d4cd5b8..4a13dad
    @Inject
    private LayoutInflater inflater;

    @InjectView(R.id.ad_indicator)
    private CirclePageIndicator adPageIndicator;

<<<<<<< HEAD
=======
    private PullToRefreshLayout mPullToRefreshLayout;
>>>>>>> Revert d4cd5b8..4a13dad
    private AutoScrollViewPager viewPager;
    private List<Integer> imageIdList;
    private ImageView closeAdsBtn;
    private HorizontalListView freeBookHorizontalListView;

    private FreeBookData[] freeBookDatas;


    //------------------------------ 假数据 -------------------------------
    private void initFreeBookData(){
        freeBookDatas = new FreeBookData[]{
            new FreeBookData(R.drawable.book_one_small, "C++ Primer1"),
            new FreeBookData(R.drawable.book_two_small, "C++ Primer2"),
            new FreeBookData(R.drawable.book_three_small, "C++ Primer3"),
            new FreeBookData(R.drawable.book_one_small, "C++ Primer1"),
            new FreeBookData(R.drawable.book_two_small, "C++ Primer2"),
            new FreeBookData(R.drawable.book_three_small, "C++ Primer3"),
        };
    }
    //--------------------------------------------------------------------

    private void setupFreeBookList(){
        initFreeBookData();
        FreeBookAdapter adapter = new FreeBookAdapter(getActivity(), freeBookDatas);
        freeBookHorizontalListView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

<<<<<<< HEAD
=======
        // Now find the PullToRefreshLayout to setup
        mPullToRefreshLayout = (PullToRefreshLayout) getView().findViewById(R.id.ptr_layout);

        // Now setup the PullToRefreshLayout
        ActionBarPullToRefresh.from(getActivity())
                // Mark All Children as pullable
                .allChildrenArePullable()
                        // Set a OnRefreshListener
                .listener(this)
                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);

>>>>>>> Revert d4cd5b8..4a13dad
        viewPager = (AutoScrollViewPager) getView().findViewById(R.id.ad_view);

        //------------------------假数据-------------------------
        imageIdList = new ArrayList<Integer>();
        imageIdList.add(R.drawable.ad_one);
        imageIdList.add(R.drawable.ad_two);
        imageIdList.add(R.drawable.ad_three);
        imageIdList.add(R.drawable.ad_four);
        //------------------------------------------------------

        viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imageIdList).setInfiniteLoop(false));

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

        closeAdsBtn = (ImageView) getView().findViewById(R.id.close_ads);
        closeAdsBtn.setOnClickListener(this);

        showAdPageIndicator();

        freeBookHorizontalListView = (HorizontalListView) getView().findViewById(R.id.free_book_list_view);
        setupFreeBookList();
    }

    /**
     * 展示广告栏的指示器
     */
    private void showAdPageIndicator(){
        if (viewPager.getAdapter().getCount() > 1) {
            adPageIndicator.setViewPager(viewPager);
            adPageIndicator.setFillColor(getResources().getColor(R.color.blue));
            adPageIndicator.setPageColor(getResources().getColor(R.color.gray_light));
<<<<<<< HEAD
            adPageIndicator.setStrokeWidth(0);
            adPageIndicator.setRadius(BaseConfig.dp2px(3));
=======
            adPageIndicator.setRadius(BaseConfig.dp2px(4));
>>>>>>> Revert d4cd5b8..4a13dad
            adPageIndicator.setVisibility(View.VISIBLE);
        }else {
            adPageIndicator.setVisibility(View.GONE);
        }
    }

    @Override
<<<<<<< HEAD
=======
    public void onRefreshStarted(View view) {
        // Hide the list
        // setListShown(false);

        /**
         * Simulate Refresh with 4 seconds sleep
         */
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                // Notify PullToRefreshLayout that the refresh has finished
                mPullToRefreshLayout.setRefreshComplete();
            }
        }.execute();
    }

    @Override
>>>>>>> Revert d4cd5b8..4a13dad
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_ads:
                if(getView() != null) {
                    viewPager.stopAutoScroll();
                    getView().findViewById(R.id.ad_container).setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        viewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume
        viewPager.startAutoScroll();
    }
}
