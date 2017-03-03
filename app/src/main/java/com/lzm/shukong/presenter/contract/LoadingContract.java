package com.lzm.shukong.presenter.contract;

import android.graphics.Bitmap;

import com.lzm.shukong.base.BasePresenter;
import com.lzm.shukong.base.BaseView;

/**
 * Created by Administrator on 2017/2/26.
 */

public interface LoadingContract {
    
    interface view extends BaseView{
        
        void showContent(Bitmap bitmap );

        void jumpToMain();
    }
    
    interface presenter extends BasePresenter<view>{

        void getLoadingImgData();
        
    }
}
