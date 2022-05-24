package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "hello";
    ImageView imageView;
    String url2 = "https://pixabay.com/get/g27b7978d03f3745b13e203df6c88cbeb8583cc2fdd6c8be14c7af3fab6e71bbecb94528fbe57799de88c9f0f91632362036f46d02ffb4b9c2797834b195f0526_640.jpg";
    String url1 = "https://pixabay.com/get/gd65f13ba9eaf5ad3bafcd067152f45f7c39354304e437bd6412b1692086f73097b59876e6458a283a082a1193e02267a2a41028d537f0b7676ff687a38732016_640.jpg";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        swipeRefreshLayout = findViewById(R.id.swipeRefresjLayout);
        RequestQueue queue = Volley.newRequestQueue(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImage();
            }
        });
    }

    void loadImage(){
        Random random = new Random();
        String url = random.nextBoolean()? url1 : url2;//
        Glide.with(this)
                .load(url)
                //占位符预览图
                .placeholder(R.drawable.ic_launcher_background)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    //加载失败
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        return false;
                    }

                    @Override
                    //加载成功
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        return false;
                    }
                })
                .into(imageView);
    }
}
