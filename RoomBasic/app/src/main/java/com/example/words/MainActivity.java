package com.example.words;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonInsert, buttonClear;//,buttonUpdate,buttonDelete;
    TextView textView;
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    MyAdapter myAdapter1, myAdapter2;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        myAdapter1 = new MyAdapter(false,wordViewModel);
        myAdapter2 = new MyAdapter(true,wordViewModel);
        recyclerView.setAdapter(myAdapter1);
        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    recyclerView.setAdapter(myAdapter2);
                } else {
                    recyclerView.setAdapter(myAdapter1);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textView = findViewById(R.id.textViewNumber);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonClear = findViewById(R.id.buttonClear);
        //buttonDelete = findViewById(R.id.buttonDelete);
        //buttonUpdate = findViewById(R.id.buttonUpdate);
        //updateView();
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapter1.getItemCount();
                myAdapter1.setAllWords(words);
                myAdapter2.setAllWords(words);
                if (temp != words.size()){      //判断长度是否变化，长度不变不刷新列表，使aSwitchChineseInvisible导致的变化在此处不刷新
                    myAdapter1.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
                }
            }
        });

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] english = {
                        "Hello",
                        "World",
                        "Android",
                        "Google",
                        "Studio",
                        "Project",
                        "Database",
                        "Recycler",
                        "View",
                        "String",
                        "Value",
                        "Integer"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓系统",
                        "谷歌公司",
                        "工作室",
                        "项目",
                        "数据库",
                        "回收站",
                        "视图",
                        "字符串",
                        "价值",
                        "整数类型"
                };
                for (int i = 0; i < english.length; i++) {
                    wordViewModel.insertWords(new Word(english[i], chinese[i]));
                }
                //updateView();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordViewModel.deleteAllWords();
                //updateView();
            }
        });
//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Word word = new Word("Hi","你好啊!");
//                word.setId(28);
//                wordViewModel.updateWords(word);
//                //updateView();
//            }
//        });
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Word word = new Word("Hi","你好啊!");
//                word.setId(29);
//                wordViewModel.deleteWords(word);
//                //updateView();
//            }
//        });
//    }

//    void updateView(){
//        List<Word> list = wordDao.getAllWords();
//        String text = "";
//        for (int i=0;i<list.size();i++){
//            Word word = list.get(i);
//            text += word.getId() + ":" + word.getWord() + "=" + word.getChineseMeaning() + "\n";
//        }
//        textView.setText(text);
//    }
    }
}
