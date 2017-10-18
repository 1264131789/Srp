package com.itheima.srp.tool;

/*
 *  @项目名：  Srp 
 *  @包名：    com.itheima.srp.tool
 *  @文件名:   DoubleCache
 *  @创建者:   shenbinjian
 *  @创建时间:  2017/10/18 18:35
 *  @描述：    TODO
 */

import android.graphics.Bitmap;

public class DoubleCache {
    MemoryCache mMemoryCache =new MemoryCache();
    DiskCache mDiskCache=new DiskCache();
    public Bitmap get(String url){
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap==null){
            bitmap=mDiskCache.get(url);
        }
        return bitmap;
    }

    public void put(String url,Bitmap bitmap){
        mMemoryCache.put(url,bitmap);
        mDiskCache.put(url,bitmap);
    }

}
