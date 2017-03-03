package com.lzm.shukong.ui.meipai.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzm.shukong.R;
import com.lzm.shukong.app.App;
import com.lzm.shukong.app.Constants;
import com.lzm.shukong.base.SimpleActivity;
import com.lzm.shukong.modul.bean.comment.CollectBean;
import com.lzm.shukong.modul.db.RealmHelper;
import com.lzm.shukong.util.ToastUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MeipaiDetailActivity extends SimpleActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.tv_progress)
    TextView tvProgress;

    
    RealmHelper mRealmHelper ;
    MenuItem menuItem;
    String url,title,image,id;
    int type;
    boolean isLiked;


    @Override
    protected int getLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initEventAndData() {
        mRealmHelper = App.getApplicationComponent().REALM_HELPER();
        url = getIntent().getStringExtra(Constants.IT_LIKE_URL);
        title = getIntent().getStringExtra(Constants.IT_LIKE_TITLE);
        image = getIntent().getStringExtra(Constants.IT_LIKE_IMAGE);
        type = getIntent().getIntExtra(Constants.IT_LIKE_TYPE,Constants.TYPE_ZHIHU);
        id = getIntent().getStringExtra(Constants.IT_LIKE_ID);
        setToolBar(toolBar, title);
        WebSettings webSettings = webview.getSettings();
        webview.setVerticalScrollBarEnabled(false);//垂直滚动条不显示
        webSettings.setJavaScriptEnabled(true);//设置WebView是否允许执行JavaScript脚本，默认false，不允许。
        webSettings.setBuiltInZoomControls(true);//是否使用内置的缩放机制。
        webSettings.setDisplayZoomControls(false);//使用内置的缩放机制时是否展示缩放控件，默认值true。
        webSettings.setSupportZoom(true);//WebView是否支持使用屏幕上的缩放控件和手势进行缩放，默认值true。

        webSettings.setDomStorageEnabled(true);//DOM存储API是否可用，默认false。
        webSettings.setDatabaseEnabled(true);//数据库存储API是否可用，默认值false。
        // 全屏显示
        webSettings.setLoadWithOverviewMode(true);//是否允许WebView度超出以概览的方式载入页面，默认false。
        webSettings.setUseWideViewPort(true);//WebView是否支持HTML的“viewport”标签或者使用wide viewport。
//        webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {//设置返回true指网页中点击链接后 在当前webview中跳转，不会转到浏览器
//                view.loadUrl(url);
//                return false;
//            }
//        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (tvProgress == null)
                    return;
                if (newProgress == 100) {
                    tvProgress.setVisibility(View.GONE);
                } else {
                    tvProgress.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams lp = tvProgress.getLayoutParams();
                    lp.width = App.SCREEN_WIDTH * newProgress / 100;
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });
        webview.loadUrl(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meipai_menu,menu);
        menuItem = menu.findItem(R.id.like_menu);
        setLikeState(mRealmHelper.queryCollectBean(id));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.like_menu:
                if (isLiked){
                    item.setIcon(R.mipmap.ic_toolbar_like_n);
                    mRealmHelper.deleteCollectBean(id);
                    isLiked = false;
                }else {
                    item.setIcon(R.mipmap.ic_toolbar_like_p);
                    CollectBean collectBean = new CollectBean();
                    collectBean.setId(id);
                    collectBean.setImage(image);
                    collectBean.setTitle(title);
                    collectBean.setType(type);
                    collectBean.setUrl(url);
                    collectBean.setTime(System.currentTimeMillis());
                    mRealmHelper.addCollectBean(collectBean);
                    isLiked = true;
                }
                break;
            case R.id.copy_menu:
                ToastUtil.shortShow("复制视频地址到剪切板");
                break;
            case R.id.share_menu:
                ToastUtil.shortShow("分享视频地址");
                showShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    private void setLikeState(boolean state) {
        if(state) {
            menuItem.setIcon(R.mipmap.ic_toolbar_like_p);
            isLiked = true;
        } else {
            menuItem.setIcon(R.mipmap.ic_toolbar_like_n);
            isLiked = false;
        }
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
}
