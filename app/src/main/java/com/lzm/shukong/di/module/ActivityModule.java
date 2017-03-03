package com.lzm.shukong.di.module;

import android.app.Activity;

import com.lzm.shukong.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/2/12.
 */
@Module
public class ActivityModule{
    
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity privideActivity(){
        return mActivity;
    }

}
