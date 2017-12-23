package com.example.persionalfinacial.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.persionalfinacial.R;
import com.example.persionalfinacial.model.m_Income;
import com.example.persionalfinacial.model.User;
import com.example.persionalfinacial.model.m_pay;

import java.lang.reflect.Array;

/**
 * Created by GK on 2017/12/14.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "finacial.db";

    // user table name
    private static final String TABLE_USER = "tb_user";

    //income table name
    private static final String TABLE_INCOME = "tb_income";

    //pay table name
    private static final String TABLE_PAY = "tb_pay";

    //user tbl col names
    private static final String COLUMN_USER_ID = "user_id";//Add

    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String COLUMN_INCOME_ID = "income_id";//Add

    private static final String COLUMN_INCOME_USERNAME = "income_username";
    private static final String COLUMN_INCOME_INCOME = "income_income";
    private static final String COLUMN_INCOME_DATE = "income_date";

    private static final String COLUMN_PAY_ID = "pay_id";//Add

    private static final String COLUMN_PAY_USERNAME = "pay_username";
    private static final String COLUMN_PAY_PAY = "pay_pay";
    private static final String COLUMN_PAY_DATE = "pay_date";

    //create table sql

    public static final String CREATE_USER = "create table " + TABLE_USER + "("
            + COLUMN_USER_ID + " integer primary key autoincrement, "//Add
            + COLUMN_USER_NAME + " text ,"
            + COLUMN_USER_PASSWORD + " text)";

    public static final String CREATE_INCOME = "create table " + TABLE_INCOME + "("
            + COLUMN_INCOME_ID + " integer primary key autoincrement,"//Add
            + COLUMN_INCOME_USERNAME + " text ,"
            + COLUMN_INCOME_INCOME + " text,"
            + COLUMN_INCOME_DATE + " text)";
    public static final String CREATE_PAY = "create table " + TABLE_PAY + "("
            + COLUMN_PAY_ID + " integer primary key autoincrement, "//Add
            + COLUMN_PAY_USERNAME + " text ,"
            + COLUMN_PAY_PAY + " text,"
            + COLUMN_PAY_DATE + " text)";

    //drop table if needed
    private String DROP_USER = "drop table if exists " + TABLE_USER;
    private String DROP_INCOME = "drop table if exists" + TABLE_INCOME;
    private String DROP_PAY = "drop table if exists" + TABLE_PAY;

    private Context mContext;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_INCOME);
        db.execSQL(CREATE_PAY);
        Toast.makeText(mContext, "表成功建立", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER);
        db.execSQL(DROP_INCOME);
        db.execSQL(DROP_PAY);
        // create tbl again
        onCreate(db);
    }

    public boolean isExistName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_USER + " where " + COLUMN_USER_NAME + "=?", new String[]{name});
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0 ? true : false;
    }

    public boolean isPass(String name, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COLUMN_USER_NAME + " = ?" + " and " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {name, pwd};
        Cursor cursor = db.query(TABLE_USER, null, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;

    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        //inserting now
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addIncome(m_Income mIncome) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INCOME_USERNAME, mIncome.getName());
        values.put(COLUMN_INCOME_INCOME, mIncome.getIncome());
        values.put(COLUMN_INCOME_DATE, mIncome.getDate());

        //inserting now
        db.insert(TABLE_INCOME, null, values);
        db.close();
    }

    public void addPay(m_pay mPay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAY_USERNAME, mPay.getName());
        values.put(COLUMN_PAY_PAY, mPay.getPay());
        values.put(COLUMN_PAY_DATE, mPay.getDate());

        db.insert(TABLE_PAY, null, values);
        db.close();
    }

    public ArrayAdapter<String> selectIncome(String username) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1);
        String selection = "select * from " + TABLE_INCOME + " where " + COLUMN_INCOME_USERNAME + " ='" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selection, null);
            if (cursor.moveToFirst()) {
                do {
                    String print = "";
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_USERNAME));
                    String type = "收入";
                    String amount = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_INCOME));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DATE));
                    print += name;
                    print += " ";
                    print += type;
                    print += amount;
                    print += "元";
                    print += "  ";
                    print += date;

                    adapter.add(print);
                } while (cursor.moveToNext());
                Toast.makeText(mContext, "查询成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "查询无记录", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
        }
        return adapter;
    }

    public ArrayAdapter<String> selectPay(String username){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1);
        String selection = "select * from " + TABLE_PAY + " where " + COLUMN_PAY_USERNAME + " ='" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selection, null);
            if (cursor.moveToFirst()) {
                do {
                    String print = "";
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_PAY_USERNAME));
                    String type = "支出";
                    String amount = cursor.getString(cursor.getColumnIndex(COLUMN_PAY_PAY));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_PAY_DATE));
                    print += name;
                    print += " ";
                    print += type;
                    print += amount;
                    print += "元";
                    print += "  ";
                    print += date;
                    adapter.add(print);
                } while (cursor.moveToNext());
                Toast.makeText(mContext, "查询成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "查询无记录", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
        }
        return adapter;
    }

    public ArrayAdapter<String> selectDate(String data){
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1);
        String selection="";
        String selection1 ="select * from " + TABLE_PAY +" where "+ COLUMN_PAY_DATE + " ='" +
                data + "'";
        String selection2 ="select * from " + TABLE_INCOME + " where "+ COLUMN_INCOME_DATE + " ='" +
                data + "'";

        SQLiteDatabase db=this.getWritableDatabase();
        try {
            int mark=0;
            Cursor cursor=db.rawQuery(selection1,null);
            if(cursor.moveToFirst()){
                do{
                        String print="";
                        String name = cursor.getString(cursor.getColumnIndex(COLUMN_PAY_USERNAME));
                        String type = "支出";
                        String amount=cursor.getString(cursor.getColumnIndex(COLUMN_PAY_PAY));
                        String date=cursor.getString(cursor.getColumnIndex(COLUMN_PAY_DATE));
                        print += name;
                        print += " ";
                        print += type;
                        print += amount;
                        print += "元";
                        print += "  ";
                        print += date;
                        adapter.add(print);
                }while (cursor.moveToNext());
            }else mark++;

            cursor=db.rawQuery(selection2, null);
            if(cursor.moveToFirst()){
                do{
                    String print="";
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_USERNAME));
                    String type = "收入";
                    String amount=cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_INCOME));
                    String date=cursor.getString(cursor.getColumnIndex(COLUMN_INCOME_DATE));
                    print += name;
                    print += " ";
                    print += type;
                    print += amount;
                    print += "元";
                    print += "  ";
                    print += date;

                    adapter.add(print);

                }while(cursor.moveToNext());
            }else mark++;
            cursor.close();
            if(mark==2){
                Toast.makeText(mContext, "查询无数据", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(mContext, "查询成功", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(mContext, "查询失败", Toast.LENGTH_SHORT).show();
        }
        return adapter;
    }



}
