package com.cn.gov.jms.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cn.gov.jms.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView imageView;
    @BindView(R.id.about_app)
    RelativeLayout about_app;
    @BindView(R.id.clear)
    RelativeLayout clear;
    @BindView(R.id.update)
    RelativeLayout update;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

    }


    @OnClick({ R.id.iv_back,R.id.about_app,R.id.update,R.id.clear})
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.iv_back:
               finish();
               break;
           case R.id.about_app:
               Toast.makeText(this,"关于APP", Toast.LENGTH_SHORT).show();
               break;
           case R.id.clear:
               Toast.makeText(this,"清除缓存成功", Toast.LENGTH_SHORT).show();
               break;
           case R.id.update:
               Toast.makeText(this,"已经是最新版本!", Toast.LENGTH_SHORT).show();
               break;
       }
    }
}
