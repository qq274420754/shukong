package com.lzm.shukong.di.module;

import com.lzm.shukong.app.App;
import com.lzm.shukong.di.ContextLife;
import com.lzm.shukong.modul.db.RealmHelper;
import com.lzm.shukong.modul.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/2/12.
 */
//作为Module，需要使用@Module注解，在被@Module注解修饰的类内部，
// 使用@Provides注解来表明可以提供的依赖对象
@Module 
public class ApplicationModuel {
    private final App application ;

    public ApplicationModuel(App application) {
        this.application = application;
    }


    @Provides
    @Singleton
    @ContextLife("Application")
    App provideApplicationContext(){
        return this.application;
    }
    
    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper(){return new RetrofitHelper();}

    @Provides
    @Singleton
    RealmHelper provideRealmHelper() {
        return new RealmHelper(application);
    }

}
