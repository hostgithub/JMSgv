package com.cn.gov.jms.presenter;


import com.cn.gov.jms.base.BasePresenter;
import com.cn.gov.jms.model.ApplicationEntity;

/**
 * Created by wangjiawei on 2017/7/18.
 */

public interface MainContract {
    interface View extends BaseView {
        void retureResult(String result);
        void retureUpdateResult(ApplicationEntity entity);
    }

    interface Presenter extends BasePresenter {
        void checkUpdate(String url);
        void update(ApplicationEntity entity);
    }
}
