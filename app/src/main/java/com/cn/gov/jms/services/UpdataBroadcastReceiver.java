package com.cn.gov.jms.services;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.cn.gov.jms.utils.SharePreferenceTools;

import java.io.File;

/**
 * Created by wangjiawei on 2018-1-18.
 */

public class UpdataBroadcastReceiver extends BroadcastReceiver {

    @SuppressLint("NewApi")
    public void onReceive(Context context, Intent intent) {

        long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        SharePreferenceTools sp=new SharePreferenceTools(context);
        long refernece = sp.getLong("refernece", 0);
        if (refernece != myDwonloadID) {
            return;
        }

        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(myDwonloadID);
        installAPK(context,downloadFileUri);

    }


//    解决Android7.0 更新安装包时不能自动安装问题
//96  和平菌 关注
//2017.07.07 17:44* 字数 410 阅读 2410评论 1喜欢 3
//    项目中发现在自动更新功能的时候，下载好了apk的文件后在android7.0系统中不能自动跳到安装界面，后来搜索了一番解决了问题，但感觉没有描述清楚，所以补充一下。
//
//    每个Android版本的发布，对于安全性问题的要求越来越高，也为Android程序员增加了额外的工作量。Android6.0引入动态权限控制(Runtime Permissions)，Android7.0引入私有目录被限制访问和StrictMode API 。私有目录被限制访问是指在Android7.0中为了提高应用的安全性，在7.0上应用私有目录将被限制访问，这与iOS的沙盒机制类似。StrictMode API是指禁止向你的应用外公开 file:// URI。 如果一项包含文件 file:// URI类型 的 Intent 离开你的应用，则会报出异常。
//
//    也就是说不能访问你应用私有的文件夹了
//
//    那么怎么解决呢，GOGOOLE自然提供了方法，那就是允许共享你私有目录下的一个文件夹，共享出去让大家访问，这样就可以访问你下载的apk来安装了
//
//    具体步骤是：
//
//            1.manifest进行注册：加在节点下
//
//            <provider
//    android:name="android.support.v4.content.FileProvider"
//    android:authorities="你的包名.fileprovider"
//    android:grantUriPermissions="true"
//    android:exported="false">
//            <meta-data
//    android:name="android.support.FILE_PROVIDER_PATHS"
//    android:resource="@xml/provider_paths" />
//        </provider>
//    注意这一个配置 @xml/provider_paths
//
//2.xml文件编写：
//    在res文件下新建xml文件夹，编写file_paths:
//
//    <?xml version="1.0" encoding="utf-8"?>
//<resources>
//    <paths>
//        <external-path path="" name="download"/>
//    </paths>
//</resources>
//    这个要说明一下
//
//            <files-path/>代表的根目录： [Context.getFilesDir()](https://developer.android.com/reference/android/content/Context.html?hl=zh-tw#getFilesDir())
//
//<external-path/>代表的根目录: [Environment.getExternalStorageDirectory()](https://developer.android.com/reference/android/os/Environment.html?hl=zh-tw#getExternalStorageDirectory())
//
//<cache-path/>代表的根目录: [getCacheDir()](https://developer.android.com/reference/android/content/Context.html?hl=zh-tw#getCacheDir())
//    这样就把这个目录给共享出去了
//
//3.安装的时候：
//
//            if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
//        Uri apkUri = FileProvider.getUriForFile(this, "你的包名.fileprovider", apkFile);//在AndroidManifest中的android:authorities值
//        Intent install = new Intent(Intent.ACTION_VIEW);
//        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        startActivity(install);
//    } else{
//        Intent install = new Intent(Intent.ACTION_VIEW);
//        install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(install);
//    }
//    好了 现在就可以了

    private void installAPK(Context context,Uri apk ) {
        if (Build.VERSION.SDK_INT < 23) {
            Intent intents = new Intent();
            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
            intents.setType("application/vnd.android.package-archive");
            intents.setData(apk);
            intents.setDataAndType(apk, "application/vnd.android.package-archive");
            intents.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intents);
        } else {
            File file = queryDownloadedApk(context);
            if (file.exists()) {
                openFile(file, context);
            }

        }
    }

    /**
     * 通过downLoadId查询下载的apk，解决6.0以后安装的问题
     * @param context
     * @return
     */
    public static File queryDownloadedApk(Context context) {
        File targetApkFile = null;
        DownloadManager downloader = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        SharePreferenceTools sp=new SharePreferenceTools(context);
        long downloadId = sp.getLong("refernece", -1);
        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloader.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;

    }

    private void openFile(File file, Context context) {  //解决7.0 打开内部文件
//        Intent intent = new Intent();
//        intent.addFlags(268435456);
//        intent.setAction("android.intent.action.VIEW");
//        String type = getMIMEType(file);
//        intent.setDataAndType(Uri.fromFile(file), type);

        Uri apkUri = FileProvider.getUriForFile(context, "com.cn.gov.jms.MyApplication.fileprovider", file);//在AndroidManifest中的android:authorities值
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
        try {
            context.startActivity(install);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(context, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }

    }

    private String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }

}
