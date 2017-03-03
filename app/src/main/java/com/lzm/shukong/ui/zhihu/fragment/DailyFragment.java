package com.lzm.shukong.ui.zhihu.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lzm.shukong.R;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.base.BaseFragment;
import com.lzm.shukong.modul.bean.zhihubean.DailyBeforeListBean;
import com.lzm.shukong.modul.bean.zhihubean.DailyListBean;
import com.lzm.shukong.modul.db.RealmHelper;
import com.lzm.shukong.presenter.DailyPresenter;
import com.lzm.shukong.presenter.contract.DailyContract;
import com.lzm.shukong.ui.zhihu.activity.DailyDetailActivity;
import com.lzm.shukong.ui.zhihu.adapter.DailyListAdapter;
import com.lzm.shukong.ui.zhihu.adapter.TopPagerAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/28.
 */

public class DailyFragment extends BaseFragment<DailyPresenter> implements DailyContract.view {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.iv_progress)
    AVLoadingIndicatorView ivProgress;
    @BindView(R.id.lv_daily)
    ListView lvDaily;
    List<DailyListBean.TopStoriesBean> mTopList = new ArrayList<>();
    List<DailyListBean.StoriesBean> mList = new ArrayList<>();
    ViewPager vpTop;
    String date;
    TopPagerAdapter mTopPagerAdapter;
    DailyListAdapter mDailyListAdapter;
    LayoutInflater inflate;
    RealmHelper realmHelper;
    boolean isLoading = false;
    


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initEventAndData() {
        realmHelper = new RealmHelper(mContext);
        inflate = LayoutInflater.from(mContext);
        ivProgress.show();
        mPresenter.getDailyData();
        mTopPagerAdapter = new TopPagerAdapter(mContext);
        mDailyListAdapter = new DailyListAdapter(mContext);
        View header = inflate.inflate(R.layout.fragmentdaily_item_top, lvDaily, false);
        vpTop = (ViewPager) header.findViewById(R.id.vp_top);
        vpTop.setAdapter(mTopPagerAdapter);
        lvDaily.addHeaderView(header);
        lvDaily.setAdapter(mDailyListAdapter);
        lvDaily.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lvDaily != null && lvDaily.getChildCount() > 0) {
                   // boolean enable = (firstVisibleItem == 0) && (view.getChildAt(firstVisibleItem).getTop() == 0);
                    // ((MainActivity) mActivity).setSwipeRefreshEnable(enable);//到顶刷新
                    if (firstVisibleItem + visibleItemCount == totalItemCount && !isLoading) {
                        isLoading = true;
                        mPresenter.getMoreDailyData(date);
                    }
                }
            }
        });
        lvDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DailyListBean.StoriesBean stories = ( DailyListBean.StoriesBean) parent.getAdapter().getItem(position);
                realmHelper.addReadStateBean(String .valueOf(stories.getId()));
                Intent intent = new Intent();
                intent.setClass(mContext, DailyDetailActivity.class);
                intent.putExtra("id",stories.getId());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, "shareView");
                mContext.startActivity(intent,options.toBundle());
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getDailyData();
            }
        });
    }

    @Override
    public void showContent(DailyListBean dailyListBean) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.hide();
        }
        mList.clear();
        DailyListBean.StoriesBean storiesBean = new DailyListBean.StoriesBean();
        storiesBean.setType(Constants.DAILY_TOPIC);
        storiesBean.setTitle("木易咩");
        mList = dailyListBean.getStories();
        mList.add(0,storiesBean);
        date = dailyListBean.getDate();
        mDailyListAdapter.addList(mList);
        mTopPagerAdapter.addTopStories(dailyListBean);
        isLoading = false;
    }

    @Override
    public void addMoreContent(DailyBeforeListBean dailyBeforeListBean) {
        mList.clear();
        mList = dailyBeforeListBean.getStories();
        DailyListBean.StoriesBean storiesBean = new DailyListBean.StoriesBean();
        storiesBean.setType(Constants.DAILY_TOPIC);
        storiesBean.setTitle(convertDate(dailyBeforeListBean.getDate()));
        mList.add(0,storiesBean);
        mDailyListAdapter.addMoreList(mList);
        date = dailyBeforeListBean.getDate();
        isLoading = false;
    }

    @Override
    public void showError(String msg) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.hide();
        }
       // SnackbarUtil.showShort(lvDaily, msg);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
    private String convertDate(String date) {
        String result = date.substring(0, 4);
        result += "年";
        result += date.substring(4, 6);
        result += "月";
        result += date.substring(6, 8);
        result += "日";
        return result;
    }
}
