package cn.eccut.dormitoryms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.jar.JarFile;

public class MainActivity extends AppCompatActivity {
    //定义控件
    EditText et_username, et_password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_pwd);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "用户名或密码不能为空!请检查后提交!", Toast.LENGTH_SHORT).show();
                } else {
                    if (username.equals("admin") && password.equals("123")) {
                        Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}