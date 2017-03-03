package com.lzm.shukong.presenter.contract;

import com.lzm.shukong.base.BasePresenter;
import com.lzm.shukong.base.BaseView;
import com.lzm.shukong.modul.bean.zhihubean.DailyBeforeListBean;
import com.lzm.shukong.modul.bean.zhihubean.DailyListBean;

/**
 * Created by Administrator on 2017/2/26.
 */

public interface DailyContract {
    
    interface view extends BaseView{
        
        void showContent(DailyListBean dailyListBean);

        void addMoreContent(DailyBeforeListBean dailyBeforeListBean);
    }
    
    interface presenter extends BasePresenter<view>{
        
        void getDailyData();
        
        void getMoreDailyData(String date);
        
    }
}
