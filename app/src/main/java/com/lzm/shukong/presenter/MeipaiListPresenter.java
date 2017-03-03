package com.lzm.shukong.presenter;

import com.lzm.shukong.base.RxPresenter;
import com.lzm.shukong.modul.bean.meipai.MeipaiBean;
import com.lzm.shukong.modul.http.RetrofitHelper;
import com.lzm.shukong.presenter.contract.MeipaiListContract;
import com.lzm.shukong.util.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MeipaiListPresenter extends RxPresenter<MeipaiListContract.View> implements MeipaiListContract.persenter {

    private static final int NUM_EACH_PAGE = 20;
    private int currentPage = 1;
    
    RetrofitHelper retrofitHelper;
    private List<MeipaiBean> mList = new ArrayList<>();
    String topic;
    

    @Inject
    public MeipaiListPresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void getMeipaiDate(String topic) {
        this.topic = topic;
        currentPage = 1;
        mList.clear();
        Observable<List<MeipaiBean>> observable = retrofitHelper.fetchMeipaiList(getId(topic),topic,
                NUM_EACH_PAGE,currentPage++)
                .compose(RxUtil.<List<MeipaiBean>>rxSchedulerHelper());

        Subscription subscription = observable.subscribe(new Action1<List<MeipaiBean>>() {
            @Override
            public void call(List<MeipaiBean> meipaiBeanList) {
                mView.showContent(meipaiBeanList);
                mList.addAll(meipaiBeanList);
            }
        },new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mView.showError("数据加载失败ヽ(≧Д≦)ノ");
            }
        });
        addSubscrebe(subscription);
    }

    @Override
    public void getMoreMeipaiDate() {
        Observable<List<MeipaiBean>> observable = retrofitHelper.fetchMeipaiList(getId(topic),topic,
                NUM_EACH_PAGE,currentPage++)
                .compose(RxUtil.<List<MeipaiBean>>rxSchedulerHelper());

        Subscription subscription = observable.subscribe(new Action1<List<MeipaiBean>>() {
            @Override
            public void call(List<MeipaiBean> meipaiBeanList) {
                mList.addAll(meipaiBeanList);
                mView.showMoreContent(meipaiBeanList,mList.size(), mList.size() + NUM_EACH_PAGE);
            }
        },new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mView.showError("没有更多了ヽ(≧Д≦)ノ");
            }
        });
        addSubscrebe(subscription);
    }

    /**
     * 根据主题获取ID
     * @param topic
     * @return
     */
    public int getId(String topic) {
        switch (topic) {
            case "热门":
                return 1;
            case "搞笑":
                return 13;
            case "明星名人":
                return 16;
            case "男神":
                return 31;
            case "女神":
                return 19;
            case "音乐":
                return 62;
            case "舞蹈":
                return 63;
            case "美食":
                return 59;
            case "美妆":
                return 27;
            case "宝宝":
                return 18;
            case "萌宠":
                return 6;
            case "手工":
                return 450;
            case "穿秀":
                return 460;
            case "吃秀":
                return 423;
        }
        return 1;
    }

}
