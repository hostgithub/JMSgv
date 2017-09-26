package com.cn.gov.jms.ui;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.Detail;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity
{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_from)
    TextView tv_from;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_content)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {

//        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT);
        //mDetailWebView.setLayoutParams(mWebViewLP);
        //mDetailWebView.setInitialScale(100);

        Intent intent = getIntent();
        //mDetailWebView.loadUrl(it.getStringExtra(Config.NEWS));
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
}
