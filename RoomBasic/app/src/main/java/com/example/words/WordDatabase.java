package com.example.words;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton
@Database(entities = {Word.class},version = 5,exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;
    //synchronized进一步强化singleton，保证了当有多个线程请求INSTANCE时不会发生碰撞，会进行排队，保证只有一个INSTANCE生成。
    static synchronized WordDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"word_database")
                    //.fallbackToDestructiveMigration() //破坏式的迁移，将数据清空后迁移
                    //.allowMainThreadQueries()
                    .addMigrations(MIGRATION_4_5) //需要传递一个自己创建的迁移策略作为参数
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordDao getWordDao();

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");//SQL没有布尔类型变量,用代替INTEGER
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        //删除foo_data和bar_data属性
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL,english_word TEXT, " +
                    "chinese_meaning TEXT)");  //创建一个新表word_temp，来存储新数据库的数据
            database.execSQL("INSERT INTO word_temp (id,english_word,chinese_meaning)" +
                    "SELECT id,english_word,chinese_meaning FROM word");   //从原来的word表中查找数据添加到word_temp中
            database.execSQL("DROP TABLE word");   //删除原来的表word
            database.execSQL("ALTER TABLE word_temp RENAME TO word"); //将word_temp改名为word
        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN chinese_invisible INTEGER NOT NULL DEFAULT 0");  //SQL没有布尔类型变量,用代替INTEGER
        }
    };
}

