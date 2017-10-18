package com.itheima.srp.tool;

/*
 *  @项目名：  Srp 
 *  @包名：    com.itheima.srp.tool
 *  @文件名:   ImageLoader
 *  @创建者:   shenbinjian
 *  @创建时间:  2017/9/2 0:03
 *  @描述：    图片加载器1.0
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImageLoader {
    //图片缓存
//    LruCache<String,Bitmap> mImageCache;
    //内存缓存
    ImageCache mImageCache = new ImageCache();
    //sd卡缓存
    DiskCache mDiskCache = new DiskCache();
    //双缓存
    DoubleCache mDoubleCache = new DoubleCache();


    public void useDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    //是否使用sd卡缓存
    boolean isUseDiskCache = false;

    public void useDoubleCache(boolean useDoubleCache) {
        isUseDoubleCache = useDoubleCache;
    }

    boolean isUseDoubleCache =false;
    //线程池，线程数量为CPU的数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    /*public ImageLoader(){
        initImageCache();
    }

    private void initImageCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //取最大内存的1/4作为缓存
        final int cacheSize=maxMemory/4;
        mImageCache=new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };
    }*/

    public void displayImage(final String url, final ImageView imageView) {
//        Bitmap bitmap = isUseDiskCache ? mDiskCache.get(url) : mImageCache.get(url);
        Bitmap bitmap=null;
        if (isUseDiskCache){
            bitmap=mDiskCache.get(url);
        }else if (isUseDoubleCache){
            bitmap=mDoubleCache.get(url);
        }else {
            bitmap=mImageCache.get(url);
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(url)) {
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(url, bitmap);
            }
        });
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
