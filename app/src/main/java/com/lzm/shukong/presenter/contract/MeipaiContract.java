package com.lzm.shukong.presenter.contract;

import com.lzm.shukong.base.BasePresenter;
import com.lzm.shukong.base.BaseView;
import com.lzm.shukong.modul.bean.meipai.MeipaiManagerBean;
import com.lzm.shukong.modul.bean.meipai.meipaiManagerItemBean;

import io.realm.RealmList;

/**
 * Created by Administrator on 2017/2/19.
 */

public interface MeipaiContract {
    
    interface View extends BaseView {
        
        void upDataTab(RealmList<meipaiManagerItemBean> meipaiBeanList);

        void jumpToManager(MeipaiManagerBean mBean);
    }

    interface Presenter extends BasePresenter<View> {
        
        void initManagerList();

        void setManagerList();
     
    }
}
