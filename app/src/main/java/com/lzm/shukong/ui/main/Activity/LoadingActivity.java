package com.lzm.shukong.ui.main.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzm.shukong.R;
import com.lzm.shukong.base.BaseActivity;
import com.lzm.shukong.presenter.LoadingPresenter;
import com.lzm.shukong.presenter.contract.LoadingContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/26.
 */

public class LoadingActivity extends BaseActivity<LoadingPresenter> implements LoadingContract.view {


    @BindView(R.id.welcomeimgview)
    ImageView welcomeimgview;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    @BindView(R.id.loadingImgView)
    ImageView loadingImgView;


    @Override
    protected int getLayout() {
        return R.layout.activity_loading;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getLoadingImgData();
    }

    @Override
    protected void initInject() {
        getActivityComponter().inject(this);
    }

    @Override
    public void showContent(Bitmap bitmap) {
        btnSkip.setVisibility(View.VISIBLE);
        welcomeimgview.setVisibility(View.GONE);
        loadingImgView.setVisibility(View.VISIBLE);
        loadingImgView.setImageBitmap(bitmap);
    }

    @Override
    public void jumpToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mContext.startActivity(new Intent(mContext, MainActivity.class));
                finish();
                mContext.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }, 2000);
    }

    @Override
    public void showError(String msg) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btn_skip)
    public void skipToMain() {
        mContext.startActivity(new Intent(mContext, MainActivity.class));
        finish();
        mContext.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

}
