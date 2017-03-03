package com.lzm.shukong.modul.http;

import com.lzm.shukong.modul.bean.meipai.MeipaiBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/18.
 */

public interface MeipaiApis {
    
    String HOST = "http://newapi.meipai.com/output/";

    @GET("channels_topics_timeline.json")
    Observable<List<MeipaiBean>> getMeipaiList(@Query("id") int id,
                                               @Query("topic_name") String topicName,
                                               @Query("count") int count,
                                               @Query("page") int page);
    
}
