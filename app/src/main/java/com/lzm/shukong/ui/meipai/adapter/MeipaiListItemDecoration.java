package com.lzm.shukong.ui.meipai.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lzm.shukong.app.App;
import com.lzm.shukong.util.SystemUtil;

/**
 * Created by Administrator on 2017/2/21.
 * 设置recycleView的item之间间距
 */

public class MeipaiListItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) {
            if (position == 0) {
                outRect.set(0, SystemUtil.dp2px(App.getInstance(), 15), 0, 0);
            } else {
                outRect.set(0, SystemUtil.dp2px(App.getInstance(), 0.5f), 0, 0);
            }
        }
    }
}
