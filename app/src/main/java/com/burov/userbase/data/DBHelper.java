package com.burov.userbase.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
//задаем поля
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "usersDB";
    public static final String TABLE_USERS = "users";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_CITY = "city";
    public static final String KEY_BALANCE = "balance";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //создаем базу данных
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USERS + "(" +
                KEY_ID + " integer primary key," +
                KEY_NAME + " text," +
                KEY_SURNAME + " text," +
                KEY_EMAIL + " text," +
                KEY_PHONE + " text," +
                KEY_COUNTRY + " text," +
                KEY_CITY + " text," +
                KEY_BALANCE + " text" + ")");
    }

    @Override
   // в случае изменения базы данных
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS);
        onCreate(db);
    }
}
