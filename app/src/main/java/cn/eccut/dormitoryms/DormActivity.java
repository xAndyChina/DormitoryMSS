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

public class DormActivity extends AppCompatActivity {
    //数据库访问层
    StudentDao studentDao = new StudentDao(DormActivity.this);
    EditText et_partNoD, et_stuNameD, et_stuNoD;
    Button btn_queryD, btn_distributeD, btn_stuMD, btn_signD, btn_addD;
    ListView listView;
    //适配器
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorm);

        listView = findViewById(R.id.listviewD);

        et_partNoD = findViewById(R.id.et_partNoD);
        et_stuNameD = findViewById(R.id.et_stuNameS);
        et_stuNoD = findViewById(R.id.et_stuNoS);

        btn_queryD = findViewById(R.id.btn_queryS);
        btn_queryD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DormActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });

        btn_distributeD = findViewById(R.id.btn_distributeS);
        btn_distributeD.setActivated(false);

        btn_stuMD = findViewById(R.id.btn_stuMS);
        btn_stuMD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DormActivity.this, StudentActivity.class);
                startActivity(intent);
            }
        });

        btn_signD = findViewById(R.id.btn_signS);
        btn_signD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DormActivity.this, OutActivity.class);
                startActivity(intent);
            }
        });


        btn_addD = findViewById(R.id.btn_addD);
        btn_addD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String partNo = et_partNoD.getText().toString();
                String stuName = et_stuNameD.getText().toString();
                String stuNo = et_stuNoD.getText().toString();
                if (TextUtils.isEmpty(partNo) || TextUtils.isEmpty(stuName) || TextUtils.isEmpty(stuNo)) {
                    Toast.makeText(DormActivity.this, "请完整输入以上三项", Toast.LENGTH_SHORT).show();
                } else {
                    if (studentDao.judgeExists(stuName, stuNo)) {
                        studentDao.addStu2Part(partNo, stuName, stuNo);
                        List<Student> withPartNo = studentDao.findDorm(partNo);
                        List<Map<String, String>> students = convertStudentsToMapList(withPartNo);
                        simpleAdapter = new SimpleAdapter(
                                DormActivity.this,
                                students,
                                R.layout.listview,
                                new String[]{"number", "name", "gender", "age", "college", "grade", "dorNo", "tel"},
                                new int[]{R.id.lv_number, R.id.lv_name, R.id.lv_gender, R.id.lv_age, R.id.lv_college, R.id.lv_grade, R.id.lv_dorNo, R.id.lv_tel}
                        );
                        simpleAdapter.notifyDataSetChanged();
                        listView.setAdapter(simpleAdapter);
                    } else {
                        Toast.makeText(DormActivity.this, "该学生不存在,请检查姓名或学号是否有误", Toast.LENGTH_SHORT).show();
                    }
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