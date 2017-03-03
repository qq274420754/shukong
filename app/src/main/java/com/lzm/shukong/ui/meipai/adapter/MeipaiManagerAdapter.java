package com.lzm.shukong.ui.meipai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lzm.shukong.R;
import com.lzm.shukong.modul.bean.meipai.meipaiManagerItemBean;

import io.realm.RealmList;

import static com.lzm.shukong.app.Constants.TITLES;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MeipaiManagerAdapter extends RecyclerView.Adapter<MeipaiManagerAdapter.ViewHolder> {


    private RealmList<meipaiManagerItemBean> mList;
    private LayoutInflater inflater;

    public MeipaiManagerAdapter(RealmList<meipaiManagerItemBean> mList, Context mContext) {
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.activity_meipaiitem, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(TITLES[mList.get(position).getIndex()]);
        holder.switchview.setChecked(mList.get(position).getIsSelect());
        holder.switchview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mList.get(position).setIsSelect(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return TITLES.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.tv_name)
        TextView tvName;
        //@BindView(R.id.switchview)
        SwitchCompat switchview;

        public ViewHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            switchview = (SwitchCompat) itemView.findViewById(R.id.switchview);
        }
    }
}
