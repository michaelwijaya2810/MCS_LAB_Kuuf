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

        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        userId = intent.getIntExtra(HomeActivity.SEND_USER_ID, 0);

        dbHelper = new DBHelper(this);
        productsDB = new ProductsDB(dbHelper);
        vecProduct = productsDB.getProducts();

        if(vecProduct.isEmpty()) {
            jsonParse();
            Intent refresh = getIntent();
            finish();
            startActivity(refresh);
        }

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

    public void jsonParse(){
        String url = "https://api.jsonbin.io/b/5eb51c6947a2266b1474d701/7";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            for(int i=0 ; i<jsonArray.length() ; i++){
                                JSONObject products = jsonArray.getJSONObject(i);
                                String productName = products.getString("name");
                                int minPlayer = products.getInt("min_player");
                                int maxPlayer = products.getInt("max_player");
                                long price = products.getLong("price");
                                String createdAt = products.getString("created_at");
                                double latitude = Double.parseDouble(products.getString("latitude"));
                                double longitude = Double.parseDouble(products.getString("longitude"));
                                productsDB.insertProduct(productName, minPlayer, maxPlayer, price, createdAt, latitude, longitude);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}