package com.example.persionalfinacial.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.persionalfinacial.R;
import com.example.persionalfinacial.sql.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class datequery extends AppCompatActivity {

    private String data=""; //保存日期的信息。
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datequery);
        dbHelper=new MyDatabaseHelper(this);
        //下面要做的就是获取DatePicker的时间值。
        DatePicker dp= (DatePicker) findViewById(R.id.datePicker);
        dp.init(2015,6,7,new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker datePicker, int year,
                                      int monthOfYear, int dayOfMonth) {
                //获取一个日历对象。
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd");
                 data=format.format(calendar.getTime());
                Toast.makeText(datequery.this,
                        format.format(calendar.getTime()), Toast.LENGTH_SHORT)
                        .show();

            }
        });

        Button btDateQuery= (Button) findViewById(R.id.bt_datequery);
        btDateQuery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ListView listView= (ListView) findViewById(R.id.list_date);
                ArrayAdapter<String> adapter=dbHelper.selectDate(data);
                listView.setAdapter(adapter);
            }
        });
    }
}
