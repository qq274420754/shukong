package com.lzm.shukong.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzm.shukong.R;
import com.lzm.shukong.modul.bean.zhihubean.DailyListBean;
import com.lzm.shukong.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class TopPagerAdapter extends PagerAdapter {

    private List<DailyListBean.TopStoriesBean> mList = new ArrayList<>();
    private Context mContext;

    public TopPagerAdapter( Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top_pager, container, false);
        ImageView ivImage = (ImageView) view.findViewById(R.id.iv_top_image);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_top_title);
        Glide.with(mContext).load(mList.get(position).getImage()).centerCrop().crossFade().into(ivImage);
        tvTitle.setText(mList.get(position).getTitle());
        final int id = mList.get(position).getId();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortShow("点击了"+mList.get(position).getTitle());
//                Intent intent = new Intent();
//                intent.setClass(mContext, ZhihuDetailActivity.class);
//                intent.putExtra("id",id);
//                intent.putExtra("isNotTransition",true);
//                mContext.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    
    public void addTopStories(DailyListBean dailyListBean){
        mList.addAll(dailyListBean.getTop_stories());
        notifyDataSetChanged();
    }
}
