package com.lzm.shukong.modul.bean.meipai;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/2/19.
 */

public class meipaiManagerItemBean extends RealmObject implements Parcelable {
    
    private int index;

    private boolean isSelect;
    
    public meipaiManagerItemBean() {

    }

    public meipaiManagerItemBean(int index, boolean isDelect) {
        this.index = index;
        this.isSelect = isDelect;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean select) {
        isSelect = select;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected meipaiManagerItemBean(Parcel in) {
        this.index = in.readInt();
        this.isSelect = in.readByte() != 0;
    }

    public static final Creator<meipaiManagerItemBean> CREATOR = new Creator<meipaiManagerItemBean>() {
        @Override
        public meipaiManagerItemBean createFromParcel(Parcel source) {
            return new meipaiManagerItemBean(source);
        }

        @Override
        public meipaiManagerItemBean[] newArray(int size) {
            return new meipaiManagerItemBean[size];
        }
    };
}
