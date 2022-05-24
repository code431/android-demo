package com.example.imagenet;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //新建控件
    private ImageView volley_img;
    private EditText txtUrl;
    private Button btnLoad;
    //新建一个Volley请求队列
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        volley_img = (ImageView) findViewById(R.id.volley_img);
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        btnLoad = (Button) findViewById(R.id.btnLoad);

        //为按钮添加点击监听
        btnLoad.setOnClickListener(this);
    }

    //按钮的点击监听
    @Override
    public void onClick(View view) {
        //加载网络图片
        //loadImage();
        //加载网络图片-使用了缓存
        loadCacheImage();
    }

    //加载网络图片-使用了缓存
    private void loadCacheImage(){

        //1、创建网络请求队列
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        //获取网络图片的URL
        String url = txtUrl.getText().toString();
        //2、创建请求
        ImageLoader imageLoader = new ImageLoader(requestQueue,new BitmapCache());//带缓存
        //加载失败，采用默认图
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(volley_img,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        //3、发送请求
        imageLoader.get(url,imageListener);
    }

    //加载网络图片
    private void loadImage(){
        //1、创建网络请求队列
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        //获取网络图片的URL
        String url = txtUrl.getText().toString();

        //2、创建请求
        final ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                //正确接收图片时，调用的方法
                volley_img.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //接收图片错误，调用的方法
                Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_LONG).show();
                //设置默认图片
                volley_img.setImageResource(R.mipmap.ic_launcher);
            }
        });
        //3、将请求加入到队列
        requestQueue.add(imageRequest);
    }
}
