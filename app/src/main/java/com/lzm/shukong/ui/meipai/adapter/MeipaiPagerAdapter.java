package com.lzm.shukong.ui.meipai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lzm.shukong.ui.meipai.fragment.MeipaiListFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class MeipaiPagerAdapter extends FragmentStatePagerAdapter {

    private List<MeipaiListFragment> fragments;

    public MeipaiPagerAdapter(FragmentManager fm, List<MeipaiListFragment> fragments) {
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
}
