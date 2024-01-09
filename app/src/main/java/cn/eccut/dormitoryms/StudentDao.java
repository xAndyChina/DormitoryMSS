package cn.eccut.dormitoryms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Dao层：学生数据库访问
public class StudentDao {
    private StudentHelper studentHelper;

    public StudentDao(Context context) {
        studentHelper=new StudentHelper(context);
    }

    //增加学生
    public void add(Student student) {
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        String number,name,gender,college,grade,partNo,tel;
        int age;
        age = student.getAge();
        number = student.getNumber();
        name = student.getName();
        gender = student.getGender();
        college = student.getCollege();
        grade = student.getGrade();
        tel = student.getTel();
        partNo = student.getDorNo();
        sqLiteDatabase.execSQL("insert into students(number,name,gender,age,college,grade,dorNo,tel) values (?,?,?,?,?,?,?,?)", new Object[]{number,name,gender,age,college,grade,partNo,tel});
        sqLiteDatabase.close();
    }

    //将学生信息录入宿舍信息数据库中
    public void addStu2Part(String dorNo,String name,String number){
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update students set dorNo =? where name =? and number =?",new Object[]{dorNo,name,number});
        sqLiteDatabase.close();
    }
    //删除学生
    public void del(String number,String name) {
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from students where number=? and name=?",new Object[]{number,name});
        sqLiteDatabase.close();
    }
    //更新学生信息
    public void update(Student student){
        String number = student.getNumber();
        String name = student.getName();
        String gender = student.getGender();
        int age = student.getAge();
        String college = student.getCollege();
        String grade = student.getGrade();
        String dorNo = student.getDorNo();
        String tel = student.getTel();
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("update students set gender =?,age=?,college=?,grade=?,dorNo=?,tel=? where name =? and number =?",new Object[]{gender,age,college,grade,dorNo,tel,name,number});
        sqLiteDatabase.close();
    }

    //查找学生(带有宿舍号)
    public List findWithPartNo(String partNo,String name,String number){
        List data = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select number,name,gender,age,college,grade,dorNo,tel from students join dormitorys on dorNo = num", new String[]{});
        if (cursor.getColumnCount() > 0) {

            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setNumber(cursor.getString(1));
                student.setName(cursor.getString(2));
                student.setGender(cursor.getString(3));
                student.setAge(Integer.parseInt(cursor.getString(4)));
                student.setCollege(cursor.getString(5));
                student.setGrade(cursor.getString(6));
                student.setDorNo(cursor.getString(7));
                student.setTel(cursor.getString(8));
                data.add(student);
            }
        }
        return data;
    }

    public Student findWithoutPartNo(String name,String number){
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from students where name=? & number=?", new String[]{name,number});
        boolean result=cursor.moveToNext();
        boolean find_result=false;
        Student student = new Student();
        if(result){
            student.setNumber(cursor.getString(1));
            student.setName(cursor.getString(2));
            student.setGender(cursor.getString(3));
            student.setAge(Integer.parseInt(cursor.getString(4)));
            student.setCollege(cursor.getString(5));
            student.setGrade(cursor.getString(6));
            student.setDorNo(cursor.getString(7));
            student.setTel(cursor.getString(8));
        }
        cursor.close();
        sqLiteDatabase.close();
        return student;
    }


    public boolean judgeExists(String name,String number){
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from students where name=? and number=?", new String[]{name,number});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        sqLiteDatabase.close();
        return result;
    }
    public boolean find(String name){
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select number,sex from students where name=?", new String[]{name});
        boolean result=cursor.moveToNext();
        boolean find_result=false;
        if(result){
//			number=cursor.getString(0);
//			sex=cursor.getString(1);
            find_result=true;
        }
        cursor.close();
        sqLiteDatabase.close();
        return find_result;
    }

    public List<Student> find_a(String number, String name) {
        SQLiteDatabase db = studentHelper.getWritableDatabase();
        List<Student> students = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM students WHERE number=? AND name=?", new String[]{number, name});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Retrieve data from the cursor and create Student objects
                /*String studentNumber = cursor.getString(1);
                String studentName   = cursor.getString(cursor.getColumnIndex("name"));
                String gender        = cursor.getString(cursor.getColumnIndex("gender"));
                int age              = cursor.getInt(cursor.getColumnIndex("age"));
                String college       = cursor.getString(cursor.getColumnIndex("college"));
                String grade         = cursor.getString(cursor.getColumnIndex("grade"));
                String dorNo         = cursor.getString(cursor.getColumnIndex("dorNo"));
                String tel           = cursor.getString(cursor.getColumnIndex("tel"));*/


                String studentNumber = cursor.getString(1);
                String studentName   = cursor.getString(2);
                String gender        = cursor.getString(3);
                int age              = Integer.parseInt(cursor.getString(4));
                String college       = cursor.getString(5);
                String grade         = cursor.getString(6);
                String dorNo         = cursor.getString(7);
                String tel           = cursor.getString(8);

                Student student = new Student(studentNumber, studentName, gender, age, college, grade, dorNo, tel);
                students.add(student);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        return students;
    }


    public Student find_a2(String name,String number){
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from students where name=? and number =?", new String[]{name,number});
        Student student = new Student();
        if (cursor.getCount() > 0){

            student.setNumber(cursor.getString(1));
            student.setName(cursor.getString(2));
            student.setGender(cursor.getString(3));
            student.setAge(Integer.parseInt(cursor.getString(4)));
            student.setCollege(cursor.getString(5));
            student.setGrade(cursor.getString(6));
            student.setDorNo(cursor.getString(7));
            student.setTel(cursor.getString(8));
        }
        cursor.close();
        sqLiteDatabase.close();

        return student;
    }

    public List<Student> find_all(){
        List<Student> students=new ArrayList<Student>();
        SQLiteDatabase sqLiteDatabase=studentHelper.getWritableDatabase();


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

                Student student = new Student(number,name,gender,age,college,grade,dorNo,tel);
                students.add(student);
            }
        } finally {
            cursor.close();
        }
        sqLiteDatabase.close();
        return students;
    }
}
