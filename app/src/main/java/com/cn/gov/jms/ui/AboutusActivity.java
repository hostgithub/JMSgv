package com.cn.gov.jms.ui;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.cn.gov.jms.base.BaseActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutusActivity extends BaseActivity
{

    @BindView(R.id.iv_back)
    ImageView imageView;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void initView() {
        processWebString();
    }

    private void processWebString() {
        // 加载 asset 文件
        //String tpl = getFromAssets("contactus.html");

        WebSettings webSettings = webView .getSettings();
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片  用于加载网络 本地不适用

        //加载本地HTML中要显示图片 只能将src字段引用本地的图片目录

        String tpl = getFromAssets("aboutus.html");
        webView.loadDataWithBaseURL(null, tpl, "text/html", "utf-8", null);
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

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
