package com.example.mybooks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybooks.R;
import com.example.mybooks.entity.Person;

import java.util.ArrayList;
import java.util.List;

//自定义适配器
public class PersonAdapter extends BaseAdapter {

    //通讯录数据
    private List<Person> pdata = new ArrayList<Person>();

    //上下文
    private Context context;

    //构造方法
    public PersonAdapter(List<Person> pdata, Context context) {
        this.pdata = pdata;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pdata.size();//获取列表数据个数
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;//返回数据项在列表中的索引(下标)
    }

    //优化ListView
    //定义一个ViewHolder静态类
    static class ViewHolder{
        //定义属性，对应是列表数据项
        private ImageView myimg;
        private TextView myname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //定义一个ViewHolder对象
        ViewHolder holder;
        //判断convertView是否为空,converView对应的列表项
        if (convertView==null){
            //新建
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bookitem,parent,false);

            holder.myimg = (ImageView) convertView.findViewById(R.id.item_img);
            holder.myname = (TextView) convertView.findViewById(R.id.item_name);

            convertView.setTag(holder);
        }
        else{
            //复用列表项目
            holder = (ViewHolder)convertView.getTag();
        }


        //设置列表项数据
        holder.myimg.setImageResource(pdata.get(position).getImg());
        holder.myname.setText(pdata.get(position).getName());

        return convertView;
    }
}
