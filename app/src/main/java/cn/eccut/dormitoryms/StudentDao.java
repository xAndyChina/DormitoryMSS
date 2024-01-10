package cn.eccut.dormitoryms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Dao层：学生数据库访问
public class StudentDao {
    private StudentHelper studentHelper;

    public StudentDao(Context context) {
        studentHelper = new StudentHelper(context);
    }

    //增加学生
    public void add(Student student) {
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();
        String number, name, gender, college, grade, partNo, tel;
        int age;
        age = student.getAge();
        number = student.getNumber();
        name = student.getName();
        gender = student.getGender();
        college = student.getCollege();
        grade = student.getGrade();
        tel = student.getTel();
        partNo = student.getDorNo();
        sqLiteDatabase.execSQL("insert into students(number,name,gender,age,college,grade,dorNo,tel) values (?,?,?,?,?,?,?,?)", new Object[]{number, name, gender, age, college, grade, partNo, tel});
        sqLiteDatabase.close();
    }

    //将学生信息录入宿舍信息数据库中
    public void addStu2Part(String dorNo, String name, String number) {
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update students set dorNo =? where name =? and number =?", new Object[]{dorNo, name, number});
        sqLiteDatabase.close();
    }

    //删除学生
    public void del(String number, String name) {
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from students where number=? and name=?", new Object[]{number, name});
        sqLiteDatabase.close();
    }

    //更新学生信息
    public void update(Student student) {
        String number = student.getNumber();
        String name = student.getName();
        String gender = student.getGender();
        int age = student.getAge();
        String college = student.getCollege();
        String grade = student.getGrade();
        String dorNo = student.getDorNo();
        String tel = student.getTel();
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update students set gender =?,age=?,college=?,grade=?,dorNo=?,tel=? where name =? and number =?", new Object[]{gender, age, college, grade, dorNo, tel, name, number});
        sqLiteDatabase.close();
    }

    //根据宿舍进行查找
    public List<Student> findDorm(String partNo) {
        List<Student> data = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select number,name,gender,age,college,grade,dorNo,tel from students where dorNo = ?", new String[]{partNo});
        if (cursor.getCount() > 0) {
            try {
                while (cursor.moveToNext()) {

                    @SuppressLint("Range") String number = cursor.getString(0);
                    @SuppressLint("Range") String name = cursor.getString(1);
                    @SuppressLint("Range") String gender = cursor.getString(2);
                    @SuppressLint("Range") int age = Integer.parseInt(cursor.getString(3));
                    @SuppressLint("Range") String college = cursor.getString(4);
                    @SuppressLint("Range") String grade = cursor.getString(5);
                    @SuppressLint("Range") String dorNo = cursor.getString(6);
                    @SuppressLint("Range") String tel = cursor.getString(7);

                    Student student = new Student(number, name, gender, age, college, grade, dorNo, tel);
                    data.add(student);
                }
            } finally {
                cursor.close();
            }
        }
        return data;
    }

    //查找学生(带有宿舍号)
    public List<Student> findWithPartNo(String partNo, String name, String number) {
        List<Student> data = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select number,name,gender,age,college,grade,dorNo,tel from students join dormitorys on dorNo = num where name = ? and number = ?", new String[]{name, number});
        if (cursor.getColumnCount() > 0) {

            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setNumber(cursor.getString(0));
                student.setName(cursor.getString(1));
                student.setGender(cursor.getString(2));
                student.setAge(Integer.parseInt(cursor.getString(3)));
                student.setCollege(cursor.getString(4));
                student.setGrade(cursor.getString(5));
                student.setDorNo(cursor.getString(6));
                student.setTel(cursor.getString(7));
                data.add(student);
            }
        }
        return data;
    }

    public Student findWithoutPartNo(String name, String number) {
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select number,name,gender,age,college,grade,dorNo,tel from students where name=? & number=?", new String[]{name, number});
        boolean result = cursor.moveToNext();
        boolean find_result = false;
        Student student = new Student();
        if (result) {
            student.setNumber(cursor.getString(0));
            student.setName(cursor.getString(1));
            student.setGender(cursor.getString(2));
            student.setAge(Integer.parseInt(cursor.getString(3)));
            student.setCollege(cursor.getString(4));
            student.setGrade(cursor.getString(5));
            student.setDorNo(cursor.getString(6));
            student.setTel(cursor.getString(7));
        }
        cursor.close();
        sqLiteDatabase.close();
        return student;
    }


    public boolean judgeExists(String name, String number) {
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from students where name=? and number=?", new String[]{name, number});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        sqLiteDatabase.close();
        return result;
    }

    public List<Student> find_a(String number, String name) {
        List<Student> students = new ArrayList<Student>();
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select number,name,gender,age,college,grade,dorNo,tel from students WHERE number=? AND name=?", new String[]{number, name});
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String number1 = cursor.getString(0);
                @SuppressLint("Range") String name1 = cursor.getString(1);
                @SuppressLint("Range") String gender = cursor.getString(2);
                @SuppressLint("Range") int age = Integer.parseInt(cursor.getString(3));
                @SuppressLint("Range") String college = cursor.getString(4);
                @SuppressLint("Range") String grade = cursor.getString(5);
                @SuppressLint("Range") String dorNo = cursor.getString(6);
                @SuppressLint("Range") String tel = cursor.getString(7);

                Student student = new Student(number1, name1, gender, age, college, grade, dorNo, tel);
                students.add(student);
            }
        } finally {
            cursor.close();
        }
        sqLiteDatabase.close();
        return students;

    }

    public List<Student> find_all() {
        List<Student> students = new ArrayList<Student>();
        SQLiteDatabase sqLiteDatabase = studentHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from students", null);
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String number = cursor.getString(1);
                @SuppressLint("Range") String name = cursor.getString(2);
                @SuppressLint("Range") String gender = cursor.getString(3);
                @SuppressLint("Range") int age = Integer.parseInt(cursor.getString(4));
                @SuppressLint("Range") String college = cursor.getString(5);
                @SuppressLint("Range") String grade = cursor.getString(6);
                @SuppressLint("Range") String dorNo = cursor.getString(7);
                @SuppressLint("Range") String tel = cursor.getString(8);

                Student student = new Student(number, name, gender, age, college, grade, dorNo, tel);
                students.add(student);
            }
        } finally {
            cursor.close();
        }
        sqLiteDatabase.close();
        return students;
    }
}
