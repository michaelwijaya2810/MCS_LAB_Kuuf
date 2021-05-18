package com.mcs_project.mcs_lab_kuuf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.os.Bundle;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    TextView ETUsername, ETPassword, ETPhone, ETConfirmationPass, TVerror;
    Button BTNRegister;
    DatePicker datebirth;
    RadioGroup radiogroupgender;
    RadioButton radioButtongender;
    CheckBox checkagreement;
    UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ETUsername = findViewById(R.id.edittextusername);
        ETPassword = findViewById(R.id.edittextpassword);
        ETPhone = findViewById(R.id.edittextphone);
        ETConfirmationPass = findViewById(R.id.edittextrepassword);
        BTNRegister = findViewById(R.id.regbtnreg);
        datebirth = findViewById(R.id.datebirth);
        radiogroupgender = findViewById(R.id.radiogroup);
        checkagreement = findViewById(R.id.checkboxagreement);
        TVerror = findViewById(R.id.texterrors);
        usersDB = new UsersDB(this);

        BTNRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkusername() && checkpassword() &&  checkPhone() && checkconfirmationpass() && checkDateOfBirth() && checkGender() && checkTerms() ){
                    Users user = new Users();
                    user.username = ETUsername.getText().toString();
                    user.password = ETPassword.getText().toString();
                    user.phone = ETPhone.getText().toString();
                    user.gender = radioButtongender.getText().toString();
                    user.dateOfBirth = datebirth.getDayOfMonth() + "-" + (datebirth.getMonth() + 1) + "-" + datebirth.getYear();
                    TVerror.setText("Register Succeed");
                    usersDB.insertUsers(user);
                    OpenRegisterActivity();
                }
            }
        });

        radiogroupgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                radioButtongender = findViewById(id);
            }
        });
    }

    private void OpenRegisterActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    private Boolean checkusername(){
        String usertext = ETUsername.getText().toString();
        int usernamelength = ETUsername.getText().toString().length();
        if(usernamelength >=6 && usernamelength<=12 ){
            return true;
        }
        else if(usertext.isEmpty()){
            TVerror.setText("Username cannot be empty");
            return false;
        }
        else{
            TVerror.setText("Username must be between 6 and 12 characters");
            return false;
        }
    }

    private Boolean checkpassword(){
        String passtext = ETPassword.getText().toString();
        int passLength = ETPassword.getText().toString().length();
        boolean checknumeric = false , checkalpha = false;
        char[] passtextarray = new char[passLength];
        for(int i=0 ; i<passLength ; i++){
            passtextarray[i] = passtext.charAt(i);
        }

        if(passLength > 8){
            for (char cekchar:passtextarray) {
                if(checknumeric && checkalpha) {
                    break;
                }
                else{
                    if (cekchar >= '0' && cekchar <= '9') {
                        checknumeric = true;
                    }
                    if (cekchar >= 'A' && cekchar <= 'Z' || cekchar >= 'a' && cekchar <= 'z') {
                        checkalpha = true;
                    }
                }
            }
        }
        if(checknumeric && checkalpha){
            return true;
        }
        else if(passtext.isEmpty()){
            TVerror.setText("Password cannot be empty");
            return false;
        }
        else if (passtext.length()<8){
            TVerror.setText("Password must be more than 8 characters");
            return false;
        }
        else if(!checknumeric && !checkalpha){
            TVerror.setText("Password must be alphanumeric");
            return false;
        }

        else{
            return false;
        }
    }

    private boolean checkconfirmationpass(){
        String passtext = ETPassword.getText().toString();
        String confirmpasstext = ETConfirmationPass.getText().toString();
        if(passtext.equals(confirmpasstext)){
            return true;
        }
        else if(passtext.isEmpty()){
            TVerror.setText("Confirmation Password cannot be empty");
            return false;
        }
        else {
            TVerror.setText("Confirmation Password must be the same with Password");
            return false;
        }
    }

    private boolean checkDateOfBirth()
    {
        Date dateOfBirth = new Date(datebirth.getYear(), datebirth.getMonth(), datebirth.getDayOfMonth());
        if (!datebirth.isEnabled())
        {
            TVerror.setText("DOB cannot be in the future");
            return false;
        }
        else{
            return true;
        }
    }

    private boolean checkPhone()
    {
        String PhoneNumber = ETPhone.getText().toString();
        int PhoneNumberlength = ETPhone.getText().toString().length();
        boolean phonenum = false, phonedigit =false;
        char[] PhoneNumberArray = new char[PhoneNumberlength];
        for(int i=0 ; i<PhoneNumberlength ; i++){
            PhoneNumberArray[i] = PhoneNumber.charAt(i);
        }

        if(PhoneNumberlength >=10 && PhoneNumberlength <=12){
            phonedigit = true;
            for (char cekphone:PhoneNumberArray) {
                if(phonenum) {
                    break;
                }
                else{
                    if (cekphone >= '0' && cekphone <= '9') {
                        phonenum = true;
                    }
                }
            }
        }
        if(phonenum){
            return true;
        }
        else if(PhoneNumber.isEmpty()){
            TVerror.setText("Phone Number cannot be emtpy");
            return false;
        }
        else if(!phonenum){
            TVerror.setText("Phone Number must contain only numbers");
            return false;
        }
        else if(PhoneNumberlength<10 && PhoneNumberlength>12){
            TVerror.setText("Phone Number must be between 10 to 12 digits");
            return false;
        }
        else {
            return false;
        }
    }

    private boolean checkTerms()
    {
        if (checkagreement.isChecked()){
            return true;
        }
        else{
            TVerror.setText("User must agree the terms and conditions");
            return false;
        }

    }

    private boolean checkGender()
    {
        if (radioButtongender == null){
            TVerror.setText("Gender must be selected");
            return false;
        }
        else{
            return true;
        }
    }
}