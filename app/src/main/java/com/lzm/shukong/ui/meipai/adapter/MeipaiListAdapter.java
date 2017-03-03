package com.lzm.shukong.ui.meipai.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzm.shukong.R;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.modul.bean.meipai.MeipaiBean;
import com.lzm.shukong.ui.meipai.activity.MeipaiDetailActivity;
import com.lzm.shukong.weight.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class MeipaiListAdapter extends RecyclerView.Adapter<MeipaiListAdapter.MyViewHolder> {

   
    private LayoutInflater inflater;
    private List<MeipaiBean> mlist;
    private Context mContext;

    public void updateData(List<MeipaiBean> list) {
        this.mlist = list;
    }

    public MeipaiListAdapter(Context mContext , List<MeipaiBean> list) {
        inflater = LayoutInflater.from(mContext);
        this.mlist = list;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_meipailist_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MeipaiBean meipaiBean = mlist.get(position);
        Glide.with(mContext).load(meipaiBean.getAvatar()).centerCrop().into((holder.itemVideoHeader));
        holder.itemVideoName.setText(meipaiBean.getScreen_name());
        Glide.with(mContext).load(meipaiBean.getCover_pic()).centerCrop().into(holder.itemVideoCover);
        holder.itemVideoCaption.setText(meipaiBean.getCaption());
        holder.itemVideoPlayNum.setText(meipaiBean.getPlays_count()+"");
        holder.itemVideoLikeNum.setText(meipaiBean.getLikes_count()+"");
        holder.itemVideoCommentNum.setText(meipaiBean.getComments_count()+"");

        holder.itemView.setOnClickListener(new MyOnItemClickListener(position));
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.item_video_header)
        CircleImageView itemVideoHeader;
        //@BindView(R.id.item_video_name)
        TextView itemVideoName;
        //@BindView(R.id.item_video_cover)
        ImageView itemVideoCover;
        //@BindView(R.id.item_video_caption)
        TextView itemVideoCaption;
        //@BindView(R.id.item_video_play_num)
        TextView itemVideoPlayNum;
        //@BindView(R.id.item_video_like_num)
        TextView itemVideoLikeNum;
        //@BindView(R.id.item_video_comment_num)
        TextView itemVideoCommentNum;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemVideoHeader = (CircleImageView) itemView.findViewById(R.id.item_video_header);
            itemVideoName = (TextView) itemView.findViewById(R.id.item_video_name);
            itemVideoCover = (ImageView) itemView.findViewById(R.id.item_video_cover);
            itemVideoCaption = (TextView) itemView.findViewById(R.id.item_video_caption);
            itemVideoPlayNum = (TextView) itemView.findViewById(R.id.item_video_play_num);
            itemVideoLikeNum = (TextView) itemView.findViewById(R.id.item_video_like_num);
            itemVideoCommentNum = (TextView) itemView.findViewById(R.id.item_video_comment_num);
            //ButterKnife.bind(this.itemView);
        }
    }
    public class MyOnItemClickListener implements View.OnClickListener{
        int position;

        public MyOnItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, MeipaiDetailActivity.class);
            intent.putExtra(Constants.IT_LIKE_URL,mlist.get(position).getUrl());
            intent.putExtra(Constants.IT_LIKE_TITLE,mlist.get(position).getCaption());
            intent.putExtra(Constants.IT_LIKE_IMAGE,mlist.get(position).getCover_pic());
            intent.putExtra(Constants.IT_LIKE_ID,String.valueOf(mlist.get(position).getId()));
            intent.putExtra(Constants.IT_LIKE_TYPE,Constants.TYPE_MEIPAI);
            mContext.startActivity(intent);
            //ToastUtil.shortShow(mlist.get(position).getUrl());
        }
    }
}
