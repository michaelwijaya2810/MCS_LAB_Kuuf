package com.mcs_project.mcs_lab_kuuf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    TextView tvProfileName, tvProfileGender, tvProfilePhone, tvProfileWallet, tvProfileDOB,
    tvProfileTopUp;
    RadioGroup rgTopUp;
    RadioButton rb250, rb500, rb1000;
    Button btnConfirm;
    EditText txtConfirmTopUp;
    private int topUp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileGender = findViewById(R.id.tvProfileGender);
        tvProfilePhone = findViewById(R.id.tvProfilePhone);
        tvProfileWallet = findViewById(R.id.tvProfileWallet);
        tvProfileDOB = findViewById(R.id.tvProfileDOB);

        Intent intent = getIntent();
        int userId = intent.getIntExtra(HomeActivity.SEND_USER_ID,0);

        UsersDB usersDB = new UsersDB(this);
        Users users = usersDB.getUserDetail(userId);

        tvProfileName.setText("Username: " + users.getUsername());
        tvProfileGender.setText("Gender: " + users.getGender());
        tvProfilePhone.setText("Phone: " + users.getPhone());
        tvProfileDOB.setText("Date of birth: " + users.getDateOfBirth());
        tvProfileWallet.setText("Wallet: " + users.getWallet());

        //TOP UP SECTION
        rgTopUp = findViewById(R.id.rgTopUp);
        tvProfileTopUp = findViewById(R.id.tvProfileTopUp);
        btnConfirm = findViewById(R.id.btnConfirm);
        txtConfirmTopUp = findViewById(R.id.txtConfirmTopUp);
        rb250 = findViewById(R.id.rb250);
        rb500 = findViewById(R.id.rb500);
        rb1000 = findViewById(R.id.rb1000);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = false;

                //VALIDATE RADIOBUTTON TOPUP
                if(!rb250.isChecked() && !rb500.isChecked() && !rb1000.isChecked()){
                    tvProfileTopUp.setError("Choose nominal");
                    Toast.makeText(getApplicationContext(),"Choose nominal", Toast.LENGTH_LONG).show();
                    flag = true;
                }else{
                    int radioId;
                    radioId = rgTopUp.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(radioId);
                    topUp = Integer.valueOf(rb.getText().toString());
                    tvProfileTopUp.setError(null);
                }

                //VALIDATE PASSWORD
                String password = txtConfirmTopUp.getText().toString();
                if(!password.equals(users.getPassword())){
                    txtConfirmTopUp.setError("Wrong Password");
                    flag = true;
                }
                if(password.isEmpty()){
                    txtConfirmTopUp.setError("Please input password");
                    flag = true;
                }

                //ALL VALIDATED
                if(!flag){
                    long updateWallet = users.getWallet() + topUp;
                    usersDB.updateUserWallet(userId, updateWallet);
                    Toast.makeText(getApplicationContext(),"Top up success", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }
}