package com.example.serialiable;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    TextView textViewGrade;
    EditText editTextName,editTextAge,editTextMath,editTextEnglish,editTextChinese;
    Button buttonLoad,buttonSave;
    private static final String FILE_NAME = "myfile.data";
    private static final String TAG = "hello";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextMath = findViewById(R.id.editTextMath);
        editTextChinese = findViewById(R.id.editTextChinese);
        editTextEnglish = findViewById(R.id.editTextEnglish);
        textViewGrade = findViewById(R.id.textViewGrade);
        buttonLoad = findViewById(R.id.buttonLoad);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int math = Integer.valueOf(editTextMath.getText().toString());
                int english = Integer.valueOf(editTextEnglish.getText().toString());
                int chinese = Integer.valueOf(editTextChinese.getText().toString());
                Score score = new Score(math,english,chinese);
                String name = editTextName.getText().toString();
                int age = Integer.valueOf(editTextAge.getText().toString());
                Student student = new Student(name,age,score);
                /*将对象序列化，写入到文件中，借助接口*/
                /*写入*/
                try{
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(openFileOutput(FILE_NAME,MODE_PRIVATE));//获取接口
                    objectOutputStream.writeObject(student);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                    editTextName.getText().clear();
                    editTextAge.getText().clear();
                    editTextMath.getText().clear();
                    editTextEnglish.getText().clear();
                    editTextChinese.getText().clear();
                    editTextMath.setText("-");
                    Toast.makeText(MainActivity.this, "Data saved!", Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                    Log.d(TAG, "onClick: ",e);
                }
            }
        });
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //读取
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(openFileInput(FILE_NAME));
                    Student student = (Student) objectInputStream.readObject();
                    editTextName.setText(student.getName());
                    editTextAge.setText(String.valueOf(student.getAge()));
                    editTextMath.setText(String.valueOf(student.getScore().getMath()));
                    editTextChinese.setText(String.valueOf(student.getScore().getChinese()));
                    editTextEnglish.setText(String.valueOf(student.getScore().getEnglish()));
                }catch (IOException | ClassNotFoundException e){
                    Log.d(TAG, "onClick: ",e);
                }
            }
        });
    }
}
