package com.lzm.shukong.ui.main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.lzm.shukong.R;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.base.BaseActivity;
import com.lzm.shukong.base.BaseView;
import com.lzm.shukong.presenter.MainPresenter;
import com.lzm.shukong.ui.comment.fragment.GuanyuFragment;
import com.lzm.shukong.ui.comment.fragment.ShezhiFragment;
import com.lzm.shukong.ui.comment.fragment.ShoucangFragment;
import com.lzm.shukong.ui.fuli.fragment.FuliMainFragment;
import com.lzm.shukong.ui.ganhuo.fragment.GanhuoMainFragment;
import com.lzm.shukong.ui.meipai.fragment.MeipaiMainFragment;
import com.lzm.shukong.ui.zhihu.fragment.ZhihuMainFragment;
import com.lzm.shukong.util.SharedPreferenceUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements BaseView {


    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.toolbar_container)
    FrameLayout toolbarContainer;
    @BindView(R.id.container_view)
    FrameLayout containerView;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mdrawerLayout;

    MenuItem mLastMenuItem;
    ActionBarDrawerToggle mDrawerToggle;
    ZhihuMainFragment mZhihuMainFragment;
    MeipaiMainFragment mMeipaiMainFragment;
    GanhuoMainFragment mGanhuoMainFragment;
    FuliMainFragment mFuliMainFragment;
    ShoucangFragment mShoucangFragment;
    ShezhiFragment  mShezhiFragment ;
    GuanyuFragment  mGuanyuFragment ;
    
    int needShowFragment = Constants.TYPE_ZHIHU;
    int needHideFragment = Constants.TYPE_ZHIHU;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            SharedPreferenceUtil.setNightModeState(false);
        }else {
            needShowFragment = SharedPreferenceUtil.getCurrentItem();
            needHideFragment = Constants.TYPE_ZHIHU;
            showHideFragment(getTargetFragment(needShowFragment),getTargetFragment(needHideFragment));
            setToolBar(toolBar,navigationView.getMenu().findItem(getCurrentFragment(needShowFragment)).getTitle().toString());
            needHideFragment = needShowFragment;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponter().inject(this);
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, "知乎日报");//这里先设置toolbar，在设置左侧导航监听，不然toolbar导航监听会覆盖左侧导航监听
        mDrawerToggle = new ActionBarDrawerToggle(this,mdrawerLayout,toolBar,R.string.draw_open,R.string.draw_close);
        mDrawerToggle.syncState();
        mdrawerLayout.setDrawerListener(mDrawerToggle);
        mLastMenuItem = navigationView.getMenu().findItem(R.id.drawer_zhihu);
        mZhihuMainFragment = new ZhihuMainFragment();
        mMeipaiMainFragment = new MeipaiMainFragment();
        mGanhuoMainFragment = new GanhuoMainFragment();
        mFuliMainFragment = new FuliMainFragment();
        mShoucangFragment = new ShoucangFragment();
        mShezhiFragment = new ShezhiFragment();
        mGuanyuFragment = new GuanyuFragment();
        loadMultipleRootFragment(R.id.container_view,0,mZhihuMainFragment,mMeipaiMainFragment,mGanhuoMainFragment,mFuliMainFragment
        ,mShoucangFragment,mShezhiFragment,mGuanyuFragment);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_zhihu:
                        needShowFragment = Constants.TYPE_ZHIHU;
                        searchView.setVisibility(View.GONE);
                        break;
                    case R.id.drawer_meipai:
                        needShowFragment = Constants.TYPE_MEIPAI;
                        searchView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.drawer_chengxuyuan:
                        needShowFragment = Constants.TYPE_GANHUO;
                        break;
                    case R.id.drawer_fuli:
                        needShowFragment = Constants.TYPE_FULI;
                        break;
                    case R.id.drawer_shoucang:
                        needShowFragment = Constants.TYPE_SHOUCANG; 
                        break;
                    case R.id.drawer_shezhi:
                        needShowFragment = Constants.TYPE_SHEZHI;
                        break;
                    case R.id.drawer_guanyu:
                        needShowFragment = Constants.TYPE_GUANYU;
                        break;
                }
                if (mLastMenuItem != null){
                    mLastMenuItem.setChecked(false);
                }
                mLastMenuItem = item;
                mdrawerLayout.closeDrawers();
                item.setChecked(true);
                toolBar.setTitle(item.getTitle());
                SharedPreferenceUtil.setCurrentItem(needShowFragment);
                showHideFragment(getTargetFragment(needShowFragment),getTargetFragment(needHideFragment));
                needHideFragment = needShowFragment;
                return false;
            }
        });
    }
    public SupportFragment getTargetFragment(int type){
        switch (type){
            case Constants.TYPE_ZHIHU:
                return mZhihuMainFragment;
            case Constants.TYPE_MEIPAI:
                return mMeipaiMainFragment;
            case Constants.TYPE_GANHUO:
                return mGanhuoMainFragment;
            case Constants.TYPE_FULI:
                return mFuliMainFragment;
            case Constants.TYPE_SHOUCANG:
                return mShoucangFragment;
            case Constants.TYPE_SHEZHI:
                return mShezhiFragment;
            case Constants.TYPE_GUANYU:
                return mGuanyuFragment;
        }
        return mZhihuMainFragment;
    }
    public int getCurrentFragment(int currentFragment){
        switch (currentFragment){
            case Constants.TYPE_ZHIHU:
                return R.id.drawer_zhihu;
            case Constants.TYPE_MEIPAI:
                return R.id.drawer_meipai;
            case Constants.TYPE_GANHUO:
                return R.id.drawer_chengxuyuan;
            case Constants.TYPE_FULI:
                return R.id.drawer_fuli ;
            case Constants.TYPE_SHOUCANG:
                return R.id.drawer_shoucang;
            case Constants.TYPE_SHEZHI:
                return R.id.drawer_shezhi;
            case Constants.TYPE_GUANYU:
                return R.id.drawer_guanyu;
        }
        return R.id.drawer_zhihu;
    }
    
    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
    @Override
    public void showError(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 后退键改HOME
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
}
