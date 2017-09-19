package com.cn.gov.jms.ui;

import android.view.View;

import com.cn.gov.jms.base.BaseActivity;

import butterknife.OnClick;

public class ZhengminHudongActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhengmin_hudong;
    }

    @Override
    protected void initView() {

    }
    @OnClick({ R.id.iv_back,R.id.system_of_government_information_publicity,R.id.government_information_public_directory,
            R.id.guide_to_government_information_disclosure,R.id.open_in_accordance_with_the_application,R.id.government_information_public_briefing,
            R.id.annual_report_on_government_information_work,R.id.important_area})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
            case R.id.system_of_government_information_publicity:finish();
                break;
            case R.id.government_information_public_directory:finish();
                break;
            case R.id.guide_to_government_information_disclosure:finish();
                break;
            case R.id.open_in_accordance_with_the_application:finish();
                break;
            case R.id.government_information_public_briefing:finish();
                break;
            case R.id.annual_report_on_government_information_work:finish();
                break;
            case R.id.important_area:finish();
                break;
            default:
                break;
        }
    }
}
