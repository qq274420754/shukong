package com.lzm.shukong.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lzm.shukong.base.RxPresenter;
import com.lzm.shukong.modul.http.RetrofitHelper;
import com.lzm.shukong.presenter.contract.LoadingContract;
import com.lzm.shukong.util.RxUtil;
import com.lzm.shukong.util.SystemUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/26.
 */

public class LoadingPresenter extends RxPresenter<LoadingContract.view> implements LoadingContract.presenter{
    
    
    RetrofitHelper retrofitHelper;
    
    public static String sizeWidth = "720";
    public static String sizeHeig = "1280";

    @Inject
    public LoadingPresenter(RetrofitHelper retrofitHelper) {
        this.retrofitHelper = retrofitHelper;
    }

    @Override
    public void getLoadingImgData() {
        if (SystemUtil.isWifiConnected()){
            Subscription subscription = retrofitHelper.fetchUnsplashImg(sizeWidth,sizeHeig)
                    .subscribeOn(Schedulers.newThread())
                    .map(new Func1<ResponseBody, Bitmap>() {
                        @Override
                        public Bitmap call(ResponseBody responseBody) {
                            InputStream is = null;
                            try {
                                is = responseBody.byteStream();
                            } finally {
                                try {
                                    if (is != null) {
                                        is.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (is != null){
                                return BitmapFactory.decodeStream(is);
                            }
                            return null;
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Bitmap>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable e) {
                            mView.showError(e.toString());
                            startCountDown();
                        }
                        @Override
                        public void onNext(Bitmap bitmap) {
                            mView.showContent(bitmap);
                            mView.jumpToMain();
                        }
                    });
            addSubscrebe(subscription);
        }else {
            startCountDown();
        }
    }
    private void startCountDown() {
        Subscription rxSubscription = Observable.timer(1000, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.jumpToMain();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
