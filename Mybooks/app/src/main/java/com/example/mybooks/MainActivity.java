package com.example.mybooks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.mybooks.adapter.MyRecyclerAdapter;
import com.example.mybooks.adapter.OnItemClickListener;
import com.example.mybooks.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity /*implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener*/ {

    //创建RecyclerView控件
    private RecyclerView myrecyclerview;
    //定义一个适配器
    private MyRecyclerAdapter myadapter;
    //定义一个数组，用于存储
    private String[] names = {"张三","李四","王五","张麻子"};
    private int[] imgs = {R.mipmap.tx1,R.mipmap.tx2,R.mipmap.tx3,R.mipmap.tx4,};

    //定义一个通讯录列表，作为数据源
    private List<Person> books = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //一、ArrayAdapter
//        String[] books = {"张三","李四","王五","张麻子"};
        //初始化控件
//        ListView listView = (ListView)findViewById(R.id.listview);
        //创建适配器(参数一：上下文，参数二：列表项布局文件，参数三：数据源)
//        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,books);
//        设置适配器到ListView
//        listView.setAdapter(Adapter);
        //
        //二、BaseAdapter
        //初始化控件
//        mylistview = (ListView)findViewById(R.id.listview);
//        //初始化数据
//        initDataBooks();
//        //创建适配器(参数一：数据源，参数二：上下文)
//        myadapter = new PersonAdapter(books,MainActivity.this);
//        //设置适配器到ListView
//        mylistview.setAdapter(myadapter);
//        //监听时间
//        mylistview.setOnItemClickListener(this);//单击某个条目的监听
//        mylistview.setOnScrollListener(this);//视图在滚动中加载数据
        //三、RecyclerView
        //初始化数据
        initDataBooks();
        //初始化控件
        myrecyclerview = findViewById(R.id.myrecyclerview);
        //设置RecyclerView布局管理器
        myrecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        //初始化数据适配器
        myadapter = new MyRecyclerAdapter(books,MainActivity.this);
        //设置动画，采用默认动画效果
        myrecyclerview.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        myrecyclerview.setAdapter(myadapter);
        //设置监听
        myadapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                //获取通讯录点击对象
                Person person = books.get(postion);
                Toast.makeText(MainActivity.this,"你点击了第"+postion+"个通讯录，姓名："+person.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    //BaseAdapter实现滚动监听方法
//    @Override
//    public void onScrollStateChanged(AbsListView absListView, int i) {
//
//        //判断滚动状态
//        if (i==SCROLL_STATE_FLING){
//            Toast.makeText(MainActivity.this,"用户手指离开屏幕前，由于用力滑动了一下，列表仍然依靠惯性在继续滑动",Toast.LENGTH_LONG).show();
//        }
//        else if (i==SCROLL_STATE_IDLE){
//            Toast.makeText(MainActivity.this,"列表停止滑动",Toast.LENGTH_LONG).show();
//            //增加一条通讯录数据
//            Person person = new Person("小马",R.mipmap.tx1);
//            //将新的通讯录数据加入到通讯录列表，数据源books中
//            books.add(person);
//            //通知UI线程刷新界面ListView
//            myadapter.notifyDataSetChanged();
//        }
//        else if (i==SCROLL_STATE_TOUCH_SCROLL){
//            Toast.makeText(MainActivity.this,"手指离开屏幕，列表正在滑动",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//
//    }
//
//    //实现点击方法
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        //获取点击的条目对象
//        Person person = books.get(position);
//        //弹出点击通讯录人的姓名
//        Toast.makeText(MainActivity.this,"你点击了第"+position+"个通讯录，姓名："+person.getName(),Toast.LENGTH_LONG).show();

//    }

    //初始化数据
    private void initDataBooks(){
        for (int i=0;i<names.length;i++){
            //新建Person对象，存放通讯录数据
            Person person = new Person(names[i], imgs[i]);
            //将通讯录数据加入到数据列表中
            books.add(person);
        }
    }
}
