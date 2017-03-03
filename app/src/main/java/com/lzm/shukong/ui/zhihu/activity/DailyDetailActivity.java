package com.lzm.shukong.ui.zhihu.activity;

import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzm.shukong.R;
import com.lzm.shukong.base.BaseActivity;
import com.lzm.shukong.modul.bean.zhihubean.DetailExtraBean;
import com.lzm.shukong.modul.bean.zhihubean.ZhihuDetailBean;
import com.lzm.shukong.presenter.ZhihuDetailPresenter;
import com.lzm.shukong.presenter.contract.ZhihuDetailContract;
import com.lzm.shukong.util.HtmlUtil;
import com.lzm.shukong.util.SharedPreferenceUtil;
import com.lzm.shukong.util.SnackbarUtil;
import com.lzm.shukong.util.SystemUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wang.avi.AVLoadingIndicatorView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/2.
 */

public class DailyDetailActivity extends BaseActivity<ZhihuDetailPresenter> implements ZhihuDetailContract.View {

    @BindView(R.id.detail_bar_image)
    ImageView detailBarImage;
    @BindView(R.id.detail_bar_copyright)
    TextView detailBarCopyright;
    @BindView(R.id.view_toolbar)
    Toolbar viewToolbar;
    @BindView(R.id.clp_toolbar)
    CollapsingToolbarLayout clpToolbar;
    @BindView(R.id.wv_detail_content)
    WebView wvDetailContent;
    @BindView(R.id.iv_progress)
    AVLoadingIndicatorView ivProgress;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.tv_detail_bottom_like)
    TextView tvDetailBottomLike;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView tvDetailBottomComment;
    @BindView(R.id.tv_detail_bottom_share)
    TextView tvDetailBottomShare;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout llDetailBottom;
    @BindView(R.id.fab_like)
    FloatingActionButton fabLike;

    int id = 0;
    int allNum = 0;
    int shortNum = 0;
    int longNum = 0;
    String shareUrl;
    String imgUrl;
    boolean isBottomShow = true;
    boolean isImageShow = false;
    boolean isTransitionEnd = false;
    boolean isNotTransition = false;

    @Override
    protected void initInject() {
        getActivityComponter().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dailydetail;
    }
    
    @Override
    protected void initEventAndData() {
        setToolBar(viewToolbar,"");
        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");
        isNotTransition = intent.getBooleanExtra("isNotTransition",false);
        mPresenter.queryLikeData(id);
        mPresenter.getDetailData(id);
        mPresenter.getExtraData(id);
        ivProgress.show();
        WebSettings settings = wvDetailContent.getSettings();
        if (SharedPreferenceUtil.getNoImageState()) {
            settings.setBlockNetworkImage(true);
        }
        if (SharedPreferenceUtil.getAutoCacheState()) {
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (SystemUtil.isNetworkConnected()) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        wvDetailContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        nsvScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY - oldScrollY > 0 && isBottomShow) {  //下移隐藏
                    isBottomShow = false;
                    llDetailBottom.animate().translationY(llDetailBottom.getHeight());
                } else if(scrollY - oldScrollY < 0 && !isBottomShow){    //上移出现
                    isBottomShow = true;
                    llDetailBottom.animate().translationY(0);
                }
            }
        });
        (getWindow().getSharedElementEnterTransition()).addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }
            @Override
            public void onTransitionEnd(Transition transition) {
                /**
                 * 测试发现部分手机(如红米note2)上加载图片会变形,没有达到centerCrop效果
                 * 查阅资料发现Glide配合SharedElementTransition是有坑的,需要在Transition动画结束后再加载图片
                 * https://github.com/TWiStErRob/glide-support/blob/master/src/glide3/java/com/bumptech/glide/supportapp/github/_847_shared_transition/DetailFragment.java
                 */
                isTransitionEnd = true;
                if (imgUrl != null) {
                    isImageShow = true;
                    Glide.with(mContext).load(imgUrl).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(detailBarImage);
                }
            }
            @Override
            public void onTransitionCancel(Transition transition) {
            }
            @Override
            public void onTransitionPause(Transition transition) {
            }
            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    @Override
    public void showContent(ZhihuDetailBean zhihuDetailBean) {
        ivProgress.hide();
        imgUrl = zhihuDetailBean.getImage();
        shareUrl = zhihuDetailBean.getShare_url();
        if (isNotTransition) {
            Glide.with(mContext).load(zhihuDetailBean.getImage()).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(detailBarImage);
            
        } else {
            if (!isImageShow && isTransitionEnd) {
                Glide.with(mContext).load(zhihuDetailBean.getImage()).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(detailBarImage);
            }
        }
        clpToolbar.setTitle(zhihuDetailBean.getTitle());
        detailBarCopyright.setText(zhihuDetailBean.getImage_source());
        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(),zhihuDetailBean.getCss(),zhihuDetailBean.getJs());
        wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showExtraInfo(DetailExtraBean detailExtraBean) {
        ivProgress.hide();
        tvDetailBottomLike.setText(String.format("%d个赞",detailExtraBean.getPopularity()));
        tvDetailBottomComment.setText(String.format("%d条评论",detailExtraBean.getComments()));
        allNum = detailExtraBean.getComments();
        shortNum = detailExtraBean.getShort_comments();
        longNum = detailExtraBean.getLong_comments();
    }

    @Override
    public void setLikeButtonState(boolean isLiked) {
        fabLike.setSelected(isLiked);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvDetailContent.canGoBack()) {
            wvDetailContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showError(String msg) {
        ivProgress.hide();
        SnackbarUtil.showShort(getWindow().getDecorView(),msg);
    }

    @OnClick(R.id.tv_detail_bottom_comment)
    void gotoComment() {
//        Intent intent = getIntent();
//        intent.setClass(this,CommentActivity.class);
//        intent.putExtra("id",id);
//        intent.putExtra("allNum",allNum);
//        intent.putExtra("shortNum",shortNum);
//        intent.putExtra("longNum",longNum);
//        startActivity(intent);
    }

    @OnClick(R.id.tv_detail_bottom_share)
    void shareUrl() {
 //       ShareUtil.shareText(mContext,shareUrl,"分享一篇文章");
    }

    @OnClick(R.id.fab_like)
    void likeArticle() {
        if (fabLike.isSelected()) {
            fabLike.setSelected(false);
            mPresenter.deleteLikeData();
        } else {
            fabLike.setSelected(true);
            mPresenter.insertLikeData();
        }
    }


    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            finishAfterTransition();
        }
    }
}
