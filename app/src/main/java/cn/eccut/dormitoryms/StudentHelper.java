package cn.eccut.dormitoryms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StudentHelper extends SQLiteOpenHelper {
    //数据库名常量
    private static final String DATABASE_NAME = "dorm";
    //数据库版本常量
    private static final Integer DATABASE_VERSION = 1;
    //表名常量
    private static final String TABLE_NAME1 = "students";
    private static final String TABLE_NAME2 = "dormitorys";


    public StudentHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建学生信息表
        String sqlCreate1 = "create table "+ TABLE_NAME1 +"(id integer primary key autoincrement," +
                "number varchar(30),name varchar(30),gender varchar(10),age int,college varchar(20)," +
                "grade varchar(20),dorNo varchar(15),tel varchar(30))";
        //创建宿舍信息表
        String sqlCreate2 = "create table "+ TABLE_NAME2 +"(id integer primary key autoincrement,num varchar(30),floor int,bedNum int,s1 varchar(30),s2 varchar(30),s3 varchar(30),s4 varchar(30))";
        sqLiteDatabase.execSQL(sqlCreate1);
        sqLiteDatabase.execSQL(sqlCreate2);
    }

    //版本更新
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion > newVersion){
            sqLiteDatabase.needUpgrade(newVersion);
        }
    }
}
