package com.cn.gov.jms.ui;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.SqgkDetail;
import com.cn.gov.jms.services.Api;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShuJiDetailActivity extends BaseActivity
{
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.action_bar_title)
    TextView action_bar_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leader_info_detail;
    }

    @Override
    protected void initView() {

//        LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT);
        //mDetailWebView.setLayoutParams(mWebViewLP);
        //mDetailWebView.setInitialScale(100);
        WebSettings webSettings = webView .getSettings();
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片  用于加载网络 本地不适用
        getData();
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }

    private void getData(){        //json格式有问题 解析失败
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<SqgkDetail> call=api.getMayorSecretaryData("徐建国");
        call.enqueue(new Callback<SqgkDetail>() {
            @Override
            public void onResponse(Call<SqgkDetail> call, Response<SqgkDetail> response) {
                if(response!=null){
                    SqgkDetail detail=response.body();
                    SqgkDetail.ResultsBean resultsBean=detail.getResults().get(0);
                    webView.loadDataWithBaseURL(Config.BANNER_BASE_URL, resultsBean.content, "text/html", "utf-8", null);
                    action_bar_title.setText(Html.fromHtml(resultsBean.title));
                    Log.e("xxxxxxx",resultsBean.title);
                }else{
                    Toast.makeText(ShuJiDetailActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SqgkDetail> call, Throwable t) {
                Toast.makeText(ShuJiDetailActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }
}
