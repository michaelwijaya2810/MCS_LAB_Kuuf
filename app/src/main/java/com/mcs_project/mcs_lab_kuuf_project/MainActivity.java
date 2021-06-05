package com.mcs_project.mcs_lab_kuuf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ETUsername, ETPassword;
    Button BTNLogin;
    TextView BTNRegister;
    UsersDB usersDB;
    public static final String SEND_KEY = "com.example.application.MCS_LAB_Kuuf_Project.SEND_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETUsername =findViewById(R.id.edittextusernamelogin);
        ETPassword = findViewById(R.id.edittextpasswordlogin);
        BTNLogin = findViewById(R.id.btnlogin);
        BTNRegister = findViewById(R.id.btnregister);
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
                    Integer id = usersDB.getId(username);
                    if(usersDB.checkUsers(username, password)){
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        intent.putExtra(SEND_KEY,id);
                        Toast.makeText(MainActivity.this, "Login Successful! Welcome, " + username, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        //open mainactivity
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Login failed, please check your credentials!", Toast.LENGTH_SHORT).show();
                        ETUsername.setError("Username is wrong!");
                        ETPassword.setError("Password does not match!");
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
            ETUsername.setError("Username cannot be empty");
            return false;
        }
        else{
            return true;
        }
    }

    private Boolean checkpassword(){
        if(ETPassword.getText().toString().isEmpty()){
            ETPassword.setError("Password cannot be empty");
            return false;
        }
        else{
            return true;
        }
    }
}