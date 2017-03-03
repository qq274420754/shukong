package com.lzm.shukong.ui.meipai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lzm.shukong.R;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.base.BaseFragment;
import com.lzm.shukong.modul.bean.meipai.MeipaiManagerBean;
import com.lzm.shukong.modul.bean.meipai.meipaiManagerItemBean;
import com.lzm.shukong.presenter.MeipaiMainPresenter;
import com.lzm.shukong.presenter.contract.MeipaiContract;
import com.lzm.shukong.ui.meipai.activity.MeipaiTabManagerAvtivity;
import com.lzm.shukong.ui.meipai.adapter.MeipaiPagerAdapter;
import com.lzm.shukong.util.SnackbarUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmList;

/**
 * Created by Administrator on 2017/2/18.
 */

public class MeipaiMainFragment extends BaseFragment<MeipaiMainPresenter> implements MeipaiContract.View {


    @BindView(R.id.tabLayout_meipai)
    TabLayout tabLayoutMeipai;
    @BindView(R.id.menu_meipaimanager)
    ImageView menuMeipaimanager;
    @BindView(R.id.viewpager_meipai)
    ViewPager viewpagerMeipai;
    
    String[] titles = new String[]{
            "热门", "搞笑", "明星名人", "男神", "女神", "音乐", "舞蹈", "美食", "美妆", "宝宝", "萌宠", "手工", "穿秀", "吃秀"};
    
    List<MeipaiListFragment> listFragments = new ArrayList<>();
    private int currentIndex = 0;
    

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meipaimain;
    }

    @Override
    protected void initEventAndData() {
        tabLayoutMeipai.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayoutMeipai.setupWithViewPager(viewpagerMeipai);
        mPresenter.initManagerList();
    }
    
    @Override
    public void upDataTab(RealmList<meipaiManagerItemBean> meipaiBeanList) {
        listFragments.clear();
        tabLayoutMeipai.removeAllTabs();
        for (meipaiManagerItemBean itemBean : meipaiBeanList){
            if (itemBean.getIsSelect()){
                MeipaiListFragment fragment = new MeipaiListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.IT_MEIPAI_TOPIC,titles[itemBean.getIndex()]);
                tabLayoutMeipai.addTab(tabLayoutMeipai.newTab().setText(titles[itemBean.getIndex()]));
                fragment.setArguments(bundle);
                listFragments.add(fragment);
            }
        }
        MeipaiPagerAdapter pagerAdapter = new MeipaiPagerAdapter(getChildFragmentManager(),listFragments);
        viewpagerMeipai.setAdapter(pagerAdapter);
        for (meipaiManagerItemBean item : meipaiBeanList) {
            if (item.getIsSelect()) {
                TabLayout.Tab tab = tabLayoutMeipai.getTabAt(currentIndex++);
                tab.setText(titles[item.getIndex()]);
            }
        }
        currentIndex = 0;
    }
    
    @Override
    public void showError(String msg) {
        SnackbarUtil.showShort(tabLayoutMeipai, msg);
    }
    
    @OnClick(R.id.menu_meipaimanager)
    public void onClick(View view) {
        mPresenter.setManagerList();
    }


    @Override
    public void jumpToManager(MeipaiManagerBean mBean) {
        Intent intent = new Intent(getActivity(), MeipaiTabManagerAvtivity.class);
        intent.putExtra(Constants.IT_MEIPAI_MANAGER,mBean);
        startActivity(intent);
        //ToastUtil.shortShow("跳转管理页");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("meipaiMainFragment");
        
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("meipaiMainFragment");
    }
}
