package com.example.administrator.androiddemo.imageload.fresco;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.androiddemo.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by gx on 2018/3/15 0015
 */

public class FrescoLearningActivity extends AppCompatActivity {

    /**
     * 使用Fresco（https://www.fresco-cn.org/）：
     * 在app的build.gradle中添加依赖:
     * dependencies {
     * // 其他依赖
     * compile 'com.facebook.fresco:fresco:0.12.0'
     * }
     * 根据需求添加以下依赖：
     * dependencies {
     * // 在 API < 14 上的机器支持 WebP 时，需要添加
     * compile 'com.facebook.fresco:animated-base-support:0.12.0'
     * // 支持 GIF 动图，需要添加
     * compile 'com.facebook.fresco:animated-gif:0.12.0'
     * // 支持 WebP （静态图+动图），需要添加
     * compile 'com.facebook.fresco:animated-webp:0.12.0'
     * compile 'com.facebook.fresco:webpsupport:0.12.0'
     * // 仅支持 WebP 静态图，需要添加
     * compile 'com.facebook.fresco:webpsupport:0.12.0'
     * }
     * 在加载图片之前，须初始化Fresco类：
     * 只需要调用Fresco.initialize一次即可完成初始化，
     * 在 Application 里面做这件事再适合不过了，注意多次的调用初始化是无意义的
     * <p>
     * （还可以：
     * 配置Image Pipeline ——— ImageLoaderConfig，如果配置了此，需要在初始化Fresco时通过Fresco的构造方法设置下;
     * 配置内存缓存 ——— BitmapMemoryCacheParamsSupplier，在配置Image Pipeline时配置；）
     */

    // 加载网络图片前显示一张占位图 -- SimpleDraweeView -- placeholderImage
    private SimpleDraweeView animateImage; // 带动画的显示（从半透明到不透明）
    private SimpleDraweeView overLayImage; // 图层叠加显示
    private SimpleDraweeView otherProperties; // 其它的属性的配置，比如加载进度、加载失败、重试图

    private SimpleDraweeView simpleDraweeView; // 简单加载图片
    private SimpleDraweeView circleImage; // 加载圆形图片
    private SimpleDraweeView circleWithBorderImage; // 加载圆形带边框的图片
    private SimpleDraweeView filletImage; // 加载圆角图片
    private SimpleDraweeView bottomFilletImage; // 加载底部是圆角的图片
    private SimpleDraweeView leftTopAndRightBottomFilletImage; // 加载左上和右下是圆角的图片

    private SimpleDraweeView simpleDraweeViewWithController; // 使用DraweeController加载图片（可以添加listener监听加载过程）

    Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_learning);

        uri = Uri.parse("http://f2.topitme.com/2/b9/71/112660598401871b92l.jpg");

        initView();
        // 带动画的显示（从半透明到不透明）
        loadWithAnimateImage();
        // 图层叠加显示
        loadOverLayImage();
        // 其它的属性的配置，比如加载进度、加载失败、重试图
        loadWithOtherPropertiesImage();
        // 简单加载图片
        simpleLoadImage();
        // 加载圆角图片
        loadCircleImage();
        // 加载圆角带边框的图片
        loadCircleWithBorderImage();
        // 加载圆角图片
        loadFilletImage();
        // 加载底部是圆角的图片
        loadBottomFilletImage();
        // 加载左上和右下是圆角的图片
        loadLeftTopAndRightBottomFilletImage();

        // 使用DraweeController加载图片（可以添加listener监听加载过程）
        loadImageWithController();
    }

    /**
     * 使用Fresco简单的加载网络图片
     * <p>
     * 剩下的，Fresco会替你完成:
     * 显示占位图直到加载完成；
     * 下载图片；
     * 缓存图片；
     * 图片不再显示时，从内存中移除；
     * 等等等等。
     */
    private void simpleLoadImage() {
        /**
         * 注意不要忘记添加网络权限
         */
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 使用DraweeController加载图片（可以添加listener监听加载过程）
     * <p>
     * Fresco不仅仅提供了
     * simpleDraweeView.setImageURI(uri)  方法来显示图片;
     * 它还提供了
     * simpleDraweeView.setController（controller） 方法加载图片;
     */
    private void loadImageWithController() {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(controllerListener)  //如果想监听加载的过程，就加一个ControllerListener
//                .setLowResImageRequest();  // 当我们要从服务器端下载一张高清图片，图片比较大，
//                .setImageRequest();  // 下载很慢的情况下有些服务器会提供一张缩略图，同样的Fresco也支持这种方法
                .build();
        simpleDraweeViewWithController.setController(controller);
    }

    ControllerListener controllerListener = new BaseControllerListener() {
        /**
         * 图片加载成功时调用
         */
        @Override
        public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
//            Toast.makeText(FrescoLearningActivity.this, "simpleDraweeViewWithController成功展示了图片！", Toast.LENGTH_LONG).show();
        }

        /**
         * 图片加载失败时调用
         */
        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
        }

        /**
         * 如果图片设置渐进式，onIntermediateImageFailed会被回调
         */
        @Override
        public void onIntermediateImageFailed(String id, Throwable throwable) {
            super.onIntermediateImageFailed(id, throwable);
            Toast.makeText(FrescoLearningActivity.this, "Fresco配置了渐进式加载！", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 显示圆形图片
     */
    private void loadCircleImage() {
        circleImage.setImageURI(uri);
    }

    /**
     * 显示圆形带边框图片
     */
    private void loadCircleWithBorderImage() {
        circleWithBorderImage.setImageURI(uri);
    }

    /**
     * 加载圆角图片
     */
    private void loadFilletImage() {
        filletImage.setImageURI(uri);
    }

    /**
     * 加载底部是圆角的图片
     */
    private void loadBottomFilletImage() {
        bottomFilletImage.setImageURI(uri);
    }

    /**
     * 加载左上和右下是圆角的图片
     */
    private void loadLeftTopAndRightBottomFilletImage() {
        leftTopAndRightBottomFilletImage.setImageURI(uri);
    }

    /**
     * 带动画的显示（从半透明到不透明）
     */
    private void loadWithAnimateImage() {
        animateImage.setImageURI(uri);
    }

    /**
     * 图层叠加显示
     */
    private void loadOverLayImage() {
        overLayImage.setImageURI(uri);
    }

    /**
     * 其它的属性的配置，比如加载进度、加载失败、重试图
     */
    private void loadWithOtherPropertiesImage() {
        otherProperties.setImageURI(uri);
    }

    private void initView() {
        animateImage = (SimpleDraweeView) findViewById(R.id.animateImage);
        overLayImage = (SimpleDraweeView) findViewById(R.id.overLayImage);
        otherProperties = (SimpleDraweeView) findViewById(R.id.otherProperties);

        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);
        circleImage = (SimpleDraweeView) findViewById(R.id.circleImage);
        circleWithBorderImage = (SimpleDraweeView) findViewById(R.id.circleWithBorderImage);
        filletImage = (SimpleDraweeView) findViewById(R.id.filletImage);
        bottomFilletImage = (SimpleDraweeView) findViewById(R.id.bottomFilletImage);
        leftTopAndRightBottomFilletImage = (SimpleDraweeView) findViewById(R.id.leftTopAndRightBottomFilletImage);
        simpleDraweeViewWithController = (SimpleDraweeView) findViewById(R.id.simpleDraweeViewWithController);

    }
}
