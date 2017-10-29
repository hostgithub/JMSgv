package com.cn.gov.jms.ui;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.OnClick;

public class JsoupShujiActivity extends BaseActivity {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jsoup_shuji;
    }

    @Override
    protected void initView() {

        tv_title.setVisibility(View.GONE);
        image.setVisibility(View.GONE);

        WebSettings webSettings = webView .getSettings();
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片  用于加载网络 本地不适用
        webView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8

        tpl = getFromAssets("shuji.html");
        webView.loadDataWithBaseURL(null, tpl, "text/html", "utf-8", null);
        //getData();
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
                    Document doc = Jsoup.connect(Config.BANNER_BASE_URL+"html/index/alone/000100010002000100010029.html").get();

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
                            Glide.with(JsoupShujiActivity.this)
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
}
