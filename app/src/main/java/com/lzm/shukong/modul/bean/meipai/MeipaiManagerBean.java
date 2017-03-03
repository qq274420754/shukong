package com.lzm.shukong.modul.bean.meipai;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/2/19.
 */

public class MeipaiManagerBean extends RealmObject implements Parcelable {
    
    public MeipaiManagerBean() {
    }
    
    private RealmList<meipaiManagerItemBean> managerList;

    public RealmList<meipaiManagerItemBean> getManagerList(){
        return managerList;
    }
    
    public void setManagerList (RealmList<meipaiManagerItemBean> managerList){
        this.managerList = managerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.managerList);
    }

    protected MeipaiManagerBean(Parcel in) {
        this.managerList = new RealmList<>();
        in.readList(this.managerList,meipaiManagerItemBean.class.getClassLoader());
    }
    public MeipaiManagerBean(RealmList<meipaiManagerItemBean> mList) {
        this.managerList = mList;
    }

    public static final Creator<MeipaiManagerBean> CREATOR = new Creator<MeipaiManagerBean>() {
        @Override
        public MeipaiManagerBean createFromParcel(Parcel source) {
            return new MeipaiManagerBean(source);
        }

        @Override
        public MeipaiManagerBean[] newArray(int size) {
            return new MeipaiManagerBean[size];
        }
    };
}
