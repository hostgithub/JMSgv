package com.cn.gov.jms.presenter;


import com.cn.gov.jms.App;
import com.cn.gov.jms.model.ApplicationEntity;
import com.cn.gov.jms.utils.DownloadUtil;
import com.cn.gov.jms.utils.JsonUtils;
import com.cn.gov.jms.utils.LogUtil;
import com.cn.gov.jms.utils.OkHttpUtils;

/**
 * Created by wangjiawei on 2017/7/18.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void checkUpdate(String url) {
        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtil.d("应用更新:" + response);
                ApplicationEntity entity = JsonUtils.deserialize(response, ApplicationEntity.class);
                mView.retureUpdateResult(entity);
            }

            @Override
            public void onFailure(Exception e) {
                mView.retureResult(e.getMessage());
            }
        });
    }

    @Override
    public void update(ApplicationEntity entity) {
       // DownloadUtil.downloadApk(App.application, entity.getInstall_url(), entity.getName(), entity.getChangelog(), "app-release-jms.gov.apk");
        DownloadUtil.downLoadAPK(App.application, entity.getInstall_url());
    }
}
