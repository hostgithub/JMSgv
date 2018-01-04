package com.cn.gov.jms.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cn.gov.jms.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PublicServicesListActivity extends BaseActivity{

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.edu_info)
    RelativeLayout edu_info;
    @BindView(R.id.social_security)
    RelativeLayout social_security;
    @BindView(R.id.social_assistance)
    RelativeLayout social_assistance;
    @BindView(R.id.health_care_student)
    RelativeLayout health_care_student;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_public_services_list;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.iv_back,R.id.edu_info, R.id.social_security, R.id.social_assistance, R.id.health_care_student })
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.edu_info:
                Intent intent1=new Intent(PublicServicesListActivity.this,PublicServicesActivity.class);
                intent1.putExtra("public_services_list_id","3001002000040001");
                startActivity(intent1);
                break;
            case R.id.social_security:
                Intent intent2=new Intent(PublicServicesListActivity.this,PublicServicesActivity.class);
                intent2.putExtra("public_services_list_id","3001002000040003");
                startActivity(intent2);
                break;
            case R.id.social_assistance:
                Intent intent3=new Intent(PublicServicesListActivity.this,PublicServicesActivity.class);
                intent3.putExtra("public_services_list_id","3001002000040002");
                startActivity(intent3);
                break;
            case R.id.health_care_student:
                Intent intent4=new Intent(PublicServicesListActivity.this,PublicServicesActivity.class);
                intent4.putExtra("public_services_list_id","3001002000040004");
                startActivity(intent4);
                break;
            default:
                break;
        }
    }

}
