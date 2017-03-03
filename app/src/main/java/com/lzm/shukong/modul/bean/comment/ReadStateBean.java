package com.lzm.shukong.modul.bean.comment;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Administrator on 2017/2/19.
 */

public class ReadStateBean extends RealmObject{
    
    @PrimaryKey
    private String id ;

    public ReadStateBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
