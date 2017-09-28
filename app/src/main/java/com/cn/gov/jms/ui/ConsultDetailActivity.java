package com.cn.gov.jms.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.ResponseBean;
import com.cn.gov.jms.model.ZixunFanyingDetailBean;
import com.cn.gov.jms.services.Api;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultDetailActivity extends BaseActivity {

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
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rb1)
    RadioButton rb1;
    private String advice="1";
    private Intent intent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consult_detail;
    }

    @Override
    protected void initView() {
        rb1.setChecked(true);
        intent = getIntent();
        //mDetailWebView.loadUrl(it.getStringExtra(Config.NEWS));
        ZixunFanyingDetailBean.ResultsBean resultsBean= (ZixunFanyingDetailBean.ResultsBean) intent.getSerializableExtra(Config.NEWS);
        if(resultsBean.title.equals("null")){ //主题
            tv_title.setText("无");
        }else{
            tv_title.setText(resultsBean.title);
        }

        tv_time.setText(resultsBean.addTime);//时间
        if(resultsBean.accessor.equals("null")){//附件
            tv_fujian.setText("无");
        }else{
            tv_fujian.setText(resultsBean.accessor);
        }
        tv_dept.setText(resultsBean.dept);//部门
        if(resultsBean.content.equals("null")){//内容
            tv_content.setText("无");
        }else{
            tv_content.setText(resultsBean.content);
        }
        if(resultsBean.state=="1"){
            tv_state.setText("正在办理");
        }if(resultsBean.state=="2"){
            tv_state.setText("办理完毕");
        }if(resultsBean.state.equals("null")){
            tv_state.setText("正在办理");
        }

        if(resultsBean.replyContent.equals("null")){
            tv_reply.setText("无");
        }else{
            tv_reply.setText(resultsBean.replyContent);
        }
    }

    @OnClick({ R.id.iv_back,R.id.btn_submit,R.id.rb3,R.id.rb1,R.id.rb2})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rb1:
                advice="1";
                break;
            case R.id.rb2:
                advice="2";
                break;
            case R.id.rb3:
                advice="3";
                break;
            case R.id.btn_submit://保存意见
                post(String.valueOf(intent.getIntExtra(Config.LIST_ID,0)),advice);
                break;
            default:
                break;
        }
    }

    private void post(String id,String pj){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<ResponseBean> call=api.getSaveOpinionsData(id,pj);
        call.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                Log.e("sssss","-----------------------"+response.body().success);
                if(response.body().success=="true"){
                    AlertDialog.Builder builder=new AlertDialog.Builder(ConsultDetailActivity.this);
                    builder.setTitle("保存意见");//设置对话框的标题
                    builder.setMessage("操作成功,请关闭对话框!");//设置对话框的内容
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮
//
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            Toast.makeText(Some_suggestionsActivity.this, "取消",Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    AlertDialog b=builder.create();
                    b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(ConsultDetailActivity.this);
                    builder.setTitle("保存意见");//设置对话框的标题
                    builder.setMessage("抱歉:每个用户只能评价一次,请关闭对话框!");//设置对话框的内容
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog b=builder.create();
                    b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                }
            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                Log.e("sssss",t.getMessage());
                Toast.makeText(ConsultDetailActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
