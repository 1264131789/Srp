package com.itheima.srp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.itheima.srp.tool.ImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ImageView ivSrp = (ImageView) findViewById(R.id.iv_srp);
        ImageLoader imageLoader = new ImageLoader();
//        ivSrp.setImageResource(R.mipmap.ic_launcher);
//        imageLoader.useDiskCache(true);
        imageLoader.useDoubleCache(true);
        imageLoader.displayImage("http://192.168.127.2:8080/imgs/sunny.jpg",ivSrp);
    }
}