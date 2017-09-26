package com.cn.gov.jms.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.utils.DataCleanManagerUtils;

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
    @BindView(R.id.tv_size)
    TextView tv_size;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        try {
            tv_size.setText(DataCleanManagerUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({ R.id.iv_back,R.id.about_app,R.id.update,R.id.clear})
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.iv_back:
               finish();
               break;
           case R.id.about_app:
               startActivity(new Intent(SettingActivity.this, AboutusDetailActivity.class));
               break;
           case R.id.clear:
               try {
                   DataCleanManagerUtils.clearAllCache(this);
                   tv_size.setText(DataCleanManagerUtils.getTotalCacheSize(this));
                   Toast.makeText(this,"清除缓存成功!", Toast.LENGTH_SHORT).show();
               } catch (Exception e) {
                   e.printStackTrace();
               }
               break;
           case R.id.update:
               Toast.makeText(this,"已经是最新版本!", Toast.LENGTH_SHORT).show();
               break;
       }
    }
}
