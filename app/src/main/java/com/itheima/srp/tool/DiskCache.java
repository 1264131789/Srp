package com.itheima.srp.tool;

/*
 *  @项目名：  Srp 
 *  @包名：    com.itheima.srp.tool
 *  @文件名:   DiskCache
 *  @创建者:   shenbinjian
 *  @创建时间:  2017/9/13 20:11
 *  @描述：    TODO
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiskCache {
    static String cacheDir="sdcard/cache/";
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir+url);
    }

    public void put(String url,Bitmap bitmap){
        FileOutputStream fileOutputStream= null;
        try {
            fileOutputStream=new FileOutputStream(cacheDir+url);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
