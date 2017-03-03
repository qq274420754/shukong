package com.lzm.shukong.base;

/**
 * Created by Administrator on 2017/2/11.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
    
}
