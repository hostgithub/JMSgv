package com.cn.gov.jms.collectmokuan;

import android.content.Context;

import com.cn.gov.jms.base.BasePresenter;
import com.cn.gov.jms.base.BaseView;
import com.cn.gov.jms.entity.JcodeEntity;

import java.util.List;

/**
 * Created by llf on 2017/3/28.
 */

public interface GirlContract {
    interface View extends BaseView {
        void returnData(List<JcodeEntity> datas);
    }

    interface Presenter extends BasePresenter {
        void loadData(String url);

        void addRecord(Context context, JcodeEntity entity);
    }
}
