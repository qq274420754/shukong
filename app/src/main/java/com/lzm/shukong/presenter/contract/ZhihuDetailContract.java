package com.lzm.shukong.presenter.contract;


import com.lzm.shukong.base.BasePresenter;
import com.lzm.shukong.base.BaseView;
import com.lzm.shukong.modul.bean.zhihubean.DetailExtraBean;
import com.lzm.shukong.modul.bean.zhihubean.ZhihuDetailBean;

/**
 * Created by codeest on 16/8/13.
 */

public interface ZhihuDetailContract {

    interface View extends BaseView {

        void showContent(ZhihuDetailBean zhihuDetailBean);

        void showExtraInfo(DetailExtraBean detailExtraBean);

        void setLikeButtonState(boolean isLiked);
    }

    interface  Presenter extends BasePresenter<View> {

        void getDetailData(int id);

        void getExtraData(int id);

        void insertLikeData();

        void deleteLikeData();

        void queryLikeData(int id);
    }
}
