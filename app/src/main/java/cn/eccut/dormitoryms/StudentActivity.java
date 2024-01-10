package cn.eccut.dormitoryms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentActivity extends AppCompatActivity {
    StudentDao studentDao = new StudentDao(StudentActivity.this);

    EditText et_stuNameS, et_stuNoS, et_genderS, et_ageS, et_gradeS, et_collegeS, et_dorNoS, et_phoneS;
    Button btn_queryS, btn_distributeS, btn_stuMS, btn_signS, btn_addS, btn_delS, btn_modifyS, btn_queryStu;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        listView = findViewById(R.id.listviewStu);

        et_stuNameS = findViewById(R.id.et_stuNameS);
        et_stuNoS = findViewById(R.id.et_stuNoS);
        et_genderS = findViewById(R.id.et_genderS);
        et_ageS = findViewById(R.id.et_ageS);
        et_gradeS = findViewById(R.id.et_gradeS);
        et_collegeS = findViewById(R.id.et_collegeS);
        et_dorNoS = findViewById(R.id.et_dorNoS);
        et_phoneS = findViewById(R.id.et_phoneS);

        btn_queryS = findViewById(R.id.btn_queryS);
        btn_queryS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });

        btn_distributeS = findViewById(R.id.btn_distributeS);
        btn_distributeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, DormActivity.class);
                startActivity(intent);
            }
        });

        btn_stuMS = findViewById(R.id.btn_stuMS);
        btn_stuMS.setActivated(false);

        btn_signS = findViewById(R.id.btn_signS);
        btn_signS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, OutActivity.class);
                startActivity(intent);
            }
        });


        btn_addS = findViewById(R.id.btn_addS);
        btn_addS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = et_stuNoS.getText().toString();
                String name = et_stuNameS.getText().toString();
                String gender = et_genderS.getText().toString();
                int age = Integer.parseInt(et_ageS.getText().toString());
                String college = et_collegeS.getText().toString();
                String grade = et_gradeS.getText().toString();
                String dorNo = et_dorNoS.getText().toString();
                String tel = et_phoneS.getText().toString();

                if (judegeNull(number, name, gender, age, college, grade, dorNo, tel)) {
                    Toast.makeText(StudentActivity.this, "请完整输入学生信息", Toast.LENGTH_SHORT).show();
                } else {
                    if (isValidPhoneNum(tel) && isValidNum(number)) {
                        Student student = new Student(number, name, gender, age, college, grade, dorNo, tel);
                        Log.i("student", student.toString());
                        studentDao.add(student);
                        List<Student> allStudents = studentDao.find_all();
                        List<Map<String, String>> data = convertStudentsToMapList(allStudents);

                        SimpleAdapter simpleAdapter = new SimpleAdapter(
                                StudentActivity.this,
                                data,
                                R.layout.listview,
                                new String[]{"number", "name", "gender", "age", "college", "grade", "dorNo", "tel"},
                                new int[]{R.id.lv_number, R.id.lv_name, R.id.lv_gender, R.id.lv_age, R.id.lv_college, R.id.lv_grade, R.id.lv_dorNo, R.id.lv_tel}
                        );
                        simpleAdapter.notifyDataSetChanged();
                        listView.setAdapter(simpleAdapter);

                    } else {
                        Toast.makeText(StudentActivity.this, "学号或电话输入有误,请检查后提交", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        btn_delS = findViewById(R.id.btn_delS);
        btn_delS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = et_stuNoS.getText().toString();
                String name = et_stuNameS.getText().toString();
                if (TextUtils.isEmpty(number) || TextUtils.isEmpty(name)) {
                    Toast.makeText(StudentActivity.this, "请完整输入学生信息", Toast.LENGTH_SHORT).show();
                } else {
                    studentDao.del(number, name);

                    List<Student> allStudents = studentDao.find_all();
                    List<Map<String, String>> data = convertStudentsToMapList(allStudents);

                    SimpleAdapter simpleAdapter = new SimpleAdapter(
                            StudentActivity.this,
                            data,
                            R.layout.listview,
                            new String[]{"number", "name", "gender", "age", "college", "grade", "dorNo", "tel"},
                            new int[]{R.id.lv_number, R.id.lv_name, R.id.lv_gender, R.id.lv_age, R.id.lv_college, R.id.lv_grade, R.id.lv_dorNo, R.id.lv_tel}
                    );
                    simpleAdapter.notifyDataSetChanged();
                    listView.setAdapter(simpleAdapter);

                }
            }
        });

        btn_modifyS = findViewById(R.id.btn_modifyS);
        btn_modifyS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = et_stuNoS.getText().toString();
                String name = et_stuNameS.getText().toString();
                String gender = et_genderS.getText().toString();
                int age = 18;
                if (TextUtils.isEmpty(et_ageS.getText().toString())){
                    Toast.makeText(StudentActivity.this,"请输入年龄...",Toast.LENGTH_SHORT).show();
                }else {
                    age = Integer.parseInt(et_ageS.getText().toString());
                }

                String college = et_collegeS.getText().toString();
                String grade = et_gradeS.getText().toString();
                String dorNo = et_dorNoS.getText().toString();
                String tel = et_phoneS.getText().toString();
                if (judegeNull(number, name, gender, age, college, grade, dorNo, tel)) {
                    Toast.makeText(StudentActivity.this, "请检查学号、姓名输入是否为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (isValidNum(number) && isValidPhoneNum(tel)) {
                        Student student = new Student(number, name, gender, age, college, grade, dorNo, tel);
                        studentDao.update(student);


                        List<Student> allStudents = studentDao.find_all();
                        List<Map<String, String>> data = convertStudentsToMapList(allStudents);

                        SimpleAdapter simpleAdapter = new SimpleAdapter(
                                StudentActivity.this,
                                data,
                                R.layout.listview,
                                new String[]{"number", "name", "gender", "age", "college", "grade", "dorNo", "tel"},
                                new int[]{R.id.lv_number, R.id.lv_name, R.id.lv_gender, R.id.lv_age, R.id.lv_college, R.id.lv_grade, R.id.lv_dorNo, R.id.lv_tel}
                        );
                        listView.setAdapter(simpleAdapter);
                    } else {
                        Toast.makeText(StudentActivity.this, "学号或电话输入有误,请检查后提交", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_queryStu = findViewById(R.id.btn_queryStu);
        btn_queryStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = et_stuNoS.getText().toString();
                String name = et_stuNameS.getText().toString();
                if (TextUtils.isEmpty(number) || TextUtils.isEmpty(name)) {
                    Toast.makeText(StudentActivity.this, "请输入学号以及姓名进行查询", Toast.LENGTH_SHORT).show();
                } else {

                    List<Student> allStudents = studentDao.find_a(number, name);
                    List<Map<String, String>> data = convertStudentsToMapList(allStudents);

                    SimpleAdapter simpleAdapter = new SimpleAdapter(
                            StudentActivity.this,
                            data,
                            R.layout.listview,
                            new String[]{"number", "name", "gender", "age", "college", "grade", "dorNo", "tel"},
                            new int[]{R.id.lv_number, R.id.lv_name, R.id.lv_gender, R.id.lv_age, R.id.lv_college, R.id.lv_grade, R.id.lv_dorNo, R.id.lv_tel}
                    );
                    listView.setAdapter(simpleAdapter);

                }
            }
        });

    }

    private List<Map<String, String>> convertStudentsToMapList(List<Student> students) {
        List<Map<String, String>> data = new ArrayList<>();

        for (Student student : students) {
            Map<String, String> map = new HashMap<>();

            map.put("number", student.getNumber());
            map.put("name", student.getName());
            map.put("gender",student.getGender());
            map.put("age",String.valueOf(student.getAge()));
            map.put("college",student.getCollege());
            map.put("grade",student.getGrade());
            map.put("dorNo",student.getDorNo());
            map.put("tel",student.getTel());

            data.add(map);
        }

        return data;
    }


    private boolean judegeNull(String number, String name, String gender, int age, String college,
                               String grade, String dorNo, String tel) {
        boolean result = TextUtils.isEmpty(number) || TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(gender) || TextUtils.isEmpty(String.valueOf(age))
                || TextUtils.isEmpty(college) || TextUtils.isEmpty(grade)
                || TextUtils.isEmpty(dorNo) || TextUtils.isEmpty(tel);
        return result;
    }

    public boolean isValidPhoneNum(String phone) {
        //定义手机号正则表达式
        String regex = "^(?:(?:\\+|00)86)?1[3-9]\\d{9}$";
        //编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        //创建匹配器
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public boolean isValidNum(String number) {
        //定义学号正则表达式
        String regex = "^20\\d{9}$";
        //编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        //创建匹配器
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}