package com.mcs_project.mcs_lab_kuuf_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class HomeActivity extends AppCompatActivity {
    Users user;
    Vector<Transactions> vecTrans = new Vector<>();
    Vector<Product> vecProduct = new Vector<>();
    DBHelper dbHelper;
    ProductsDB productsDB;
    RequestQueue mQueue;
    TextView tvGreet, tvWallet, tvEmpty;
    RecyclerView rvTrans;
    int userId;
    public static final String SEND_KEY = "com.example.application.MCS_LAB_Kuuf_Project.SEND_KEY";
    public static final String SEND_USER_ID = "com.example.application.MCS_LAB_Kuuf_Project.SEND_USER_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getPermission();

        //GET USER DATA FROM LOGIN ACTIVITY
        Intent intent = getIntent();
        userId = intent.getIntExtra(SEND_KEY,0);
        UsersDB db = new UsersDB(this);
        user = db.getUserDetail(userId);

        tvGreet = findViewById(R.id.tvGreet);
        tvGreet.setText("Welcome " + user.getUsername());
        tvEmpty = findViewById(R.id.tvEmpty);

        //INSERT PRODUCT TO DB
        mQueue = Volley.newRequestQueue(this);
        dbHelper = new DBHelper(this);
        productsDB = new ProductsDB(dbHelper);
        vecProduct = productsDB.getProducts();
        if(vecProduct.isEmpty()) {
            jsonParse();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //GET USER DETAIL
        UsersDB db = new UsersDB(this);
        user = db.getUserDetail(userId);

        //UPDATE WALLET
        String balance = "Your balance: Rp. " + user.getWallet();
        tvWallet = findViewById(R.id.tvWallet);
        tvWallet.setText(balance);
        if(vecTrans.isEmpty()){
            tvEmpty.setVisibility(View.VISIBLE);
        }else{
            tvEmpty.setVisibility(View.GONE);
        }

        //GET RECYCLER VIEW DATA
        getRvData();

    }

    public void getRvData(){
        rvTrans = findViewById(R.id.rvTransaction);
        TransactionsDB transactionsDB = new TransactionsDB(this);
        vecTrans = transactionsDB.getTransaction(userId);

        TransactionAdapter transactionAdapter = new TransactionAdapter(this);
        transactionAdapter.setTransVec(vecTrans);

        rvTrans.setAdapter(transactionAdapter);
        rvTrans.setLayoutManager(new GridLayoutManager(this, 1));

    }

    //MENU ITEM SECTION
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.storeMenu){
            Intent intent = new Intent(this, StoreActivity.class);
            intent.putExtra(SEND_USER_ID,userId);
            startActivity(intent);
        } else if (item.getItemId() == R.id.profileMenu) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(SEND_USER_ID,userId);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logoutMenu) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == R.id.homeMenu){
            startActivity(getIntent());
            overridePendingTransition(0,0);
            finish();
            overridePendingTransition(0,0);
        }
        return true;
    }

    public void getPermission(){
        int sendSmsPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        if(sendSmsPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        int receiveSmsPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS);
        if(receiveSmsPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }

        int readSmsPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS);
        if(readSmsPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS}, 1);
        }
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