package com.kindleren.kandouwo.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.inject.Inject;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by foxcoder on 14-11-7.
 */
public class MyShelfFragment extends BaseFragment {
    private String texts[] = null;
    private int images[] = null;

    @Inject
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_shelf, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        images=new int[]{
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big,
                R.drawable.book_one_big,R.drawable.book_one_big};
        texts = new String[]{ "宫式布局1", "宫式布局2",
                "宫式布局3", "宫式布局4",
                "宫式布局5", "宫式布局6",
                "宫式布局7", "宫式布局8",
                "宫式布局9", "宫式布局8",
                "宫式布局11", "宫式布局8",
                "宫式布局13", "宫式布局8",
                "宫式布局15", "宫式布局8",
                "宫式布局17", "宫式布局8",
                "宫式布局19", "宫式布局8",
                "宫式布局21", "宫式布局8"};
        final GridView gridview = (GridView) getView().findViewById(R.id.gridview);
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 22; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", images[i]);
            map.put("itemText", texts[i]);
            lstImageItem.add(map);
        }

        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                lstImageItem,// 数据源
                R.layout.home_book_image_gridview,// 显示布局
                new String[] { "itemImage", "itemText" },
                new int[] { R.id.itemImage, R.id.itemText });
        gridview.setAdapter(saImageItems);
        gridview.setOnItemClickListener(new ItemClickListener());
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        /**
         * 点击项时触发事件
         *
         * @param parent  发生点击动作的AdapterView
         * @param view 在AdapterView中被点击的视图(它是由adapter提供的一个视图)。
         * @param position 视图在adapter中的位置。
         * @param rowid 被点击元素的行id。
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowid) {
            HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
            //获取数据源的属性值
            String itemText=(String)item.get("itemText");
            Object object=item.get("itemImage");
            Toast.makeText(getActivity(), itemText, Toast.LENGTH_LONG).show();

//            //根据图片进行相应的跳转
//            switch (images[position]) {
//                case R.drawable.p1:
//                    startActivity(new Intent(GvActivity.this, TestActivity1.class));//启动另一个Activity
//                    finish();//结束此Activity，可回收
//                    break;
//                case R.drawable.p2:
//                    startActivity(new Intent(GvActivity.this, TestActivity2.class));
//                    finish();
//                    break;
//                case R.drawable.p3:
//                    startActivity(new Intent(GvActivity.this, TestActivity3.class));
//                    finish();
//                    break;
//            }

        }
    }
}
