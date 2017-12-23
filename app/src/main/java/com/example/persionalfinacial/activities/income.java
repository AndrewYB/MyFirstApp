package com.example.persionalfinacial.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.persionalfinacial.R;
import com.example.persionalfinacial.model.m_Income;
import com.example.persionalfinacial.sql.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;

public class income extends AppCompatActivity {
    private TextView tv_income;
    private Button  bt_addincome;
    private m_Income m_income;
    private String cur_user;
//    private Date dt;
    private MyDatabaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        Intent intent=getIntent();
        String data=intent.getStringExtra("extra_data");
        cur_user=data;
        dbhelper=new MyDatabaseHelper(this);
        m_income=new m_Income();
        bt_addincome= (Button) findViewById(R.id.bt_addincome);
        bt_addincome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tv_income= (TextView) findViewById(R.id.et_income);
                String s_income=tv_income.getText().toString();
//                dt=new Date();
//                String cur_dt=dt.toString();

                SimpleDateFormat formatter = new SimpleDateFormat   ("yyyy-MM-dd");
                Date time=new Date(System.currentTimeMillis());
                String cur_dt = formatter.format(time);



                m_income.setName(cur_user);
                m_income.setIncome(s_income);
                m_income.setDate(cur_dt);
                dbhelper.addIncome(m_income);
                Toast.makeText(income.this, "添加收入成功！", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
