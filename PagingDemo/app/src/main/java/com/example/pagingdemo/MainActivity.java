package com.example.pagingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MyPagedAdaptter pagedAdaptter;
    RecyclerView recyclerView;
    Button buttonPopulate,buttonClear;
    StudentsDatabase studentsDatabase;
    StudentDao studentDao;
    LiveData<PagedList<Student>> allStudentsLivePaged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        pagedAdaptter = new MyPagedAdaptter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//加上分隔符
        recyclerView.setAdapter(pagedAdaptter);
        //获取数据
        studentsDatabase = StudentsDatabase.getInstance(this);
        studentDao = studentsDatabase.getStudentDao();
        allStudentsLivePaged = new LivePagedListBuilder</*此处在Dao中已设置*/>(studentDao.getAllStudents(),30)//一页有多少数据
                .build();
        allStudentsLivePaged.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(PagedList<Student> students) {
                pagedAdaptter.submitList(students);
            }
        });
        buttonPopulate = findViewById(R.id.buttonPopulate);
        buttonPopulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student[] students = new Student[1000];
                for (int i=0;i<1000;i++){
                    Student student = new Student();
                    student.setStudentNumber(i);
                    students[i] = student;
                }
                new InsertAsyncTask(studentDao).execute(students);
            }
        });
        buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ClearAsyncTask(studentDao).execute();
            }
        });
    }

    static class InsertAsyncTask extends AsyncTask<Student, Void, Void >{
        StudentDao studentDao;

        public InsertAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insertStudents(students);
            return null;
        }
    }
    static class ClearAsyncTask extends AsyncTask<Student, Void, Void >{
        StudentDao studentDao;

        public ClearAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.deleteAllStudents();
            return null;
        }
    }
}
