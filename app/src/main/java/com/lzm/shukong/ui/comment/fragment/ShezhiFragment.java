package com.lzm.shukong.ui.comment.fragment;

import com.lzm.shukong.R;
import com.lzm.shukong.base.SimpleFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/2/18.
 */

public class ShezhiFragment extends SimpleFragment {
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_shezhimain ;
    }

    @Override
    public void initEventAndData() {

    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("shezhiMainFragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("shezhiMainFragment");
    }
}
