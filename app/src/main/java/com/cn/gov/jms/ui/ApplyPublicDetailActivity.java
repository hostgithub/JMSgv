package com.cn.gov.jms.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.ApplyPublicDetail;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyPublicDetailActivity extends BaseActivity
{
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.tv_zhizhao_info)
    TextView tv_zhizhao_info;
    @BindView(R.id.tv_daibiao)
    TextView tv_daibiao;
    @BindView(R.id.tv_code)
    TextView tv_code;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_dept)
    TextView tv_dept;
    @BindView(R.id.tv_kind)
    TextView tv_kind;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_email_code)
    TextView tv_email_code;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_chuanzhen)
    TextView tv_chuanzhen;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_apply_kind)
    TextView tv_apply_kind;
    @BindView(R.id.tv_apply_time)
    TextView tv_apply_time;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_use)
    TextView tv_use;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_reply_content)
    TextView tv_reply_content;
    @BindView(R.id.tv_reply_dept)
    TextView tv_reply_dept;
    @BindView(R.id.tv_reply_time)
    TextView tv_reply_time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_public_detail;
    }

    @Override
    protected void initView() {

//        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT);
        //mDetailWebView.setLayoutParams(mWebViewLP);
        //mDetailWebView.setInitialScale(100);ac
        Intent intent = getIntent();
        ApplyPublicDetail.ResultsBean resultsBean= (ApplyPublicDetail.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        if(resultsBean.getCompany_name()!=null){
            tv_company.setText(resultsBean.getCompany_name());
        }
        tv_zhizhao_info.setText(resultsBean.getBusiness_license());
        tv_daibiao.setText(resultsBean.getLegal_representative());
        tv_code.setText(resultsBean.getOrganization_code());
        tv_name.setText(resultsBean.getName());
        tv_dept.setText(resultsBean.getDept_name());
        tv_kind.setText(resultsBean.getIdentity_typestr());
        tv_number.setText(resultsBean.getIdentity_number());
        tv_address.setText(resultsBean.getAddress());
        tv_email_code.setText(resultsBean.getCode());
        tv_phone.setText(resultsBean.getIphone());
        tv_chuanzhen.setText(resultsBean.getFax());
        tv_email.setText(resultsBean.getEmail());
        tv_apply_kind.setText(resultsBean.getTypestr());
        tv_apply_time.setText(resultsBean.getCreateTime());
        tv_title.setText(resultsBean.getTitle());
        tv_content.setText(resultsBean.getContent1());
        tv_use.setText(resultsBean.getPurpose());
        tv_state.setText(resultsBean.getState1str());
        tv_reply_content.setText(resultsBean.getHandle_content1());
        tv_reply_dept.setText(resultsBean.getHandle_name());
        tv_reply_time.setText(resultsBean.getHandle_time());

    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
