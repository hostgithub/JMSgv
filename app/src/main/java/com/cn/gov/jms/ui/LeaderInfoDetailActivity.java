package com.cn.gov.jms.ui;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.SqgkDetail;

import butterknife.BindView;
import butterknife.OnClick;

public class LeaderInfoDetailActivity extends BaseActivity
{
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.action_bar_title)
    TextView action_bar_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leader_info_detail;
    }

    @Override
    protected void initView() {

//        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT);
        //mDetailWebView.setLayoutParams(mWebViewLP);
        //mDetailWebView.setInitialScale(100);
        WebSettings webSettings = webView .getSettings();
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片  用于加载网络 本地不适用
        Intent intent = getIntent();
        SqgkDetail.ResultsBean resultsBean= (SqgkDetail.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        webView.loadDataWithBaseURL(Config.BANNER_BASE_URL, resultsBean.content, "text/html", "utf-8", null);
        action_bar_title.setText(Html.fromHtml(resultsBean.title));
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
