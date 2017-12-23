package com.example.persionalfinacial.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.persionalfinacial.R;
import com.example.persionalfinacial.model.User;
import com.example.persionalfinacial.sql.MyDatabaseHelper;

public class register extends AppCompatActivity {
    private EditText Etname;
    private EditText Etpassword;
    private EditText EtRepassword;
    private String name;
    private String password;
    private String Repassword;

    private User user;
    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user=new User();
        dbHelper=new MyDatabaseHelper(register.this);
        Button btsubreg= (Button) findViewById(R.id.submit_register);

        btsubreg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Etname= (EditText) findViewById(R.id.et_rgs_name);
                Etpassword= (EditText) findViewById(R.id.et_rgs_passwd);
                EtRepassword= (EditText) findViewById(R.id.et_rgs_repass);
                name=Etname.getText().toString();
                password=Etpassword.getText().toString();
                Repassword=EtRepassword.getText().toString();

                if(dbHelper.isExistName(name)){
                    Toast.makeText(register.this, "用户名存在，请重新输入", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(Repassword)){
                        user.setName(name);
                        user.setPassword(password);
                        dbHelper.addUser(user);
                        Toast.makeText(register.this, "注册成功！请回到登录界面登录", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(register.this, "输入的密码和确认密码不同，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
