package com.lzm.shukong.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzm.shukong.R;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.modul.bean.zhihubean.DailyListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class DailyListAdapter extends BaseAdapter {

    Context mContext;
    List<DailyListBean.StoriesBean> mList;
    LayoutInflater inflater;

    public DailyListAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        inflater = LayoutInflater.from(mContext);
    }

    public void addList(List<DailyListBean.StoriesBean> items) {
        this.mList.clear();
        this.mList.addAll(items);
        notifyDataSetChanged();
    } 
    public void addMoreList(List<DailyListBean.StoriesBean> items) {
        this.mList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainListViewHolder viewHolder;
        if (convertView == null) {
            inflater = LayoutInflater.from(mContext);
            viewHolder = new MainListViewHolder();
            convertView = inflater.inflate(R.layout.fragmentdaily_item_content, parent, false);
            viewHolder.iv_daily_item_image = (ImageView) convertView.findViewById(R.id.iv_daily_item_image);
            viewHolder.tv_daily_item_title = (TextView) convertView.findViewById(R.id.tv_daily_item_title);
            viewHolder.tv_daily_item_date = (TextView) convertView.findViewById(R.id.tv_daily_item_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MainListViewHolder) convertView.getTag();
        }
        if (mList.get(position).getReadState()) {
            viewHolder.tv_daily_item_title.setTextColor(ContextCompat.getColor(mContext, R.color.news_read));
        } else {
            viewHolder.tv_daily_item_title.setTextColor(ContextCompat.getColor(mContext, R.color.news_unread));
        }
        DailyListBean.StoriesBean storie = mList.get(position);
        if (storie.getType() == Constants.DAILY_TOPIC) {
            viewHolder.iv_daily_item_image.setVisibility(View.GONE);
            viewHolder.tv_daily_item_title.setVisibility(View.GONE);
            viewHolder.tv_daily_item_date.setVisibility(View.VISIBLE);
            viewHolder.tv_daily_item_date.setText(storie.getTitle());
        } else {
            viewHolder.iv_daily_item_image.setVisibility(View.VISIBLE);
            viewHolder.tv_daily_item_title.setVisibility(View.VISIBLE);
            viewHolder.tv_daily_item_date.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(mList.get(position).getImages().get(0))
                    .crossFade()
                    .centerCrop()
                    .into(viewHolder.iv_daily_item_image);
            viewHolder.tv_daily_item_title.setText(mList.get(position).getTitle());
        }
        return convertView;
    }
    public class MainListViewHolder {
        ImageView iv_daily_item_image;
        TextView tv_daily_item_title;
        TextView tv_daily_item_date;
    }
}
