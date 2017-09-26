package com.cn.gov.jms.ui;

import android.view.View;

import com.cn.gov.jms.base.BaseActivity;

import butterknife.OnClick;

public class AboutusDetailActivity extends BaseActivity {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus_detail;
    }

    @Override
    protected void initView() {

    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
