package com.lzm.shukong.ui.meipai.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.lzm.shukong.R;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.base.SimpleActivity;
import com.lzm.shukong.component.RxBus;
import com.lzm.shukong.modul.bean.meipai.MeipaiManagerBean;
import com.lzm.shukong.modul.bean.meipai.meipaiManagerItemBean;
import com.lzm.shukong.ui.meipai.adapter.MeipaiManagerAdapter;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import io.realm.RealmList;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MeipaiTabManagerAvtivity extends SimpleActivity {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

     
    RealmList<meipaiManagerItemBean> mList;
    MeipaiManagerAdapter mAdapter;

    @Override
    protected int getLayout() {
         return R.layout.activity_meipaimanager;
    }

    @Override
    public void initEventAndData() {
        setToolBar(toolBar,"设置频道");
        mList = ((MeipaiManagerBean)getIntent().getParcelableExtra(Constants.IT_MEIPAI_MANAGER)).getManagerList();
        mAdapter = new MeipaiManagerAdapter(mList,mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(mAdapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().post(new MeipaiManagerBean(mList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("meipaiManagerActivity");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("meipaiManagerActivity");
        MobclickAgent.onPause(this);
    }
}
