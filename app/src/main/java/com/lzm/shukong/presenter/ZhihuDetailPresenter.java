package com.lzm.shukong.presenter;

import com.lzm.shukong.app.Constants;
import com.lzm.shukong.base.RxPresenter;
import com.lzm.shukong.modul.bean.comment.CollectBean;
import com.lzm.shukong.modul.bean.zhihubean.DetailExtraBean;
import com.lzm.shukong.modul.bean.zhihubean.ZhihuDetailBean;
import com.lzm.shukong.modul.db.RealmHelper;
import com.lzm.shukong.modul.http.RetrofitHelper;
import com.lzm.shukong.presenter.contract.ZhihuDetailContract;
import com.lzm.shukong.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by codeest on 16/8/13.
 */

public class ZhihuDetailPresenter extends RxPresenter<ZhihuDetailContract.View> implements ZhihuDetailContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;
    private ZhihuDetailBean mData;

    @Inject
    public ZhihuDetailPresenter(RetrofitHelper mRetrofitHelper, RealmHelper mRealmHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mRealmHelper = mRealmHelper;
    }

    @Override
    public void getDetailData(int id) {
        Subscription rxSubscription =  mRetrofitHelper.fetchDetailInfo(id)
                .compose(RxUtil.<ZhihuDetailBean>rxSchedulerHelper())
                .subscribe(new Action1<ZhihuDetailBean>() {
                    @Override
                    public void call(ZhihuDetailBean zhihuDetailBean) {
                        mData = zhihuDetailBean;
                        mView.showContent(zhihuDetailBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载数据失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getExtraData(int id) {
        Subscription rxSubscription =  mRetrofitHelper.fetchDetailExtraInfo(id)
                .compose(RxUtil.<DetailExtraBean>rxSchedulerHelper())
                .subscribe(new Action1<DetailExtraBean>() {
                    @Override
                    public void call(DetailExtraBean detailExtraBean) {
                        mView.showExtraInfo(detailExtraBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError("加载额外信息失败ヽ(≧Д≦)ノ");
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void insertLikeData() {
        if (mData != null) {
            CollectBean bean = new CollectBean();
            bean.setId(String.valueOf(mData.getId()));
            bean.setImage(mData.getImage());
            bean.setTitle(mData.getTitle());
            bean.setType(Constants.TYPE_ZHIHU);
            bean.setTime(System.currentTimeMillis());
            mRealmHelper.addCollectBean(bean);
        } else {
            mView.showError("操作失败");
        }
    }

    @Override
    public void deleteLikeData() {
        if (mData != null) {
            mRealmHelper.deleteCollectBean(String.valueOf(mData.getId()));
        } else {
            mView.showError("操作失败");
        }
    }

    @Override
    public void queryLikeData(int id) {
        mView.setLikeButtonState(mRealmHelper.queryCollectBean(String.valueOf(id)));
    }
}
