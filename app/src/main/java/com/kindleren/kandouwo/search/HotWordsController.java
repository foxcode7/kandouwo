package com.kindleren.kandouwo.search;

import android.text.TextUtils;

import com.kandouwo.model.datarequest.search.HotWord;

import java.util.ArrayList;
import java.util.List;

public class HotWordsController {

    public static int getSpace(String name) {
        if (TextUtils.isEmpty(name)) {
            return 0;
        }
        return getWordCount(name.trim()) > 8 ? 2 : 1;
    }

    public static int getWordCount(String s)
    {
        int length = 0;
        for(int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255)
                length++;
            else
                length += 2;

        }
        return length;

    }

    private static final int WORDS_PER_COLUMN = 12;//每个fragment有3行4列

    public static List<List<HotWord>> getFragmentCounts(List<HotWord> hotWords) {
        if (hotWords==null || hotWords.isEmpty()) {
            return new ArrayList<List<HotWord>>();
        }

        int count = 0;
        List<List<HotWord>> fragmentHotwords = new ArrayList<List<HotWord>>();
        List<HotWord> hotWordList = new ArrayList<HotWord>();
        for (int i = 0; i < hotWords.size(); i++) {
            HotWord hotWord = hotWords.get(i);
            count += getSpace(hotWord.name);
            hotWordList.add(hotWord);
            if (count > WORDS_PER_COLUMN) {
                count = 0;
                hotWordList.remove(hotWordList.size() - 1);
                i--;
                fragmentHotwords.add(cloneList(hotWordList));
                hotWordList.clear();
            }
            if (count == WORDS_PER_COLUMN || i == hotWords.size() - 1) {
                count = 0;
                fragmentHotwords.add(cloneList(hotWordList));
                hotWordList.clear();
            }
        }
        return  fragmentHotwords;
    }

    public static List<HotWord> cloneList(List<HotWord> list) {
        List<HotWord> clone = new ArrayList<HotWord>(list.size());
        for (HotWord item : list) {
            HotWord hotWord = new HotWord();
            hotWord.isHot = item.isHot;
            hotWord.name = item.name;
            clone.add(hotWord);
        }
        return clone;
    }
}
