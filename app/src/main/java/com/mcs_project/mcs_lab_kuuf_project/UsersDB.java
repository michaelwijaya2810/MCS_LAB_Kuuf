package com.mcs_project.mcs_lab_kuuf_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsersDB {
    private UserDBHelper dbHelper;

    public UsersDB(Context ctx){
        dbHelper = new UserDBHelper(ctx);
    }

    public void insertUsers(Users users){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserDBHelper.FIELD_USER_USERNAME, users.username);
        cv.put(UserDBHelper.FIELD_USER_PASSWORD, users.password);
        cv.put(UserDBHelper.FIELD_USER_PHONE, users.phone);
        cv.put(UserDBHelper.FIELD_USER_WALLET, users.wallet);
        cv.put(UserDBHelper.FIELD_USER_DATEBIRTH, users.dateOfBirth);
        cv.put(UserDBHelper.FIELD_USER_GENDER, users.gender);

        db.insert(UserDBHelper.TABLE_USERS, null, cv );

        db.close();
    }

    public boolean checkUsers(String username, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "username=? AND password=?";
        String[] selectionargs = {"" + username, "" + password};
        int check = 0;
        Cursor cursor = db.query(UserDBHelper.TABLE_USERS, null, selection, selectionargs,null, null ,null);

        if (cursor.moveToFirst()){
            return true;
        }
        else return false;
    }

}
