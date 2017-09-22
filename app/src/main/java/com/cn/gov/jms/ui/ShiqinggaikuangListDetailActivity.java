package com.cn.gov.jms.ui;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.SqgkDetail;

import butterknife.BindView;
import butterknife.OnClick;

public class ShiqinggaikuangListDetailActivity extends BaseActivity
{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.action_bar_title)
    TextView action_bar_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shiqinggaikuang_list_detail;
    }

    @Override
    protected void initView() {

//        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT);
        //mDetailWebView.setLayoutParams(mWebViewLP);
        //mDetailWebView.setInitialScale(100);
        Intent intent = getIntent();
        SqgkDetail.ResultsBean resultsBean= (SqgkDetail.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        tv_content.setText(Html.fromHtml(resultsBean.content));
        tv_title.setText(Html.fromHtml(resultsBean.title));
        action_bar_title.setText("新闻详情");
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
