package com.lzm.shukong.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lzm.shukong.app.App;
import com.lzm.shukong.di.component.ActivityComponter;
import com.lzm.shukong.di.component.DaggerActivityComponter;
import com.lzm.shukong.di.module.ActivityModule;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.framework.ShareSDK;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2017/2/11.
 */

public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView{
    
    @Inject
    protected T mPresenter;
    
    protected Activity mContext;
    private Unbinder mUnBinder;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        App.getInstance().addActivity(this);
        initEventAndData();
        ShareSDK.initSDK(this);
    }
    
    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }
    
    public ActivityComponter  getActivityComponter(){
        return DaggerActivityComponter.builder()
                .applicationComponent(App.getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
                
    }
    
    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        App.getInstance().removeActivity(this);
        fixInputMethodManagerLeak(mContext);
    }
    
    protected abstract int getLayout();
    protected abstract void initInject();
    protected abstract void initEventAndData();

    /**
     * 发生输入法内存泄漏  
     * onDestroy中调用该方法 消除
     * @param context
     */
    public static void fixInputMethodManagerLeak(Context context) {
        if (context == null) {
            return;
        }
        try {
            // 对 mCurRootView mServedView mNextServedView 进行置空...  
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null) {
                return;
            }// author:sodino mail:sodino@qq.com  

            Object obj_get = null;
            Field f_mCurRootView = imm.getClass().getDeclaredField("mCurRootView");
            Field f_mServedView = imm.getClass().getDeclaredField("mServedView");
            Field f_mNextServedView = imm.getClass().getDeclaredField("mNextServedView");

            if (f_mCurRootView.isAccessible() == false) {
                f_mCurRootView.setAccessible(true);
            }
            obj_get = f_mCurRootView.get(imm);
            if (obj_get != null) { // 不为null则置为空  
                f_mCurRootView.set(imm, null);
            }

            if (f_mServedView.isAccessible() == false) {
                f_mServedView.setAccessible(true);
            }
            obj_get = f_mServedView.get(imm);
            if (obj_get != null) { // 不为null则置为空  
                f_mServedView.set(imm, null);
            }

            if (f_mNextServedView.isAccessible() == false) {
                f_mNextServedView.setAccessible(true);
            }
            obj_get = f_mNextServedView.get(imm);
            if (obj_get != null) { // 不为null则置为空  
                f_mNextServedView.set(imm, null);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
