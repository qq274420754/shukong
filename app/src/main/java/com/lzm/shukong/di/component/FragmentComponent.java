package com.lzm.shukong.di.component;

import android.app.Activity;

import com.lzm.shukong.di.FragmentScope;
import com.lzm.shukong.di.module.FragmentModule;
import com.lzm.shukong.ui.meipai.fragment.MeipaiListFragment;
import com.lzm.shukong.ui.meipai.fragment.MeipaiMainFragment;
import com.lzm.shukong.ui.zhihu.fragment.DailyFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/19.
 */
@FragmentScope
@Component( dependencies = ApplicationComponent.class,modules = FragmentModule.class )
public interface FragmentComponent {
    
    Activity getActivity();
    
    void inject(MeipaiMainFragment meipaiMainFragment);
    
    void inject(MeipaiListFragment meipaiListFragment);
    
    void inject(DailyFragment dailyFragment);

}
