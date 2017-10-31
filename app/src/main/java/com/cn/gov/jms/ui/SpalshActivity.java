package com.cn.gov.jms.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SpalshActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks
{

    @BindView(R.id.loading_imageCenter)
    ImageView mLoadingImageCenter;
    public static final int PERMISSION = 100;

//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_loading);
//        ButterKnife.bind(this);
//
//        initViews();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        /**
         * 6.0系统动态权限申请需要
         */
        String[] params = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(SpalshActivity.this, params)) {
            skip();
        } else {
            EasyPermissions.requestPermissions(SpalshActivity.this, "应用需要权限才能安全运行", PERMISSION, params);
        }
    }

    private void initViews()
    {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 1.0f);
        animation.setDuration(2000);
        mLoadingImageCenter.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                Intent it = new Intent(SpalshActivity.this, MainActivity.class);
                startActivity(it);
                SpalshActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        animation.start();
        /**
         * 6.0系统动态权限申请需要
         */
        String[] params = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(SpalshActivity.this, params)) {
            skip();
        } else {
            EasyPermissions.requestPermissions(SpalshActivity.this, "应用需要权限才能安全运行", PERMISSION, params);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        skip();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode) {
            case PERMISSION:
                //引导用户跳转到设置界面
                new AppSettingsDialog.Builder(SpalshActivity.this, "希望您通过权限")
                        .setTitle("权限设置")
                        .setPositiveButton("设置")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setRequestCode(PERMISSION)
                        .build()
                        .show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void skip() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
                LogUtil.d("最高可用内存:" + maxMemory);
                startThenKill(MainActivity.class);//-------------------------------------------------------------------
                SpalshActivity.this.overridePendingTransition(R.anim.scale_in, R.anim.shrink_out);
            }
        }, 1000 * 2);
    }
}
