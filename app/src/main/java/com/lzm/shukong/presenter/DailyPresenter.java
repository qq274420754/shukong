package com.lzm.shukong.presenter;

import com.lzm.shukong.base.RxPresenter;
import com.lzm.shukong.modul.bean.zhihubean.DailyBeforeListBean;
import com.lzm.shukong.modul.bean.zhihubean.DailyListBean;
import com.lzm.shukong.modul.db.RealmHelper;
import com.lzm.shukong.modul.http.RetrofitHelper;
import com.lzm.shukong.presenter.contract.DailyContract;
import com.lzm.shukong.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/26.
 */

public class DailyPresenter extends RxPresenter<DailyContract.view> implements DailyContract.presenter{
    
    RetrofitHelper retrofitHelper;
    RealmHelper realmHelper;
    int topCount;

    @Inject
    public DailyPresenter(RetrofitHelper retrofitHelper,RealmHelper realmHelper) {
        this.retrofitHelper = retrofitHelper;
        this.realmHelper = realmHelper;
    }

    @Override
    public void getDailyData() {
        Subscription subscription = retrofitHelper.fetchDailyList()
                .compose(RxUtil.<DailyListBean>rxSchedulerHelper())
                .map(new Func1<DailyListBean, DailyListBean>() {
                    @Override
                    public DailyListBean call(DailyListBean dailyListBean) {
                        List<DailyListBean.TopStoriesBean> topStories = dailyListBean.getTop_stories();
                        List<DailyListBean.StoriesBean> stories = dailyListBean.getStories();
                        topCount = topStories.size();
                        for (DailyListBean.StoriesBean storie : stories){
                            storie.setReadState(realmHelper.queryReadStateBean(String .valueOf(storie.getId())));
                        }
                        return dailyListBean;
                    }
                }).subscribe(new Action1<DailyListBean>() {
                    @Override
                    public void call(DailyListBean dailyListBean) {
                        mView.showContent(dailyListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.toString());
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void getMoreDailyData(String date) {
        Subscription subscription = retrofitHelper.fetchDailyBeforeList(date)
                .compose(RxUtil.<DailyBeforeListBean>rxSchedulerHelper())
                .map(new Func1<DailyBeforeListBean, DailyBeforeListBean>() {
                    @Override
                    public DailyBeforeListBean call(DailyBeforeListBean dailyBeforeListBean) {
                        List<DailyListBean.StoriesBean> stories = dailyBeforeListBean.getStories();
                        for (DailyListBean.StoriesBean storie : stories){
                            storie.setReadState(realmHelper.queryReadStateBean(String .valueOf(storie.getId())));
                        }
                        return dailyBeforeListBean;
                    }
                }).subscribe(new Action1<DailyBeforeListBean>() {
                    @Override
                    public void call(DailyBeforeListBean dailyBeforeListBean) {
                            mView.addMoreContent(dailyBeforeListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError(throwable.toString());
                    }
                });
        addSubscrebe(subscription);
    }
}
