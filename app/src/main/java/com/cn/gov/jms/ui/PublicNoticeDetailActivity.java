package com.cn.gov.jms.ui;

import android.content.Intent;
import android.text.Html;
import android.view.View;
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
    TextView tv_content;
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

        action_bar_title.setText("公告公示");
        Intent intent = getIntent();
        Detail.ResultsBean resultsBean= (Detail.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        tv_content.setText(Html.fromHtml(resultsBean.content));
        tv_title.setText(Html.fromHtml(resultsBean.title));
        tv_from.setText(Html.fromHtml("来源:"+resultsBean.source));
        tv_time.setText(Html.fromHtml("发布时间:"+resultsBean.addTime));
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
