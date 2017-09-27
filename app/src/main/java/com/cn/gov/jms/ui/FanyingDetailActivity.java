package com.cn.gov.jms.ui;

import android.view.View;
import android.widget.TextView;

import com.cn.gov.jms.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FanyingDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_fujian)
    TextView tv_fujian;
    @BindView(R.id.tv_dept)
    TextView tv_dept;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_reply)
    TextView tv_reply;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fanying_detail;
    }

    @Override
    protected void initView() {

    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
