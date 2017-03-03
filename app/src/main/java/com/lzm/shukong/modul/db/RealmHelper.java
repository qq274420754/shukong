package com.lzm.shukong.modul.db;

import android.content.Context;

import com.lzm.shukong.modul.bean.comment.CollectBean;
import com.lzm.shukong.modul.bean.comment.ReadStateBean;
import com.lzm.shukong.modul.bean.meipai.MeipaiManagerBean;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/2/19.
 */

public class RealmHelper  {

    private static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;

    public RealmHelper(Context mContext) {
        RealmConfiguration config = new RealmConfiguration.Builder(mContext)
                .name(DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        mRealm = Realm.getInstance(config);
    }

    /**
     * 增加 阅读记录
     * @param id
     * 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    public void addReadStateBean(String id){
        ReadStateBean readStateBean = new ReadStateBean();
        readStateBean.setId(id);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(readStateBean);
        mRealm.commitTransaction();
    }

    /**
     * 查询 阅读记录
     * @param id
     * @return
     */
    public boolean queryReadStateBean(String id){
        RealmResults<ReadStateBean> realmResults = 
                mRealm.where(ReadStateBean.class).findAll();
        for (ReadStateBean readStateBean : realmResults){
            if (readStateBean.getId() == id){
                return true;
            }
        }
        return false ;
    }

    /**
     * 增加 收藏记录
     * @param collectBean
     */
    public void addCollectBean(CollectBean collectBean){
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(collectBean);
        mRealm.commitTransaction();
    }

    /**
     * 删除 收藏记录
     * @param id
     */
    public void deleteCollectBean(String id){
        CollectBean collectBean = mRealm.where(CollectBean.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        if (collectBean != null){
            collectBean.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    /**
     * 修改 收藏的时间记录和显示位置
     * @param id
     * @param time
     * @param isPlus
     */
    public void updateCollectBean(String id ,long time , boolean isPlus){
        CollectBean bean = mRealm.where(CollectBean.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        if (isPlus) {
            bean.setTime(++time);
        } else {
            bean.setTime(--time);
        }
        mRealm.commitTransaction();
    }

    /**
     * 查询 收藏的记录
     * @param id
     * @return
     */
    public boolean queryCollectBean(String id){
        RealmResults<CollectBean> results = mRealm.where(CollectBean.class).findAll();
        for(CollectBean item : results) {
            if(item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    };

    /**
     * 更新 掘金首页管理列表
     * @param bean
     */
    public void updateMeipaiManagerList(MeipaiManagerBean bean) {
        MeipaiManagerBean data = mRealm.where(MeipaiManagerBean.class).findFirst();
        mRealm.beginTransaction();
        if (data != null) {
            data.deleteFromRealm();
        }
        mRealm.copyToRealm(bean);
        mRealm.commitTransaction();
    }

    /**
     * 获取 掘金首页管理列表
     * @return
     */
    public MeipaiManagerBean getMeipaiManagerList() {
        MeipaiManagerBean bean = mRealm.where(MeipaiManagerBean.class).findFirst();
        if (bean == null)
            return null;
        return mRealm.copyFromRealm(bean);
    }
    
}
