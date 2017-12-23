package com.example.persionalfinacial.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.persionalfinacial.R;
import com.example.persionalfinacial.sql.MyDatabaseHelper;

public class payquery extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private String cur_user;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payquery);
        Intent intent=getIntent();
        String data=intent.getStringExtra("extra_data");
        cur_user=data;
        mContext=this;

        dbHelper=new MyDatabaseHelper(this);
        ArrayAdapter<String> adapter=dbHelper.selectPay(cur_user);
        ListView listView= (ListView) findViewById(R.id.list_pay);
        listView.setAdapter(adapter);

    }
}
