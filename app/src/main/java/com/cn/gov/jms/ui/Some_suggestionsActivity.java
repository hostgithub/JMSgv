package com.cn.gov.jms.ui;

import android.view.WindowManager;

import com.cn.gov.jms.base.BaseActivity;

public class Some_suggestionsActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_some_suggestions;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// 让输入框不自动打开输入法
    }
}
