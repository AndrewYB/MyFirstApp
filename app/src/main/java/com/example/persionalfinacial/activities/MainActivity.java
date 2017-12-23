package com.example.persionalfinacial.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.persionalfinacial.R;

public class MainActivity extends AppCompatActivity {
    private TextView tv_userinfo;
    private String cur_user;//当前用户
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        String data=intent.getStringExtra("extra_data");
        cur_user=data;
        tv_userinfo= (TextView) findViewById(R.id.user);
        tv_userinfo.setText(data);

        Button btedit= (Button) findViewById(R.id.bt_editinfo);
        btedit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,userinfo.class);
                intent.putExtra("extra_data",cur_user);
                startActivity(intent);
            }
        });
// TODO: 2017/12/18 无论进行多少次插入，数据库中只有第一次的结果。bug!
        Button btincome= (Button) findViewById(R.id.bt_income);
        btincome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,income.class);
                intent.putExtra("extra_data",cur_user);
                startActivity(intent);
            }
        });
 
        Button btpay= (Button) findViewById(R.id.bt_pay);
        btpay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,pay.class);
                intent.putExtra("extra_data",cur_user);
                startActivity(intent);
            }
        });
        Button btqydate= (Button) findViewById(R.id.bt_queryBydate);
        btqydate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,datequery.class);
                startActivity(intent);
            }
        });

        Button btqyin= (Button) findViewById(R.id.bt_queryByincome);
        btqyin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,incomequery.class);
                intent.putExtra("extra_data",cur_user);
                startActivity(intent);
            }
        });

        Button btqypay= (Button) findViewById(R.id.bt_queryBypay);
        btqypay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,payquery.class);
                intent.putExtra("extra_data",cur_user);
                startActivity(intent);
            }
        });

    }
}
