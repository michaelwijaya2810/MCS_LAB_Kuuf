package com.mcs_project.mcs_lab_kuuf_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class StoreActivity extends AppCompatActivity {

    Vector<Product> vecProduct = new Vector<>();
    RecyclerView rvStore;
    ProductsDB productsDB;
    DBHelper dbHelper;
    private RequestQueue mQueue;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeActivity.SEND_USER_ID, 0);

        dbHelper = new DBHelper(this);
        productsDB = new ProductsDB(dbHelper);
        vecProduct = productsDB.getProducts();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getRvData();
        if(ProductDetailActivity.finish == 1){
            ProductDetailActivity.finish = 0;
            finish();
        }
    }

    public void getRvData(){
        rvStore = findViewById(R.id.rvStore);
        vecProduct = productsDB.getProducts();
        ProductAdapter productAdapter = new ProductAdapter(this);
        productAdapter.setUserId(userId);
        productAdapter.setVecProduct(vecProduct);

        rvStore.setAdapter(productAdapter);
        rvStore.setLayoutManager(new GridLayoutManager(this, 1));
    }

}