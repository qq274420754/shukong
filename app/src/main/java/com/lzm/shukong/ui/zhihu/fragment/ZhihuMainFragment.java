package com.lzm.shukong.ui.zhihu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.shukong.R;
import com.lzm.shukong.base.SimpleFragment;
import com.lzm.shukong.ui.zhihu.adapter.ZhihuMainViewPagerAdapter;
import com.umeng.analytics.MobclickAgent;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/18.
 */

public class ZhihuMainFragment extends SimpleFragment {

    @BindView(R.id.tabLayout_zhihu)
    TabPageIndicator IndicatorZhihu;
    @BindView(R.id.viewpager_zhihu)
    ViewPager zhihuViewPager;
    

    List<Fragment> fragments = new ArrayList<>();
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhihumain;
    }

    @Override
    public void initEventAndData() {
        fragments.add(new DailyFragment());
        fragments.add(new ThemeFragment());
        fragments.add(new ZhuanlanFragment());
        zhihuViewPager.setAdapter(new ZhihuMainViewPagerAdapter(getFragmentManager(), fragments));
        IndicatorZhihu.setViewPager(zhihuViewPager,0);
    }
    
    


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("zhihuMainFragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("zhihuMainFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(),R.style.MyTheme);//设置选项卡主题
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View rootView = localInflater.inflate(R.layout.fragment_zhihumain, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
