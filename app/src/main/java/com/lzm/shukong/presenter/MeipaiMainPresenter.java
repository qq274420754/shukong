package com.lzm.shukong.presenter;

import com.lzm.shukong.base.RxPresenter;
import com.lzm.shukong.component.RxBus;
import com.lzm.shukong.modul.bean.meipai.MeipaiManagerBean;
import com.lzm.shukong.modul.bean.meipai.meipaiManagerItemBean;
import com.lzm.shukong.modul.db.RealmHelper;
import com.lzm.shukong.presenter.contract.MeipaiContract;
import com.lzm.shukong.util.RxUtil;
import com.lzm.shukong.util.SharedPreferenceUtil;

import javax.inject.Inject;

import io.realm.RealmList;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MeipaiMainPresenter extends RxPresenter<MeipaiContract.View> implements MeipaiContract.Presenter {

    private RealmHelper mRealmHelper;
    private RealmList<meipaiManagerItemBean> mList;


    @Inject
    public MeipaiMainPresenter(RealmHelper mRealHelper) {
        this.mRealmHelper = mRealHelper;
        registerEvent();
    }

    private void registerEvent() {
        Subscription rxSubscription = RxBus.getDefault().toObservable(MeipaiManagerBean.class)
                .compose(RxUtil.<MeipaiManagerBean>rxSchedulerHelper())
                .subscribe(new Action1<MeipaiManagerBean>() {
                    @Override
                    public void call(MeipaiManagerBean meipaiManagerBean) {
                        mRealmHelper.updateMeipaiManagerList(meipaiManagerBean);
                        mView.upDataTab(meipaiManagerBean.getManagerList());
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void initManagerList() {
        if (SharedPreferenceUtil.getManagerPoint()) {
            //第一次使用，生成默认ManagerList
            initList();
            mRealmHelper.updateMeipaiManagerList(new MeipaiManagerBean(mList));
            mView.upDataTab(mList);
        } else {
            if (mRealmHelper.getMeipaiManagerList() == null) {
                initList();
                mRealmHelper.updateMeipaiManagerList(new MeipaiManagerBean(mList));
            } else {
                mList = mRealmHelper.getMeipaiManagerList().getManagerList();
            }
            mView.upDataTab(mList);
        }
    }

    private void initList() {
        mList = new RealmList<>();
        mList.add(new meipaiManagerItemBean(0, true));
        mList.add(new meipaiManagerItemBean(1, true));
        mList.add(new meipaiManagerItemBean(2, true));
        mList.add(new meipaiManagerItemBean(3, true));
        mList.add(new meipaiManagerItemBean(4, false));
        mList.add(new meipaiManagerItemBean(5, false));
        mList.add(new meipaiManagerItemBean(6, false));
        mList.add(new meipaiManagerItemBean(7, false));
        mList.add(new meipaiManagerItemBean(8, false));
        mList.add(new meipaiManagerItemBean(9, false));
        mList.add(new meipaiManagerItemBean(10, false));
        mList.add(new meipaiManagerItemBean(11, false));
        mList.add(new meipaiManagerItemBean(12, false));
        mList.add(new meipaiManagerItemBean(13, false));
        mList.add(new meipaiManagerItemBean(14, false));
    }

    @Override
    public void setManagerList() {
        mView.jumpToManager(mRealmHelper.getMeipaiManagerList());
    }
    
    
}
