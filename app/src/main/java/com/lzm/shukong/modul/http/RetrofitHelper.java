package com.lzm.shukong.modul.http;

import android.webkit.MimeTypeMap;

import com.lzm.shukong.BuildConfig;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.modul.bean.meipai.MeipaiBean;
import com.lzm.shukong.modul.bean.zhihubean.DailyBeforeListBean;
import com.lzm.shukong.modul.bean.zhihubean.DailyListBean;
import com.lzm.shukong.modul.bean.zhihubean.DetailExtraBean;
import com.lzm.shukong.modul.bean.zhihubean.WelcomeBean;
import com.lzm.shukong.modul.bean.zhihubean.ZhihuDetailBean;
import com.lzm.shukong.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/13.
 */

public class RetrofitHelper {

    private static OkHttpClient okHttpClient = null;
    private static ZhihuApis zhihuApiService = null;
    private static GankApis gankApiService = null;
    private static MeipaiApis meipaiApiService = null;
    private static UnsplashApis unsplashApis = null;
    private static UnsplashApis unsplashImgApis = null;


    private void init() {
        initOkHttp();
        zhihuApiService = getZhihuApiService();
        gankApiService = getGankApiService();
        meipaiApiService = getMeipaiService();
        unsplashApis = getUnsplashService();
        unsplashImgApis = getUnsplashImgService();
    }

    public RetrofitHelper() {
        init();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        // http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
//        Interceptor apikey = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                request = request.newBuilder()
//                        .addHeader("apikey",Constants.KEY_API)
//                        .build();
//                return chain.proceed(request);
//            }
//        }
//        设置统一的请求头部参数
//        builder.addInterceptor(apikey);
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private static ZhihuApis getZhihuApiService() {
        Retrofit zhihuRetrofit = new Retrofit.Builder()
                .baseUrl(ZhihuApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return zhihuRetrofit.create(ZhihuApis.class);
    }

    private static GankApis getGankApiService() {
        Retrofit gankRetrofit = new Retrofit.Builder()
                .baseUrl(GankApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return gankRetrofit.create(GankApis.class);
    }


    private static MeipaiApis getMeipaiService() {
        Retrofit meipaiRetrofit = new Retrofit.Builder()
                .baseUrl(MeipaiApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return meipaiRetrofit.create(MeipaiApis.class);
    }

    /**
     * 获取数据
     * @return
     */
    private static UnsplashApis getUnsplashService() {
        Retrofit unsplashApisRetrofit = new Retrofit.Builder()
                .baseUrl(UnsplashApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return unsplashApisRetrofit.create(UnsplashApis.class);
    }

    /**
     * 获取图片
     * @return
     */
    private static UnsplashApis getUnsplashImgService() {
        Retrofit unsplashApisRetrofit = new Retrofit.Builder()
                .baseUrl(UnsplashApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, ResponseBody>() {
                            @Override
                            public ResponseBody convert(ResponseBody value) throws IOException {
                                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
                                ResponseBody r = ResponseBody.create(MediaType.parse(mimeTypeMap.getMimeTypeFromExtension("jpg")), value.bytes());
                                return r;
                            }
                        };
                    }
                })
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return unsplashApisRetrofit.create(UnsplashApis.class);
    }

    
    public Observable<List<MeipaiBean>> fetchMeipaiList(int id, String topicName, int count, int page) {
        return meipaiApiService.getMeipaiList(id, topicName, count, page);
    }

    public Observable<WelcomeBean> fetchWelcomeImg(String size) {
        return zhihuApiService.getWelcomeInfo(size);
    }

    public Observable<ResponseBody> fetchUnsplashImg(String sizeWidth, String sizeHeight) {
        return unsplashImgApis.getRandomImg(sizeWidth, sizeHeight);
    }
    
    public Observable<DailyListBean> fetchDailyList() {
        return zhihuApiService.getDailyList();
    }
    public Observable<DailyBeforeListBean> fetchDailyBeforeList(String date) {
        return zhihuApiService.getDailyBeforeList(date);
    }
    public Observable<ZhihuDetailBean> fetchDetailInfo(int id) {
        return zhihuApiService.getDetailInfo(id);
    }
    public Observable<DetailExtraBean> fetchDetailExtraInfo(int id) {
        return zhihuApiService.getDetailExtraInfo(id);
    }
}
