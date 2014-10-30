package com.kindleren.kandouwo.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kandouwo.model.datarequest.search.HotWord;
import com.kindleren.kandouwo.R;
import com.kindleren.kandouwo.base.BaseFragment;
import com.kindleren.kandouwo.common.config.BaseConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wuzhi on 14-5-4.
 */
public class HotWordsFragment extends BaseFragment {

    public static HotWordsFragment newInstance(List<HotWord> hotWords) {
        Log.e("settext", "HotWordsFragment");
        HotWordsFragment fragment = new HotWordsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("hot_words", (java.util.ArrayList<? extends android.os.Parcelable>) hotWords);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.e("tag", "new onCreateView");
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(containerParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        List<HotWord> hotWords = adjustHotWords(getArguments().<HotWord>getParcelableArrayList("hot_words"));
        if (hotWords==null || hotWords.isEmpty()) {
            return linearLayout;
        }
        int count = 0;
        for (int i = 0; i < hotWords.size(); i++) {
            LinearLayout rowLayout = new LinearLayout(getActivity());
            while (count < COUNT_PER_ROW ) {
                HotWord hotWord = null;
                if (i < hotWords.size()) {
                    hotWord = hotWords.get(i);
                }
                TextView textView = (TextView) inflater.inflate(R.layout.hot_search_word, null);
                int spcae = hotWord == null ? 1 : HotWordsController.getSpace(hotWord.name);
                count += spcae;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, spcae);
                if (hotWord != null) {
                    Log.e("settext", hotWord.name);

                    textView.setText(hotWord.name);
                    if (!hotWord.isHot) {
                        textView.setClickable(true);
                        final HotWord finalHotWord = hotWord;
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    } else {
                        textView.setClickable(false);
                        textView.setTextColor(getResources().getColor(R.color.search_hot_word));
                    }
                }
                rowLayout.addView(textView, layoutParams);
                if (hotWord != null) {
                    addVerticalDivider(rowLayout);
                }

                if (count < COUNT_PER_ROW) {
                    i++;
                }
            }
            count = 0;
            linearLayout.addView(rowLayout);
            if (i < hotWords.size() - 1) {
                addHorizontalDivider(linearLayout);
            }
        }
        return linearLayout;
    }

    private static final int COUNT_PER_ROW = 4;

    /**
     * 调整list的顺序，保证每行热词所占长度都为4
     * @param hotWords
     * @return
     */
    private List<HotWord> adjustHotWords(ArrayList<HotWord> hotWords) {
        if (hotWords == null || hotWords.isEmpty()) {
            return null;
        }
        int count = 0;
        for (int i = 0; i < hotWords.size(); i++) {
            HotWord hotWord = hotWords.get(i);
            count += HotWordsController.getSpace(hotWord.name);
             if (count > COUNT_PER_ROW) {
                count -= HotWordsController.getSpace(hotWord.name);
                for (int j = i + 1; j < hotWords.size(); j++) {
                    if ((count + HotWordsController.getSpace(hotWords.get(j).name)) <= COUNT_PER_ROW) {
                        count += HotWordsController.getSpace(hotWords.get(j).name);
                        Collections.swap(hotWords, i, j);
                    }
                }
            }
            if (count == COUNT_PER_ROW) {
                count = 0;
            }
        }
        return hotWords;
    }

    private void addVerticalDivider(LinearLayout rowLayout) {
        View divider = new View(getActivity());
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
        dividerParams.topMargin = BaseConfig.dp2px(8);
        dividerParams.bottomMargin = BaseConfig.dp2px(8);
        divider.setBackgroundResource(R.color.gray_light);
        rowLayout.addView(divider, dividerParams);
    }

    private void addHorizontalDivider(LinearLayout linearLayout) {
        View divider = new View(getActivity());
        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
        dividerParams.leftMargin = BaseConfig.dp2px(10);
        dividerParams.rightMargin = BaseConfig.dp2px(10);
        dividerParams.topMargin = BaseConfig.dp2px(-1);
        divider.setBackgroundResource(R.color.gray_light);
        linearLayout.addView(divider,dividerParams);
    }
}
