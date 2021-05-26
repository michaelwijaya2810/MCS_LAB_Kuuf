package com.mcs_project.mcs_lab_kuuf_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class ProductDetailActivity extends AppCompatActivity {

    TextView tvProductName, tvMinPlayer, tvMaxPlayer, tvProductPrice;
    Button btnMap, btnBuy;
    Product product;
    SmsManager smsManager;
    static int finish = 0;
    int sendSmsPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        tvProductName = findViewById(R.id.tvProductName);
        tvMinPlayer = findViewById(R.id.tvMinPlayer);
        tvMaxPlayer = findViewById(R.id.tvMaxPlayer);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        btnMap = findViewById(R.id.btnMap);
        btnBuy = findViewById(R.id.btnBuy);

        Intent intent = getIntent();
        final int productId = intent.getIntExtra(ProductAdapter.SEND_PRODUCT_ID, 0);
        final int userId = intent.getIntExtra(ProductAdapter.SEND_USER_ID, 0);

        DBHelper db = new DBHelper(this);
        ProductsDB productsDB = new ProductsDB(db);
        product = productsDB.getProductDetail(productId);

        UsersDB usersDB = new UsersDB(this);
        Users users = usersDB.getUserDetail(userId);

        tvProductName.setText(product.getProductName());
        tvMinPlayer.setText("Minimal Player: " + product.getMinPlayer());
        tvMaxPlayer.setText("Maximal Player: " + product.getMaxPlayer());
        tvProductPrice.setText("Price: " + product.getPrice());

        sendSmsPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        if(sendSmsPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProductDetailActivity.this,new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long wallet = users.getWallet();
                long price = product.getPrice();
                if(wallet < price){
                    Toast.makeText(getApplicationContext(), "Not enough balance",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Purchased", Toast.LENGTH_LONG).show();
                    wallet = wallet - price;
                    usersDB.updateUserWallet(userId, wallet);

                    String date = new SimpleDateFormat("dd MMMM yyyy").format(Calendar.getInstance().getTime());

                    TransactionsDB transactionsDB = new TransactionsDB(getApplicationContext());
                    transactionsDB.insertTransaction(userId, productId, date);

                    //SMS 5554
                    smsManager = SmsManager.getDefault();
//                    String phoneNumber = "5554";// Untuk memunculkan notif, gunakan yang ini.
                    String phoneNumber = users.getPhone();
                    String message = "Transaction Success";
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);


                }
                finish = 1;
                finish();
            }
        });

    }
}