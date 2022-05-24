package com.example.words;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao            //database access object数据库操作的接口
public interface WordDao {
    @Insert
    void insertWords(Word... words);        //表示可以传递多个参数，个数不限，Word word传递单个参数

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    //List<Word> getAllWords();
    LiveData<List<Word>> getAllWordsLive();
}
