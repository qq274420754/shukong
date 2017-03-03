package com.lzm.shukong.presenter.contract;

import com.lzm.shukong.base.BasePresenter;
import com.lzm.shukong.base.BaseView;

/**
 * Created by Administrator on 2017/2/12.
 */

public interface MainContract {
    
    interface View extends BaseView {

        void showUpdateDialog(String versionContent);

        
    }

    interface  Presenter extends BasePresenter<View> {

        void checkVersion(String currentVersion);
        
       
    }
}
