package com.kindleren.kandouwo.hot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.common.config.BaseConfig;
import com.kindleren.kandouwo.utils.ListUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import roboguice.inject.InjectView;
import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by foxcoder on 14-9-22.
 */
public class HotFragment extends BaseFragment implements
        OnRefreshListener, View.OnClickListener {
    @Inject
    private LayoutInflater inflater;

    @InjectView(R.id.ad_indicator)
    private CirclePageIndicator adPageIndicator;

    private PullToRefreshLayout mPullToRefreshLayout;
    private AutoScrollViewPager viewPager;
    private TextView indexText;
    private List<Integer> imageIdList;
    private ImageView closeAdsBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        viewPager = (AutoScrollViewPager) getView().findViewById(R.id.ad_view);
        indexText = (TextView) getView().findViewById(R.id.view_pager_index);

        imageIdList = new ArrayList<Integer>();
        imageIdList.add(R.drawable.ad_one);
        imageIdList.add(R.drawable.ad_two);
        imageIdList.add(R.drawable.ad_three);
        imageIdList.add(R.drawable.ad_four);

        viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imageIdList).setInfiniteLoop(false));
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

        closeAdsBtn = (ImageView) getView().findViewById(R.id.close_ads);
        closeAdsBtn.setOnClickListener(this);

        showAdPageIndicator();
    }

    private void showAdPageIndicator(){
        if (viewPager.getAdapter().getCount() > 1) {
            adPageIndicator.setViewPager(viewPager);
            adPageIndicator.setFillColor(getResources().getColor(R.color.blue));
            adPageIndicator.setPageColor(getResources().getColor(R.color.gray_light));
            adPageIndicator.setRadius(BaseConfig.dp2px(4));
            adPageIndicator.setVisibility(View.VISIBLE);
        }else {
            adPageIndicator.setVisibility(View.GONE);
        }
    }

    @Override
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

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
                    .append(ListUtils.getSize(imageIdList)));
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int arg0) {}
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
