package com.mcs_project.mcs_lab_kuuf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView ETUsername, ETPassword,TVError;
    Button BTNLogin, BTNRegister;
    UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETUsername =findViewById(R.id.edittextusernamelogin);
        ETPassword = findViewById(R.id.edittextpasswordlogin);
        BTNLogin = findViewById(R.id.btnlogin);
        BTNRegister = findViewById(R.id.btnregister);
        TVError = findViewById(R.id.txterrormessage);
        usersDB = new UsersDB(this);

        BTNRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegisterActivity();
            }
        });

        BTNLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkusername() && checkpassword()){
                    String username = ETUsername.getText().toString();
                    String password = ETPassword.getText().toString();
                    if(usersDB.checkUsers(username, password)){
                        TVError.setText("correct");
                        //open mainactivity
                    }
                    else{
                        TVError.setText("Username or Password are wrong");
                    }

                }
            }
        });

    }

    private void OpenRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private Boolean checkusername(){
        if(ETUsername.getText().toString().isEmpty()){
            TVError.setText("Username cannot be empty");
            return false;
        }
        else{
            return true;
        }
    }

    private Boolean checkpassword(){
        if(ETPassword.getText().toString().isEmpty()){
            TVError.setText("Password cannot be empty");
            return false;
        }
        else{
            return true;
        }
    }
}