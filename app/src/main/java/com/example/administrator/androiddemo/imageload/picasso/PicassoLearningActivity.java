package com.example.administrator.androiddemo.imageload.picasso;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.administrator.androiddemo.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by gx on 2018/3/16 0016
 */

public class PicassoLearningActivity extends AppCompatActivity {

    /**
     * Picasso使用：
     * 在app的build.gradle中添加依赖:
     * compile 'com.squareup.picasso:picasso:2.5.2'
     * 如果你开启了混淆,你需要将以下代码添加到混淆规则文件中:
     * -dontwarn com.squareup.okhttp.**
     *
     * 如果图片地址不存在或为空怎么处理呢：
     *      如果不去处理,网络可能会一直请求或者我们的屏幕上会出现一片空白,这都不是我们希望看到的；
     * Picasso给了我们如下解决方案：
     *      1.在判断为空的地址时,取消网络请求,调用cancelRequest(),然后调用imageView.setImageDrawable(null)；
     *      2.或者调用Picasso的.placeHolder()方法进行图片的替换展示，
     *        如果图片网址错误，我们也可以调用.error()方法进行图片替换；
     *                      if (TextUtils.isEmpty(imageUrl)){  // 图片地址为空
     *                          Picasso
     *                              .with(context)
     *                              .cancelRequest(imageView);
     *                          imageView.setImageDrawable(null);
     *                      }else {                            // 图片替换
     *                           //加载图片
     *                           Picasso
     *                              .with(context)
     *                              .load(imageUrl)
     *                              .placeholder(R.mipmap.ic_launcher)
     *                              .error(R.mipmap.ic_launcher)
     *                              .into((ImageView) convertView);
     *                      }
     *       注意:.placeholder()与.error()所传的参数与.load()相同；
     */

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private ImageView imgFromNetwork; // 从网络加载显示图片
    private ImageView imgFromSD; // 从File文件中加载
    private ImageView imgFromRes; // 从Android Resources中加载
    private ImageView imgFromURI; // 从uri中加载

    private String url = "http://f2.topitme.com/2/b9/71/112660598401871b92l.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_learning);

        initView();
        // 从网络加载显示图片
        loadImgFromNetword();
        // 从File文件中加载
        loadImgFromSD();
        // 从Android Resources中加载
        loadImgFromRes();
        // 从uri中加载
        loadImgFromURI();
    }

    /**
     * 从网络加载显示图片
     */
    private void loadImgFromNetword() {
        Picasso.with(this)
                .load(url)
                .into(imgFromNetwork);
    }

    /**
     * 从File中加载
     * <p>
     * 这个file并不一定非得是在你的设备中,可以是任意的路径,只要是File路径即可
     */
    private void loadImgFromSD() { // /storage/emulated/0/Pictures/Running.jpg
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Running.jpg");
        Picasso.with(this)
                .load(file)
                .into(imgFromSD);
    }

    /**
     * 从Android Resources中加载
     */
    private void loadImgFromRes() {
        Picasso.with(this)
                .load(R.mipmap.retry)
                .into(imgFromRes);
    }

    /**
     * 从uri中加载
     *
     * 为了示范,只能用资源文件转换为URI,并不仅仅是这种方式, 它可以支持任意的URI地址
     */
    private void loadImgFromURI() {
        Uri uri = Uri.parse(ANDROID_RESOURCE + this.getPackageName() + FOREWARD_SLASH + R.mipmap.progress);
        Picasso.with(this)
                .load(uri)
                .into(imgFromURI);
    }

    private void initView() {
        imgFromNetwork = (ImageView) findViewById(R.id.imgFromNetwork);
        imgFromSD = (ImageView) findViewById(R.id.imgFromSD);
        imgFromRes = (ImageView) findViewById(R.id.imgFromRes);
        imgFromURI = (ImageView) findViewById(R.id.imgFromURI);
    }

}
