package com.lzm.shukong.presenter;

import com.lzm.shukong.base.RxPresenter;
import com.lzm.shukong.component.RxBus;
import com.lzm.shukong.modul.bean.comment.NightModeEvent;
import com.lzm.shukong.modul.http.RetrofitHelper;
import com.lzm.shukong.presenter.contract.MainContract;
import com.lzm.shukong.util.RxUtil;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/2/12.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    
    @Inject
    public MainPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        registerEvent();
    }
    
    private void registerEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(NightModeEvent.class)
                .compose(RxUtil.<NightModeEvent>rxSchedulerHelper())
                .map(new Func1<NightModeEvent, Boolean>() {
                    @Override
                    public Boolean call(NightModeEvent nightModeEvent) {
                        return nightModeEvent.getNightMode();
                    }
                }).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        mView.showError("切换模式失败ヽ(≧Д≦)ノ");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        mView.useNightMode(aBoolean);
                    }
                });
        addSubscrebe(rxSubscription);
    }
    
    @Override
    public void checkVersion(String currentVersion) {
        
    }
}
