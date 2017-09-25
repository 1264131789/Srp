package com.itheima.srp.tool;

/*
 *  @项目名：  Srp 
 *  @包名：    com.itheima.srp.tool
 *  @文件名:   ImageCache
 *  @创建者:   shenbinjian
 *  @创建时间:  2017/9/13 19:25
 *  @描述：    TODO
 */

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache {
    LruCache<String,Bitmap> mImageCache;
    public ImageCache(){
        initImageCache();
    }

    private void initImageCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize=maxMemory/4;
        mImageCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };
    }

    public void put(String url,Bitmap bitmap){
        mImageCache.put(url,bitmap);
    }

    public Bitmap get(String url){
        return mImageCache.get(url);
    }
}
