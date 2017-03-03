package com.lzm.shukong.di.component;

import android.app.Activity;

import com.lzm.shukong.di.ActivityScope;
import com.lzm.shukong.di.module.ActivityModule;
import com.lzm.shukong.ui.main.Activity.LoadingActivity;
import com.lzm.shukong.ui.main.Activity.MainActivity;
import com.lzm.shukong.ui.zhihu.activity.DailyDetailActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/12.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class ,modules = ActivityModule.class)
public interface ActivityComponter {
    
    Activity getActivity();
    
    void inject(MainActivity mainActivity);

    void inject(LoadingActivity loadingActivity);
    
    void inject(DailyDetailActivity dailyDetailActivity);
    
}
