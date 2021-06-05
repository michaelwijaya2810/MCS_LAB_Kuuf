package com.mcs_project.mcs_lab_kuuf_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "kuufDB";
    private static final int DB_VERSION = 1;

    //CREATE USER TABLE
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

    //CREATE PRODUCT TABLE
    public static final String TABLE_PRODUCTS = "Products";
    public static final String FIELD_PRODUCT_ID = "productId";
    public static final String FIELD_PRODUCT_NAME = "productName";
    public static final String FIELD_PRODUCT_MINPLAYER = "minPLayer";
    public static final String FIELD_PRODUCT_MAXPLAYER = "maxPLayer";
    public static final String FIELD_PRODUCT_PRICE = "price";
    public static final String FIELD_PRODUCT_CREATEDAT = "createdAt";
    public static final String FIELD_PRODUCT_LATITUDE = "latitude";
    public static final String FIELD_PRODUCT_LONGITUDE = "longitude";

    private static final String CREATE_PRODUCTS = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTS +" (" +
            FIELD_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_PRODUCT_NAME + " TEXT," +
            FIELD_PRODUCT_MINPLAYER + " INTEGER," +
            FIELD_PRODUCT_MAXPLAYER + " INTEGER," +
            FIELD_PRODUCT_PRICE + " LONG," +
            FIELD_PRODUCT_CREATEDAT + " DATE,"+
            FIELD_PRODUCT_LATITUDE + " DOUBLE,"+
            FIELD_PRODUCT_LONGITUDE + " DOUBLE)";

    //CREATE TRANSACTION TABLE
    public static final String TABLE_TRANSACTIONS = "Transactions";
    public static final String FIELD_TRANS_ID = "transId";
    public static final String FIELD_TRANS_DATE = "transactionDate";

    private static final String CREATE_TRANSACTIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_TRANSACTIONS + " (" +
            FIELD_TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FIELD_USER_ID + " INTEGER," +
            FIELD_PRODUCT_ID + " INTEGER," +
            FIELD_TRANS_DATE + " DATE," +
            "FOREIGN KEY(" + FIELD_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + FIELD_USER_ID + "), " +
            "FOREIGN KEY(" + FIELD_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + FIELD_PRODUCT_ID + "))";

    private static final String DROP_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;
    private static final String DROP_PRODUCTS = "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;
    private static final String DROP_TRANSACTIONS = "DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_PRODUCTS);
        db.execSQL(CREATE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL(DROP_USERS);
        db.execSQL(DROP_PRODUCTS);
        db.execSQL(DROP_TRANSACTIONS);
        onCreate(db);
    }
}