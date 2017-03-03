package com.lzm.shukong.ui.meipai.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.shukong.R;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.base.BaseFragment;
import com.lzm.shukong.modul.bean.meipai.MeipaiBean;
import com.lzm.shukong.presenter.MeipaiListPresenter;
import com.lzm.shukong.presenter.contract.MeipaiListContract;
import com.lzm.shukong.ui.meipai.adapter.MeipaiListAdapter;
import com.lzm.shukong.ui.meipai.adapter.MeipaiListItemDecoration;
import com.lzm.shukong.util.SnackbarUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/19.
 */

public class MeipaiListFragment extends BaseFragment<MeipaiListPresenter> implements MeipaiListContract.View {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.iv_progress)
    AVLoadingIndicatorView ivProgress;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    MeipaiListItemDecoration itemDecoration;
    MeipaiListAdapter meipaiListAdapter;
    List<MeipaiBean> mlist;
    
    private String topicName;
    boolean isLoadingMore = false;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meipailist;
    }

    @Override
    protected void initEventAndData() {
        mlist = new ArrayList<>();
        itemDecoration = new MeipaiListItemDecoration();
        topicName = getArguments().getString(Constants.IT_MEIPAI_TOPIC);
        recyclerview.addItemDecoration(itemDecoration);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mPresenter.getMeipaiDate(topicName);
        meipaiListAdapter = new MeipaiListAdapter(mContext,mlist);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(meipaiListAdapter);
        ivProgress.show();
        
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) recyclerview.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = recyclerview.getLayoutManager().getItemCount();
                if (lastVisibleItem >= (totalItemCount - 2) && dy > 0) {  //还剩2个Item时加载更多
                    if(!isLoadingMore){
                        isLoadingMore = true;
                        mPresenter.getMoreMeipaiDate();
                    }
                }
            }
        });
        

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMeipaiDate(topicName);
            }
        });
    }

    @Override
    public void showError(String msg) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.hide();
        }
        SnackbarUtil.showShort(recyclerview,msg);
    }

    @Override
    public void showContent(List<MeipaiBean> listbean) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
            ivProgress.hide();
        }
        mlist.clear();
        mlist.addAll(listbean);
        meipaiListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<MeipaiBean> list, int startPage, int entPage) {
        ivProgress.hide();
        mlist.addAll(list);
        meipaiListAdapter.updateData(mlist);
        meipaiListAdapter.notifyDataSetChanged();
        isLoadingMore = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
