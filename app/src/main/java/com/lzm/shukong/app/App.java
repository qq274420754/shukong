package com.lzm.shukong.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.github.moduth.blockcanary.BlockCanary;
import com.lzm.shukong.debug.AppBlockCanaryContext;
import com.lzm.shukong.debug.CrashHandler;
import com.lzm.shukong.di.component.ApplicationComponent;
import com.lzm.shukong.di.component.DaggerApplicationComponent;
import com.lzm.shukong.di.module.ApplicationModuel;
import com.lzm.shukong.util.LogUtil;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.analytics.MobclickAgent;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Administrator on 2017/2/11.
 */

public class App extends Application {
    
    private static App instance;
    private Set<Activity> allActivity;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static App getInstance(){
        return instance;
    }
    
    static {
        //使用 Support Library 23.2.0 DayNight主题实现来实现夜间模式，
        // google在23.2后自带
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        //初始化屏幕宽高
        getScreenSize();
        //友盟统计  普通模式
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setDebugMode( true );
        MobclickAgent.openActivityDurationTrack(false);
        //初始化日志
        Logger.init(getPackageName()).hideThreadInfo();
        //初始化内存泄漏检测系统
        LeakCanary.install(this);
        //程序崩溃出错收集
        CrashHandler.getInstance().init(this);
        //初始化UI线程卡顿检测工具
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        
        // 腾讯tbs SDK接入
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                
            }

            @Override
            public void onViewInitFinished(boolean b) {
                LogUtil.e("app", " onViewInitFinished is " + b);
            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("app","onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("app","onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("app","onDownloadProgress:"+i);
            }
        });
        QbSdk.initX5Environment(getApplicationContext(),  callback);
    }

    /**
     * 添加Activity到集合
     * @param a
     */
    public void addActivity(Activity a){
        if (allActivity == null){
            allActivity = new HashSet<Activity>();
        }
        allActivity.add(a);
    }

    /**
     * 从Activity集合中取出Activity
     * @param a
     */
    public void removeActivity(Activity a){
        if (allActivity != null){
            allActivity.remove(a);
        }
    }

    /**
     * 退出App
     */
    public void exitApp() {
        if (allActivity != null) {
            synchronized (allActivity) {
                for (Activity act : allActivity) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    public void getScreenSize() {
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }
    public static ApplicationComponent getApplicationComponent(){
        return DaggerApplicationComponent.builder()
                .applicationModuel(new ApplicationModuel(instance ))
                .build();
    }
}
