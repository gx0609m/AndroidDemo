package com.example.administrator.androiddemo.imageload.fresco;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 设置Fresco中的图片加载配置
 * 在Fresco中负责图片加载的主要是Image Pipeline
 * 因此，本类主要是配置Image Pipeline
 * <p>
 * 在Fresco初始化的时候，用Fresco的构造函数将我们配置的Image Pipeline设置进去
 * Fresco.initialize(context,ImageLoaderConfig.getImagePipelineConfig(context));
 * <p>
 * Created by gx on 2018/3/19 0019
 */

public class ImageLoaderConfig {

    /**
     * ImagePipeline配置:
     *      1.磁盘缓存目录 ——— 推荐缓存到应用本身的缓存文件夹，这么做的好处是：
     *              a.当应用被用户卸载后能自动清除缓存，增加用户好感（可能以后用得着时，还会想起我）；
     *              b.一些内存清理软件可以扫描出来，进行内存的清理；
     *              File fileCacheDir = context.getApplicationContext().getCacheDir();
     *
     *      2.配置磁盘缓存 ——— 大部分的应用有一个磁盘缓存就够了，但是在一些情况下，你可能需要两个缓存：
     *              比如你想把小文件放在一个缓存中（50*50及以下尺寸），大文件放在另外一个文件中，这样小文件就不会因大文件的频繁变动而被从缓存中移除；
     *
     *              大文件缓存：
     *              DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(context)
     *                                      .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
     *                                      .setBaseDirectoryPath(fileCacheDir)
     *                                      .build();
     *              小文件缓存
     *              DiskCacheConfig smallDiskCacheConfig = DiskCacheConfig.newBuilder(context)
     *                                      .setBaseDirectoryPath(fileCacheDir)
     *                                      .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)
     *                                      .setMaxCacheSize(MAX_DISK_SMALL_CACHE_SIZE)
     *                                      .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_SMALL_ONLOWDISKSPACE_CACHE_SIZE)
     *                                      .build();
     *
     */

    // 大文件缓存位置
    private static final String IMAGE_PIPELINE_CACHE_DIR = "image_cache";
    // 小文件缓存位置
    private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "image_small_cache";
    // 小文件缓存中 默认的最大缓存
    private static final int MAX_DISK_SMALL_CACHE_SIZE = 10 * ByteConstants.MB;
    // 设备磁盘空间不足时的 最大缓存大小
    private static final int MAX_DISK_SMALL_ONLOWDISKSPACE_CACHE_SIZE = 5 * ByteConstants.MB;

    private static ImagePipelineConfig sImagePipelineConfig;

    /**
     * Creates config using android http stack as network backend.
     */
    public static ImagePipelineConfig getImagePipelineConfig(final Context context) {
        if (sImagePipelineConfig == null) {
            /**
             * 推荐缓存到应用本身的缓存文件夹，这么做的好处是:
             * 1、当应用被用户卸载后能自动清除缓存，增加用户好感（可能以后用得着时，还会想起我）
             * 2、一些内存清理软件可以扫描出来，进行内存的清理
             */
            File fileCacheDir = context.getApplicationContext().getCacheDir();
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                fileCacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Fresco");
//            }

            DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(context)
                    .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                    .setBaseDirectoryPath(fileCacheDir)
                    .build();

            DiskCacheConfig smallDiskCacheConfig = DiskCacheConfig.newBuilder(context)
                    .setBaseDirectoryPath(fileCacheDir)
                    .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)
                    .setMaxCacheSize(MAX_DISK_SMALL_CACHE_SIZE)
                    .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_SMALL_ONLOWDISKSPACE_CACHE_SIZE)
                    .build();

            // 支持调试时，显示图片加载的Log
            FLog.setMinimumLoggingLevel(FLog.VERBOSE);
            Set<RequestListener> requestListeners = new HashSet<>();
            requestListeners.add(new RequestLoggingListener());

            // 当内存紧张时采取的措施
            MemoryTrimmableRegistry memoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();
            memoryTrimmableRegistry.registerMemoryTrimmable(new MemoryTrimmable() {
                @Override
                public void trim(MemoryTrimType trimType) {
                    final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
//                    MLog.i(String.format("Fresco onCreate suggestedTrimRatio : %d", suggestedTrimRatio));

                    if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                            || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                            || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                            ) {
                        // 清除内存缓存
                        Fresco.getImagePipeline().clearMemoryCaches();
                    }
                }
            });

            // OkHttp中的日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 替换网络加载层为OkHttp
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)  // 添加日志拦截
//                    .retryOnConnectionFailure(false)
                    .build();

            sImagePipelineConfig = OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient) // 将Image pipeline默认使用的网加载层HttpURLConnection替换成OkHttp
//            sImagePipelineConfig = ImagePipelineConfig.newBuilder(context)  // Image pipeline默认使用HttpURLConnection
                    .setBitmapsConfig(Bitmap.Config.RGB_565) // 若不是要求忒高清显示应用，就用使用RGB_565吧（默认是ARGB_8888)
                    .setDownsampleEnabled(true) // 在解码时改变图片的大小，支持PNG、JPG以及WEBP格式的图片，与ResizeOptions配合使用
                    // 设置Jpeg格式的图片支持渐进式显示
                    .setProgressiveJpegConfig(new ProgressiveJpegConfig() {
                        @Override
                        public int getNextScanNumberToDecode(int scanNumber) {
                            return scanNumber + 2;
                        }

                        public QualityInfo getQualityInfo(int scanNumber) {
                            boolean isGoodEnough = (scanNumber >= 5);
                            return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
                        }
                    })
                    .setRequestListeners(requestListeners)
                    .setMemoryTrimmableRegistry(memoryTrimmableRegistry) // 报内存警告时的监听

                    // 设置内存缓存配置
                    .setBitmapMemoryCacheParamsSupplier(new BitmapMemoryCacheParamsSupplier(
                            (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)))
                    .setMainDiskCacheConfig(mainDiskCacheConfig) // 设置主磁盘配置
                    .setSmallImageDiskCacheConfig(smallDiskCacheConfig) // 设置小图的磁盘配置
                    .build();
        }
        return sImagePipelineConfig;
    }

}
