package com.example.persionalfinacial.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.persionalfinacial.R;
import com.example.persionalfinacial.sql.MyDatabaseHelper;

public class login extends AppCompatActivity {
    private EditText Etname;
    private EditText Etpassword;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper=new MyDatabaseHelper(this);
      //  dbHelper.getWritableDatabase();//创建出数据库，触发Helper中onCreate。


        //下面是还没有进行判断的情况下。

        Button btlogin= (Button) findViewById(R.id.button);
        Etname= (EditText) findViewById(R.id.et_name);
        Etpassword= (EditText) findViewById(R.id.et_passwd);

        btlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name=Etname.getText().toString().trim();
                String password=Etpassword.getText().toString().trim();

                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(password)){
                    Toast.makeText(login.this,"用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 2017/12/14 接下来就是判断用户名在不在数据库中，如果不在的话，提示用户进行注册。
                else if(dbHelper.isExistName(name)==false) {
                    Toast.makeText(login.this, "该用户不存在，请注册！", Toast.LENGTH_SHORT).show();
                }
                else {
                    //该用户存在，进行密码判断。
                    if(dbHelper.isPass(name,password)==true){
                        String data=name;//用于传递当前账户姓名。
                        Intent intent=new Intent(login.this,MainActivity.class);
                        intent.putExtra("extra_data",data);
                        startActivity(intent);
                    }else{
                        Toast.makeText(login.this, "密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Button btregister= (Button) findViewById(R.id.register);
        btregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,register.class);
                startActivity(intent);
            }
        });
    }
}
