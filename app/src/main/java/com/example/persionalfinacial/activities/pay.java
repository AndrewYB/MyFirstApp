package com.example.persionalfinacial.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.persionalfinacial.R;
import com.example.persionalfinacial.model.m_pay;
import com.example.persionalfinacial.sql.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
public class pay extends AppCompatActivity {
    private TextView tv_pay;
    private Button bt_addpay;
    private m_pay m_pay;
    private  String cur_user;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent=getIntent();
        String data=intent.getStringExtra("extra_data");
        cur_user=data;
        dbHelper=new MyDatabaseHelper(this);
        m_pay=new m_pay();
        bt_addpay= (Button) findViewById(R.id.bt_addpay);
        bt_addpay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tv_pay= (TextView) findViewById(R.id.et_pay);
                String s_pay=tv_pay.getText().toString();

                SimpleDateFormat formatter = new SimpleDateFormat   ("yyyy-MM-dd");
                Date time=new Date(System.currentTimeMillis());
                String cur_dt = formatter.format(time);

                m_pay.setName(cur_user);
                m_pay.setPay(s_pay);
                m_pay.setDate(cur_dt);

                dbHelper.addPay(m_pay);
                Toast.makeText(pay.this, "添加支付成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
