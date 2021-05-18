package com.mcs_project.mcs_lab_kuuf_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "UserDB";
    private static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "Users";
    public static final String FIELD_USER_ID = "id";
    public static final String FIELD_USER_USERNAME = "username";
    public static final String FIELD_USER_PASSWORD = "password";
    public static final String FIELD_USER_PHONE = "phone";
    public static final String FIELD_USER_GENDER = "gender";
    public static final String FIELD_USER_WALLET = "wallet";
    public static final String FIELD_USER_DATEBIRTH = "datebirth";

    private static final String CREATE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +" (" +
            FIELD_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_USER_USERNAME + " TEXT," +
            FIELD_USER_PASSWORD + " TEXT," +
            FIELD_USER_PHONE + " INTEGER," +
            FIELD_USER_GENDER + " TEXT," +
            FIELD_USER_WALLET + " FLOAT,"+
            FIELD_USER_DATEBIRTH + " DATE)";

    private static final String DROP_PRODUCTS = "DROP TABLE IF EXISTS " + TABLE_USERS;

    public UserDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL(DROP_PRODUCTS);
        onCreate(db);
    }
}