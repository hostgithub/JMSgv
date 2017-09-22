package com.cn.gov.jms.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.SqgkDetail;
import com.cn.gov.jms.services.Api;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactusActivity extends BaseActivity
{

    @BindView(R.id.iv_back)
    ImageView imageView;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contactus;
    }

    @Override
    protected void initView() {
        //processWebString();
        Intent intent=getIntent();
        getData(Integer.parseInt(intent.getStringExtra(Config.NEWS)));
    }


    private void processWebString() {
        // 加载 asset 文件
        //String tpl = getFromAssets("contactus.html");
        //webView.loadDataWithBaseURL(null, tpl, "text/html", "utf-8", null);
    }


    /*
     * 获取html文件
     */
    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private void getData(int id){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<SqgkDetail> call=api.getSqgkDetailData(id);
        call.enqueue(new Callback<SqgkDetail>() {
            @Override
            public void onResponse(Call<SqgkDetail> call, Response<SqgkDetail> response) {
                if(response!=null){
                    SqgkDetail detail=response.body();
                    SqgkDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    webView.loadDataWithBaseURL(null, resultsBean.content, "text/html", "utf-8", null);
                    Log.e("xxxxxxx",resultsBean.content);
                }else{
                    Toast.makeText(ContactusActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SqgkDetail> call, Throwable t) {
                Toast.makeText(ContactusActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
