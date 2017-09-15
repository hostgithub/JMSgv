package com.cn.gov.jms.ui;

import android.view.View;
import android.widget.ImageView;

import com.cn.gov.jms.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ZhengWuGongKaiActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView imageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zheng_wu_gong_kai;
    }

    @Override
    protected void initView() {

    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
