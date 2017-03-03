package com.lzm.shukong.modul.http;

import com.lzm.shukong.modul.bean.unsplash.ImageListBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/27.
 */

public interface UnsplashApis {
    
    public static String HOST = "https://unsplash.it/";

    /**
     * https://unsplash.it/200/300/?random
     * 获取随机图片可以设置大小
     * @return
     */
    @GET("{sizewidth}/{sizeheight}/?random")//
    Observable<ResponseBody> getRandomImg(@Path("sizewidth")String width, @Path("sizeheight")String height);

    /**
     * 
     * 获取灰度随机图片可以设置大小
     * @return
     */
    @GET("/g/{sizewidth}/{sizeheight}/?random")//
    Observable getGreyscaleRandomImg(@Path("sizewidth")String width,@Path("sizeheight")String height);

    /**
     * 
     * 获取图片集合
     * @return
     */
    @GET("/list")//
    Observable<List<ImageListBean>> getImgList();

    /**
     * https://unsplash.it/200/300?image=0
     * 获取图片集合 中的图片
     * @return
     */
    @GET("{sizewidth}/{sizeheight}/")//
    Observable getListImg(@Path("sizewidth")String width,@Path("sizeheight")String height,@Query("image") int imgId);

    /**
     * https://unsplash.it/200/300/?blur
     * 随机获取图片 带模糊效果
     * @return
     */
    @GET("{sizewidth}/{sizeheight}/?blur&random")//
    Observable getListImg(@Path("sizewidth")String width,@Path("sizeheight")String height);




}
