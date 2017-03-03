package com.lzm.shukong.ui.zhihu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class ZhihuMainViewPagerAdapter extends FragmentPagerAdapter{
    
    public static String[] ZHIHUTITLE = new String[]{"日报", "主题", "专栏"};
    List<Fragment>fragments;

    public ZhihuMainViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ZHIHUTITLE[position % ZHIHUTITLE.length];
    }
    
}
