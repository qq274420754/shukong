package com.lzm.shukong.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/2/13.
 */

public class Constants {
    //================= TYPE ====================
    public static final int TYPE_ZHIHU = 110;
    
    public static final int TYPE_MEIPAI = 120;
    
    public static final int TYPE_GANHUO = 130;
    
    public static final int TYPE_FULI = 140;
    
    public static final int TYPE_SHOUCANG = 150;
    
    public static final int TYPE_SHEZHI = 160;
    
    public static final int TYPE_GUANYU = 170;
    //================= ZHIHUDAILY ====================
    public static final int DAILY_TOPIC = 110;
    
    
    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    
    public static final String PATH_LOADINGIMG = PATH_DATA + "/LoadImg";
    
    public static final String LOADINGIMG_NAME =  "loadimg.jpg";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";

    //================= Shared Preference ====================
    
    public static final String SP_NIGHT_MODE = "night_mode";//夜间模式
    
    public static final String SP_NO_IMAGE = "no_image";//无图模式

    public static final String SP_AUTO_CACHE = "auto_cache";//自动缓存

    public static final String SP_CURRENT_ITEM = "current_item";//

    public static final String SP_LIKE_POINT = "like_point";//收藏
    
    public static final String SP_MANAGER_POINT = "manager_point";//管理导航默认的选项

    //================= Intent ====================
    
    public static final String IT_MEIPAI_TOPIC = "topic";

    public static final String IT_MEIPAI_MANAGER = "manager";
    
    public static final String IT_LIKE_URL = "url";
    
    public static final String IT_LIKE_TITLE = "title";
    
    public static final String IT_LIKE_IMAGE = "image";
    
    public static final String IT_LIKE_TYPE = "type";
    
    public static final String IT_LIKE_ID = "id";

    

    //================= topic name ====================
    
    public static String[] TITLES = new String[]{
            "热门", "搞笑", "明星名人", "男神", "女神", "音乐", "舞蹈", "美食", "美妆", "宝宝", "萌宠", "手工", "穿秀", "吃秀"};


    //================= share sdk ====================
    
    public static final String SHARE_SDK = "1b9ce70be84e4";
    
    
}
