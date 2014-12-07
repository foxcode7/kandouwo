package com.kindleren.kandouwo.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.ui.AbsoluteDialogFragment;
import com.kindleren.kandouwo.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by foxcoder on 14-12-7.
 */
public class MoreDiaglogFragment extends AbsoluteDialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View blockMoreMenu;
    ListView menuListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_more_list, container, false);

        menuListView = (ListView) view.findViewById(R.id.list);
        menuListView.setAdapter(getMoreMenuAdapter());
        menuListView.setOnItemClickListener(this);

        blockMoreMenu = view.findViewById(R.id.block_more_menu);
        blockMoreMenu.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.block_more_menu:
                removeSelf();
                break;
        }
    }

    private ListAdapter getMoreMenuAdapter(){
        List<Map<String, Object>> moreMenus = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("image", R.drawable.more_menu_account);
        map.put("action", "游客xxxx");
        moreMenus.add(map);
        map = new HashMap<String, Object>();
        map.put("image", R.drawable.more_menu_settings);
        map.put("action", "设置");
        moreMenus.add(map);
        map = new HashMap<String, Object>();
        map.put("image", R.drawable.more_menu_feedback);
        map.put("action", "意见反馈");
        moreMenus.add(map);
        map = new HashMap<String, Object>();
        map.put("image", R.drawable.more_menu_sign_in);
        map.put("action", "每日签到");
        moreMenus.add(map);

        SimpleAdapter listAdapter = new SimpleAdapter(getActivity(), moreMenus, R.layout.listitem_more_list,
                new String[]{"image","action"},
                new int[]{R.id.image,R.id.action});

        return listAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position){
            case 0://用户名
                break;
            case 1://设置
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                break;
            case 2://反馈意见
                break;
            case 3://每日签到
                break;
        }
        removeSelf();
    }
}
