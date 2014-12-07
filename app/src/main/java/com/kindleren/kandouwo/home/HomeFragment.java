package com.kindleren.kandouwo.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.inject.Inject;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.common.views.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by foxcoder on 14-9-22.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG_DIALOG_MORE_MENU = "more_menu_fragment";

    @Inject
    private LayoutInflater inflater;

    private ViewPager viewPager;
    private PagerSlidingTabStrip pstHome;
    private ImageView moreImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        pstHome = (PagerSlidingTabStrip) view.findViewById(R.id.pst_home);
        moreImageView = (ImageView) view.findViewById(R.id.iv_more);
        moreImageView.setOnClickListener(this);

        List<TabInfo> list = new ArrayList<TabInfo>();
        list.add(new TabInfo(MyShelfFragment.class, null, "我的书架"));
        list.add(new TabInfo(LocalShelfFragment.class, null, "导入藏书"));

        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getActivity(), getChildFragmentManager(), list);
        viewPager.setAdapter(adapter);
        pstHome.setViewPager(viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_more:
                showMoreMenuDialogFragment();
                break;
        }
    }

    private void showMoreMenuDialogFragment(){
        getChildFragmentManager().popBackStack();
        Fragment fragment = getChildFragmentManager().findFragmentByTag(TAG_DIALOG_MORE_MENU);
        if(fragment != null){
            getChildFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        } else {
            fragment = new MoreDiaglogFragment();
            getChildFragmentManager().beginTransaction().
                    addToBackStack(TAG_DIALOG_MORE_MENU).
                    replace(R.id.menu_area, fragment, TAG_DIALOG_MORE_MENU)
                    .commitAllowingStateLoss();
        }
    }

    private static class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        private List<TabInfo> list;

        public HomeFragmentPagerAdapter(Context c, FragmentManager fm, List<TabInfo> l) {
            super(fm);
            context = c;
            list = l;
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = list.get(position);
            return Fragment.instantiate(context, info.clss.getName(), info.bundle);
        }

        @Override
        public int getCount() {
            if (list == null)
                return 0;
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).title;
        }
    }


    private static class TabInfo {
        Class<?> clss;
        Bundle bundle;
        String title;

        public TabInfo(Class<?> c, Bundle b, String s) {
            clss = c;
            bundle = b;
            title = s;
        }
    }
}
