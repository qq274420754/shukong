package com.lzm.shukong.presenter.contract;

import com.lzm.shukong.base.BasePresenter;
import com.lzm.shukong.base.BaseView;
import com.lzm.shukong.modul.bean.meipai.MeipaiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface MeipaiListContract {
    
    interface View extends BaseView{
    
        void showContent(List<MeipaiBean> list);
        
        
        void showMoreContent(List<MeipaiBean> list , int startPage,int entPage);
    }
    interface persenter extends BasePresenter<View>{
        
        void getMeipaiDate(String topic);
        
        void getMoreMeipaiDate();
    }
}
