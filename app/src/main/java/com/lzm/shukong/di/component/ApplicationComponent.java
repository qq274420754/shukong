package com.lzm.shukong.di.component;

import com.lzm.shukong.app.App;
import com.lzm.shukong.di.ContextLife;
import com.lzm.shukong.di.module.ApplicationModuel;
import com.lzm.shukong.modul.db.RealmHelper;
import com.lzm.shukong.modul.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component; 
/**
 * Created by Administrator on 2017/2/12.
 */
@Singleton
@Component(modules = ApplicationModuel.class)
public interface ApplicationComponent {
    
    @ContextLife ("Application")
    App getContext();//提供全局context
    //ContextLife用于限定我们提供的context为全局application context。区别其他context

    RetrofitHelper retrofitHelper();  //提供http的帮助类
    
    RealmHelper REALM_HELPER(); //提供realm数据库帮助类
}
