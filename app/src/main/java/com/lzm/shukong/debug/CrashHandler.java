package com.lzm.shukong.debug;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.lzm.shukong.app.App;
import com.lzm.shukong.util.LogUtil;
import com.lzm.shukong.util.ToastUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 异常退出信息捕获类
 * Created by Administrator on 2017/2/11.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //系统默认的UncaughtException处理类
    private static Thread.UncaughtExceptionHandler  defaultHandler = null;
    //CrashHandler实例
    private static CrashHandler instance ;

    private Context mContext;

    private final String TAG = CrashHandler.class.getSimpleName();

    public CrashHandler( ) {}
    
    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance (){
        if (instance == null)
            instance = new CrashHandler();
        return instance ;
    }
    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当系统未捕获到异常会转入该函数来处理
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(e.toString());
        LogUtil.e(TAG, e.toString());
        LogUtil.e(TAG, collectCrashDeviceInfo());
        LogUtil.e(TAG, getCrashInfo(e));
        // 调用系统错误机制
        defaultHandler.uncaughtException(t, e);
        ToastUtil.shortShow("抱歉,程序发生异常即将退出");
        App.getInstance().exitApp();
    }


    /**
     * 得到程序崩溃的详细信息
     */
    public String getCrashInfo(Throwable ex) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        ex.setStackTrace(ex.getStackTrace());
        ex.printStackTrace(printWriter);
        return result.toString();
    }
    /**
     * 收集程序崩溃的设备信息
     */
    public String collectCrashDeviceInfo() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            String versionName = pi.versionName;
            String model = android.os.Build.MODEL;
            String androidVersion = android.os.Build.VERSION.RELEASE;
            String manufacturer = android.os.Build.MANUFACTURER;
            return versionName + "  " + model + "  " + androidVersion + "  " + manufacturer;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
