package com.kandouwo.model.datarequest.search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuezhangbin on 14/10/29.
 */
public class HotWord implements Parcelable {
    public String name;
    public boolean isHot;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isHot ? 1 : 0));
    }


    public static final Parcelable.Creator<HotWord> CREATOR
            = new Parcelable.Creator<HotWord>() {
        public HotWord createFromParcel(Parcel in) {
            HotWord hotWord = new HotWord();
            hotWord.name = in.readString();
            hotWord.isHot = in.readByte() != 0;
            return hotWord;
        }

        public HotWord[] newArray(int size) {
            return new HotWord[size];
        }
    };
}
