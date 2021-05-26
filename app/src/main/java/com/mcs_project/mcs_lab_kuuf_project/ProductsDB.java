package com.mcs_project.mcs_lab_kuuf_project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Vector;

public class ProductsDB {
    private DBHelper dbHelper;

    public ProductsDB(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insertProduct(String name, int minPlayer, int maxPlayer, long price, String createdAt, double latitude, double longitude){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insert_query = "INSERT INTO " + DBHelper.TABLE_PRODUCTS +
                "(" +
                DBHelper.FIELD_PRODUCT_NAME + ", " +
                DBHelper.FIELD_PRODUCT_MINPLAYER + ", " +
                DBHelper.FIELD_PRODUCT_MAXPLAYER + ", " +
                DBHelper.FIELD_PRODUCT_PRICE + ", " +
                DBHelper.FIELD_PRODUCT_CREATEDAT + ", " +
                DBHelper.FIELD_PRODUCT_LATITUDE + ", " +
                DBHelper.FIELD_PRODUCT_LONGITUDE + ")" +
                "VALUES('" + name + "', " + minPlayer + ", " + maxPlayer + ", " + price + ", " + "'" + createdAt + "', " + latitude + ", " + longitude + ")";

        db.execSQL(insert_query);
        db.close();
        dbHelper.close();
    }

    public Vector<Product> getProducts(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String get_product_query = "SELECT * FROM " + DBHelper.TABLE_PRODUCTS;
        Cursor cursor = db.rawQuery(get_product_query, null);

        Vector<Product> vecProduct = new Vector<>();

        if(cursor.moveToFirst()){
            do{
                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                int min = cursor.getInt(2);
                int max = cursor.getInt(3);
                long price = cursor.getLong(4);
                String createdAt = cursor.getString(5);
                double latitude = cursor.getDouble(6);
                double longitude = cursor.getDouble(7);
                Product product = new Product(id, name, min, max, price, createdAt, latitude, longitude);
                vecProduct.add(product);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return  vecProduct;
    }

    public Product getProductDetail(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String get_product_detail_query = "SELECT * FROM " + DBHelper.TABLE_PRODUCTS +
                " WHERE " + DBHelper.FIELD_PRODUCT_ID + " = " + id;
        Cursor cursor = db.rawQuery(get_product_detail_query, null);
        if(cursor.moveToFirst()){
            Integer productId = cursor.getInt(0);
            String name = cursor.getString(1);
            int min = cursor.getInt(2);
            int max = cursor.getInt(3);
            long price = cursor.getLong(4);
            String createdAt = cursor.getString(5);
            double latitude = cursor.getDouble(6);
            double longitude = cursor.getDouble(7);
            Product product = new Product(productId, name, min, max, price, createdAt, latitude, longitude);
            cursor.close();
            db.close();
            dbHelper.close();
            return product;
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return null;
    }

}
