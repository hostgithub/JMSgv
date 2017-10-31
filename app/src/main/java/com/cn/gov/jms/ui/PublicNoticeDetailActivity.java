package com.cn.gov.jms.ui;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.Detail;

import butterknife.BindView;
import butterknife.OnClick;

public class PublicNoticeDetailActivity extends BaseActivity
{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_from)
    TextView tv_from;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_content)
    WebView webView;
    @BindView(R.id.action_bar_title)
    TextView action_bar_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_page_detail;
    }

    @Override
    protected void initView() {

//        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT);
        //mDetailWebView.setLayoutParams(mWebViewLP);
        //mDetailWebView.setInitialScale(100);ac

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

//支持插件
        //webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
        //webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        //webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        action_bar_title.setText("公告公示");
        Intent intent = getIntent();
        Detail.ResultsBean resultsBean= (Detail.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        //tv_content.setText(Html.fromHtml(resultsBean.content));
        webView.loadDataWithBaseURL(Config.BANNER_BASE_URL, resultsBean.content, "text/html", "utf-8", null);
        tv_title.setText(Html.fromHtml(resultsBean.title));
        tv_from.setText(Html.fromHtml("来源:"+resultsBean.source));
        tv_time.setText(Html.fromHtml("发布时间:"+resultsBean.addTime));
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
