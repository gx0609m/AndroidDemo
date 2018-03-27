package com.example.administrator.androiddemo.imagecompress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.StackView;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图片压缩
 * ——— 质量压缩；
 * ——— 尺寸压缩；
 * <p>
 * Created by gx on 2018/3/26 0026
 */

public class ImageCompressLearningActivity extends BaseActivity {
    /**
     * 由于图片压缩过程中存在的I/O，计算等耗时操作，所以最好放在子线程中进行
     */

    private static final String TAG = "ImageCompressLearning";

    private ImageView sizeCompressImg;
    private ImageView qualityCompressImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_compress_learning);

        initView();
        // 将经过了尺寸压缩后获取到的bitmap对象展示到屏幕上
        // 原图尺寸是117*117，这里根据ImageView的尺寸将所需的宽高设置为58*58
        sizeCompressImg.setImageBitmap(sampleCompress(58, 58));

        // 将尺寸为708*322大小为252kb的图片经过质量压缩后显示到ImageView中，
        // 并打印出压缩后的图片大小对比下，
        // 注意这里没有和尺寸压缩中一样，没有使用BitmapFactory.decodeResource(**,**,**)含三个参数的方法，略去了BitmapFactory.Options参数
        qualityCompressImg.setImageBitmap(qualityCompress(BitmapFactory.decodeResource(getResources(), R.mipmap.test)));
    }

    /**
     * 质量压缩
     */
    private Bitmap qualityCompress(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 参数format表示压缩后的格式，quality压缩后的图片质量（0表示最低，100表示不压缩），stream表示要将压缩后的图片保存到的输出流。
        // Bitmap.compress(CompressFormat format, int quality, OutputStream stream)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int options = 90;

        // 循环判断 压缩后的图片是否大于100kb，若大于则继续压缩
        while (byteArrayOutputStream.toByteArray().length / 1024 > 100) {
            byteArrayOutputStream.reset(); // 重置byteArrayOutputStream，即清空byteArrayOutputStream
            if (options > 0) // 压缩质量参数在0~100，所以这里增加了判断
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
            options -= 10; // 每次减少10

            // 打印压缩后所占内存的大小
            Log.d(TAG, byteArrayOutputStream.toByteArray().length / 1024 + "");
        }

        // 将压缩后的数据输出流byteArrayOutputStream 通过字节数组的形式 转换存储到字节数组输入流byteArrayInputStream中
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        // 将字节数组输入流byteArrayInputStream数据转换成Bitmap格式
        Bitmap bitmapCompressed = BitmapFactory.decodeStream(byteArrayInputStream);

        // 从外到内顺序关闭流并释放
        try {
            byteArrayInputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmapCompressed;
    }

    /**
     * 尺寸压缩 ——— 通过采样率压缩（BitmapFactory.Options设置inSampleSize的值进行压缩）
     *
     * @param reqWidth  要求压缩后的图片宽度
     * @param reqHeight 要求压缩后的图片高度
     */
    private Bitmap sampleCompress(int reqWidth, int reqHeight) {
        /*
         * 获取原图属性
         */
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true; // 设置为true，不将图片解码到内存中
        BitmapFactory.decodeResource(getResources(), R.mipmap.retry, opts);
        int imageHeight = opts.outHeight; // 获取原图高度
        int imageWidth = opts.outWidth; // 获取原图宽度
        String imageType = opts.outMimeType; // 获取原图类型

        /*
         * 根据原图属性和所需的图片大小，计算采样率inSampleSize
         */
        int inSampleSizeValue = 1;
        if (imageHeight > reqHeight || imageWidth > reqWidth) {
            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;
            // 计算inSampleSize值
            while ((halfHeight / inSampleSizeValue) >= reqHeight
                    && (halfWidth / inSampleSizeValue) >= reqWidth) {
                inSampleSizeValue *= 2; // 在加载图片过程中，解析器使用的inSampleSize都是2的指数倍
            }
        }

        /*
         * 设置options中的inSampleSize为我们刚刚计算出的值
         * 并根据图片的不同位置，选择BitmapFactory的不同解码方法，获取Bitmap对象
         */
        opts.inSampleSize = inSampleSizeValue; // 设置options中的inSampleSize值
        // 加载压缩版图片
        opts.inJustDecodeBounds = false;
        // 根据图片的不同位置，选择BitmapFactory的不同解码方法
        return BitmapFactory.decodeResource(getResources(), R.mipmap.retry, opts);
    }

    private void initView() {
        sizeCompressImg = (ImageView) findViewById(R.id.sizeCompress);
        qualityCompressImg = (ImageView) findViewById(R.id.qualityCompress);
    }
}
