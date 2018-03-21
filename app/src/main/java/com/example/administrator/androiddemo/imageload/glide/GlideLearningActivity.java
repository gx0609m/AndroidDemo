package com.example.administrator.androiddemo.imageload.glide;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.androiddemo.R;

/**
 * Created by gx on 2018/3/16 0016
 */

public class GlideLearningActivity extends AppCompatActivity {

    /*
     * Glide使用：
     *      1.在app的build.gradle文件中加入如下依赖：
     *          compile 'com.github.bumptech.glide:glide:3.7.0'
     *
     *      2.Glide.with(context) // 上下文
     *             .load(Url) // 被加载图像的Url地址
     *             .into(targetImageView); // 图片最终要展示的地方
     */

    private ImageView simpleShowImg; // 使用Glide简单展示图片
    private ImageView showImgWithTarget; // 使用target展示图片（实现Glide的回调与监听）
    private ImageView showImgWithListener; // 通过添加listener实现回调监听

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_learning);

        initView();

        // 使用Glide简单展示图片
        showImgSimple();
        // 使用target展示图片（实现Glide的回调与监听）
        showImgWithTarget();
        // 通过添加listener实现回调监听
        showImgWithListener();
    }

    /**
     * 使用Glide简单展示图片
     */
    private void showImgSimple() {
        Glide.with(this)
                .load("http://img.zcool.cn/community/0100785859056fa8012060c8c2bbc0.JPG@1280w_1l_2o_100sh.jpg")
                .into(simpleShowImg);
    }

    /*
     * 使用target展示图片（实现Glide的回调与监听）
     *
     * 使用了这么久的Glide，我们都知道into()方法中是可以传入ImageView的,
     * 那么into()方法还可以传入别的参数吗？
     * 我可以让Glide加载出来的图片不显示到ImageView上吗？
     *
     * 答案是肯定的，这就需要用到自定义Target功能：
     *      into()方法还有一个接收Target参数的重载，
     *      其实，即使我们传入的参数是ImageView，Glide也会在内部自动构建一个Target对象，而如果我们能够掌握
     *      自定义Target技术的话，就可以更加随心所欲地控制Glide的回调了；
     *
     * 如果我们要进行自定义的话，通常只需要在两种Target的基础上去自定义：
     *      1.SimpleTarget；
     *      2.ViewTarget；
     *
     * 通过target这种方式展示图片，我们可以：
     *      1.实现glide加载图片的回调与监听；
     *      2.虽然效果和直接在into()方法中传入ImageView并没有什么区别，但是在回调方法中，我们能够拿到了图片对象的实例，这意味着我们可以做更多的事情了；
     */
    private void showImgWithTarget() {
        Glide.with(this)
                .load("http://img.zcool.cn/community/0100785859056fa8012060c8c2bbc0.JPG@1280w_1l_2o_100sh.jpg")
                .into(simpleTarget); // into()方法中不在传入imageView实例，而是传入simpleTarget实例
    }

    /*
     * SimpleTarget：
     *      顾名思义，它是一种极为简单的Target，我们使用它可以将Glide加载出来的图片对象获取到，
     *      而不是像之前那样只能将图片在ImageView上显示出来；
     *
     *      这里我们创建了一个SimpleTarget的实例，并且指定它的泛型是GlideDrawable，然后重写了onResourceReady()方法，其他的onLoadCleared()、onLoadFailed()方法可自行重写
     *      在onResourceReady()方法中，我们就可以获取到Glide加载出来的图片对象了，
     *      也就是方法参数中传过来的GlideDrawable对象,有了这个对象之后你可以使用它进行任意的逻辑操作;
     *
     *      当然，SimpleTarget中的泛型并不一定只能是GlideDrawable，如果你能确定你正在加载的是一张静态图
     *      而不是GIF图的话，我们还能直接指定泛型位Bitmap，拿到这张图的Bitmap对象；
     *
     * （关于ViewTarget和自定义Target可参见：http://blog.csdn.net/guolin_blog/article/details/70215985）
     */
    SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            /*
             * 获取到了传过来的GlideDrawable泛型对象，这里只是简单地把它显示到了ImageView上
             */
            showImgWithTarget.setImageDrawable(resource);
        }
    };

    /*
     * 通过添加listener来监听Glide加载图片的状态
     *
     * 与target实现监听不同的是，listener()是结合into()方法一起使用的，不需要替换into()方法
     *
     * 这里我们在into()方法之前串接了一个listener()方法，然后实现了一个RequestListener的实例,其中RequestListener需要实现两个方法:
     *      1.onResourceReady()方法；
     *      2.onException()方法；
     *
     * 注意：
     *      listener()方法实现起来虽然简单，不过还有一点需要处理：
     *          onResourceReady()方法和onException()方法都有一个布尔值的返回值，
     *              返回false就表示这个事件没有被处理，还会继续向下传递；
     *              返回true就表示这个事件已经被处理掉了，从而不会再继续向下传递；
     *          举个简单点的例子，如果我们在RequestListener的onResourceReady()方法中返回了true，那么就不会再回调Target的onResourceReady()方法了；
     */
    private void showImgWithListener() {
        Glide.with(this)
                .load("http://img.zcool.cn/community/0100785859056fa8012060c8c2bbc0.JPG@1280w_1l_2o_100sh.jpg")
                .listener(new RequestListener<String, GlideDrawable>() {
                    /*
                     * 当图片加载失败的时候就会回调
                     */
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        return false;
                    }
                    /*
                     * 当图片加载完成的时候回调
                     */
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(showImgWithListener);
    }

    private void initView() {
        simpleShowImg = (ImageView) findViewById(R.id.simpleShowImg);
        showImgWithTarget = (ImageView) findViewById(R.id.showImgWithTarget);
        showImgWithListener = (ImageView) findViewById(R.id.showImgWithListener);
    }
}
