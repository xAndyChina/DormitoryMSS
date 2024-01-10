package cn.eccut.dormitoryms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ManageActivity extends AppCompatActivity {
    //数据库访问层
    StudentDao studentDao = new StudentDao(ManageActivity.this);
    EditText et_partNo, et_stuName, et_stuNo;
    Button btn_queryInfo, btn_query, btn_distribute, btn_stuM, btn_sign;
    ListView listView;
    //适配器
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        listView = findViewById(R.id.listviewD);

        btn_queryInfo = findViewById(R.id.btn_addD);
        btn_queryInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_partNo = findViewById(R.id.et_partNoD);
                et_stuName = findViewById(R.id.et_stuNameS);
                et_stuNo = findViewById(R.id.et_stuNoS);
                String partNo = et_partNo.getText().toString();
                String stuName = et_stuName.getText().toString();
                String stuNo = et_stuNo.getText().toString();
                //学生公寓号为空,进行以下查询(用学生姓名和学号联合查询)
                if (TextUtils.isEmpty(partNo)) {
                    if (TextUtils.isEmpty(stuName) || TextUtils.isEmpty(stuNo)) {
                        Toast.makeText(ManageActivity.this, "学生姓名及学号不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        Student student = studentDao.findWithoutPartNo(stuName, stuNo);
                        List<Student> data = new ArrayList<>();
                        List<Map<String, String>> datas = convertStudentsToMapList(data);
                        data.add(student);
                        simpleAdapter = new SimpleAdapter(
                                ManageActivity.this,
                                datas,
                                R.layout.listview,
                                new String[]{"number", "name", "gender", "age", "college", "grade", "dorNo", "tel"},
                                new int[]{R.id.lv_number, R.id.lv_name, R.id.lv_gender, R.id.lv_age, R.id.lv_college, R.id.lv_grade, R.id.lv_dorNo, R.id.lv_tel}
                        );
                        simpleAdapter.notifyDataSetChanged();
                        listView.setAdapter(simpleAdapter);
                    }
                } else {
                    List<Student> withPartNo = studentDao.findDorm(partNo);
                    List<Map<String, String>> students = convertStudentsToMapList(withPartNo);

                    if (students.isEmpty()) {
                        Toast.makeText(ManageActivity.this, "查询结果为空...", Toast.LENGTH_SHORT).show();
                    } else {
                        simpleAdapter = new SimpleAdapter(
                                ManageActivity.this,
                                students,
                                R.layout.listview,
                                new String[]{"number", "name", "gender", "age", "college", "grade", "dorNo", "tel"},
                                new int[]{R.id.lv_number, R.id.lv_name, R.id.lv_gender, R.id.lv_age, R.id.lv_college, R.id.lv_grade, R.id.lv_dorNo, R.id.lv_tel}
                        );
                        simpleAdapter.notifyDataSetChanged();
                        listView.setAdapter(simpleAdapter);
                    }
                }
            }
        });
        //左侧菜单按钮,设置点击事件,点击对应按钮即跳转到相应界面
        btn_query = findViewById(R.id.btn_queryS);
        btn_query.setActivated(false);

        btn_distribute = findViewById(R.id.btn_distributeS);
        btn_distribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageActivity.this, DormActivity.class);
                startActivity(intent);
            }
        });

        btn_stuM = findViewById(R.id.btn_stuMS);
        btn_stuM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageActivity.this, StudentActivity.class);
                startActivity(intent);
            }
        });

        btn_sign = findViewById(R.id.btn_signS);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageActivity.this, OutActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Map<String, String>> convertStudentsToMapList(List<Student> students) {
        List<Map<String, String>> data = new ArrayList<>();

        for (Student student : students) {
            Map<String, String> map = new HashMap<>();

            map.put("number", student.getNumber());
            map.put("name", student.getName());
            map.put("gender", student.getGender());
            map.put("age", String.valueOf(student.getAge()));
            map.put("college", student.getCollege());
            map.put("grade", student.getGrade());
            map.put("dorNo", student.getDorNo());
            map.put("tel", student.getTel());

            data.add(map);
        }

        return data;
    }
}