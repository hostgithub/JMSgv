package com.cn.gov.jms.data.remote;

import android.support.annotation.NonNull;

import com.cn.gov.jms.data.JcodeDataSource;
import com.cn.gov.jms.entity.JcodeEntity;


/**
 * Created by llf on 2017/6/14.
 */

public class JcodeRemoteDataSource implements JcodeDataSource {
    private static JcodeRemoteDataSource INSTANCE;

    private JcodeRemoteDataSource() {}

    public static JcodeRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JcodeRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public long saveJcode(@NonNull JcodeEntity entity) {
        return 0;
    }

    @Override
    public void deleteAllJcodes() {

    }

    @Override
    public int deleteJcode(@NonNull String title) {
        return 0;
    }

    @Override
    public void getJcodes(@NonNull LoadJcodesCallback callback) {

    }

    @Override
    public void getJcode(@NonNull String title, @NonNull GetJcodeCallback callback) {

    }

    @Override
    public void refreshJcodes(@NonNull JcodeEntity entity) {

    }
}
