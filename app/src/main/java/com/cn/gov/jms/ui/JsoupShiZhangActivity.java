package com.cn.gov.jms.ui;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.AboutAndContact;
import com.cn.gov.jms.model.SqgkDetail;
import com.cn.gov.jms.services.Api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsoupShiZhangActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.action_bar_title)
    TextView action_bar_title;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.image)
    ImageView image;

    private String titleName;
    private String picUrl;
    private String contentText;
    private String tpl;

    private List<AboutAndContact.ResultsBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jsoup_shi_zhang;
    }

    @Override
    protected void initView() {
        image.setVisibility(View.GONE);

        WebSettings webSettings = webView .getSettings();
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片  用于加载网络 本地不适用
        webView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8

        tpl = getFromAssets("shizhang_sgq.html");
        //webView.loadDataWithBaseURL(null, tpl, "text/html", "utf-8", null);
        //getData();

        initBannerData("0001000200010003");
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }

    public void getData() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Document doc = Jsoup.connect(Config.BANNER_BASE_URL+"html/index/alone/000100010002000100030018.html").get();

                    Elements elements = doc.select("div.detail");

                    Log.e("title:", elements.select("div.title").text());
                    titleName=elements.select("div.title").text();
                    Log.e("src:", elements.select("img").attr("src"));
                    picUrl=elements.select("img").attr("src");
                    Elements content = elements.select("p.MsoNormal");
                    Log.e("content:", content.text());
                    contentText=content.text();


                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            tv_title.setText(titleName);
                            Glide.with(JsoupShiZhangActivity.this)
                                    .load(picUrl)
                                    .into(image);
                            webView.loadDataWithBaseURL(null, tpl, "text/html", "utf-8", null);
                        }

                    });
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }).start();
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


    /**
     *初始化 Banner数据
     */
    private void initBannerData(String id) {
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AboutAndContact> call=api.getLeaderOfWindowListData(id);
        call.enqueue(new Callback<AboutAndContact>() {
            @Override
            public void onResponse(Call<AboutAndContact> call, Response<AboutAndContact> response) {
                if(response!=null){
                    AboutAndContact banners=response.body();
                    list=banners.getResults();
                    //sqgkAdapter = new SQGKAdapter(ShiQingGaiKuangActivity.this, resultsBeanList);

                    getData(Integer.parseInt(list.get(0).id));//永远取市政府领导的第一个

                }else{
                    Toast.makeText(JsoupShiZhangActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AboutAndContact> call, Throwable t) {
                Toast.makeText(JsoupShiZhangActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
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
//                    Intent intent = new Intent(JsoupShiZhangActivity.this,LeaderInfoDetailActivity.class);
//                    intent.putExtra(Config.NEWS,resultsBean);
//                    startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.content);
                    webView.loadDataWithBaseURL(Config.BANNER_BASE_URL, resultsBean.content, "text/html", "utf-8", null);
                    action_bar_title.setText(Html.fromHtml(resultsBean.title));
                    tv_title.setText(resultsBean.title+"同志简历");
                }else{
                    Toast.makeText(JsoupShiZhangActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SqgkDetail> call, Throwable t) {
                Toast.makeText(JsoupShiZhangActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }
}
