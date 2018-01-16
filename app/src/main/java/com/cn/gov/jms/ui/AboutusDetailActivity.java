package com.cn.gov.jms.ui;

import android.view.View;
import android.widget.TextView;

import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.utils.AppInfoUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutusDetailActivity extends BaseActivity {

    @BindView(R.id.version_code)
    TextView version_code;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus_detail;
    }

    @Override
    protected void initView() {
        version_code.setText("当前版本"+ AppInfoUtil.getVersionName(this));
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
