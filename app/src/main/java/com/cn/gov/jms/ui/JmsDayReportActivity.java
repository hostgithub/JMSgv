package com.cn.gov.jms.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.cn.gov.jms.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class JmsDayReportActivity extends BaseActivity {

    @BindView(R.id.webView1)
    WebView mWebview;
    WebSettings mWebSettings;
    @BindView(R.id.text_beginLoading)
    TextView beginLoading;
    @BindView(R.id.text_endLoading)
    TextView endLoading;
    @BindView(R.id.text_Loading)
    TextView loading;
    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    private String position;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jms_day_report;
    }

    @Override
    protected void initView() {

        Intent intent=getIntent();
        position=intent.getStringExtra("position");

        mWebSettings = mWebview.getSettings();
//设置支持js 可以加载出网站的轮播图 可能也不完全好使 此页面没好使
        mWebSettings.setJavaScriptEnabled(true);//支持js
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出alert
        mWebview.requestFocusFromTouch();//支持获取手势焦点，输入用户名、密码或其他

//设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        mWebSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        mWebSettings.setAllowFileAccess(true); //设置可以访问文件
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mWebSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

//        mWebview.loadUrl("http://www.baidu.com/");
        switch (position){
            case "7":
                mWebview.loadUrl("http://www.jmstv.cc/");
                break;

            case "8":
                mWebview.loadUrl("http://jiamusi.dbw.cn/");
                break;
            default:
                break;
        }


        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                action_bar_title.setText(title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    //loading.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    //loading.setText(progress);
                }
            }
        });


        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");
                //beginLoading.setText("开始加载了");

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                //endLoading.setText("结束加载了");

            }
        });
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
