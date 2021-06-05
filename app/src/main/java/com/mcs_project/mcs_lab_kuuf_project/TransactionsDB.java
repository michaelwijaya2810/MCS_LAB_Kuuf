package com.mcs_project.mcs_lab_kuuf_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.net.IDN;
import java.util.Vector;

public class TransactionsDB{
    private DBHelper dbHelper;

    public TransactionsDB(Context ctx) {
        dbHelper = new DBHelper(ctx);
    }

    public void insertTransaction(int id, int productId, String transactionDate){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insert_query = "INSERT INTO " + DBHelper.TABLE_TRANSACTIONS +
                "(" +
                DBHelper.FIELD_USER_ID + ", " +
                DBHelper.FIELD_PRODUCT_ID + ", " +
                DBHelper.FIELD_TRANS_DATE + ")" +
                "VALUES(" + id + ", " + productId + ", '" + transactionDate + "')";

        db.execSQL(insert_query);
        db.close();
        dbHelper.close();
    }

    public void deleteTransaction(int transId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String delete_query = "DELETE FROM " + DBHelper.TABLE_TRANSACTIONS +
                " WHERE " + DBHelper.FIELD_TRANS_ID + " = " + transId;
        db.execSQL(delete_query);
        db.close();
        dbHelper.close();
    }

    public Vector<Transactions> getTransaction(int userId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String get_transaction_query = "SELECT * FROM " + DBHelper.TABLE_TRANSACTIONS +
                " WHERE " + DBHelper.FIELD_USER_ID + " = " + userId;
        Cursor cursor = db.rawQuery(get_transaction_query, null);

        Vector<Transactions> vecTrans = new Vector<>();

        if(cursor.moveToFirst()){
            do{
                Integer transId = cursor.getInt(0);
                Integer id = cursor.getInt(1);
                Integer productId = cursor.getInt(2);
                String transactionDate = cursor.getString(3);
                vecTrans.add(new Transactions(transId, id, productId, transactionDate));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        dbHelper.close();
        return vecTrans;
    }
}
