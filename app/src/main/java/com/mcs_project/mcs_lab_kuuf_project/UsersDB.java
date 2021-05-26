package com.mcs_project.mcs_lab_kuuf_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsersDB {
    private DBHelper dbHelper;

    public UsersDB(Context ctx){
        dbHelper = new DBHelper(ctx);
    }

    public void insertUsers(Users users){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.FIELD_USER_USERNAME, users.username);
        cv.put(DBHelper.FIELD_USER_PASSWORD, users.password);
        cv.put(DBHelper.FIELD_USER_PHONE, users.phone);
        cv.put(DBHelper.FIELD_USER_WALLET, users.wallet);
        cv.put(DBHelper.FIELD_USER_DATEBIRTH, users.dateOfBirth);
        cv.put(DBHelper.FIELD_USER_GENDER, users.gender);

        db.insert(DBHelper.TABLE_USERS, null, cv );

        db.close();
    }

    public boolean checkUsers(String username, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "username=? AND password=?";
        String[] selectionargs = {"" + username, "" + password};
        int check = 0;
        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, selection, selectionargs,null, null ,null);

        if (cursor.moveToFirst()){
            return true;
        }
        else return false;
    }

    public Integer getId(String username){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String get_user_id = "SELECT " + DBHelper.FIELD_USER_ID + " FROM " + DBHelper.TABLE_USERS +
                " WHERE " + DBHelper.FIELD_USER_USERNAME + " = " + "'" + username + "'";

        Cursor cursor = db.rawQuery(get_user_id, null);

        if(cursor.moveToFirst()){
            Integer id = cursor.getInt(0);

            db.close();
            cursor.close();
            return id;
        }

        db.close();
        cursor.close();
        return null;
    }

    public Users getUserDetail(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String get_user_detail = "SELECT * FROM " + DBHelper.TABLE_USERS +
                " WHERE " +  DBHelper.FIELD_USER_ID + " = " + id;

        Cursor cursor = db.rawQuery(get_user_detail,null);

        if(cursor.moveToFirst()){
            Users user = new Users();
            user.id =cursor.getInt(0);
            user.username = cursor.getString(1);
            user.password = cursor.getString(2);
            user.phone = cursor.getString(3);
            user.gender = cursor.getString(4);
            user.wallet = cursor.getLong(5);
            user.dateOfBirth = cursor.getString(6);

            cursor.close();
            db.close();
            return user;
        }
        cursor.close();
        db.close();
        return null;
    }

    public void updateUserWallet(int id, long wallet){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String update_query = "UPDATE " + DBHelper.TABLE_USERS +
                " SET " + DBHelper.FIELD_USER_WALLET + " = " + wallet +
                " WHERE " + DBHelper.FIELD_USER_ID  + " = " + id;
        db.execSQL(update_query);
        db.close();
        dbHelper.close();
    }

}
