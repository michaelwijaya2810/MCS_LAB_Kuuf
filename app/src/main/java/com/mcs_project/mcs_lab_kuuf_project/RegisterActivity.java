package com.mcs_project.mcs_lab_kuuf_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    EditText ETUsername, ETPassword, ETPhone, ETConfirmationPass;
    Button BTNRegister,BTNDatebirth;
    DatePickerDialog datebirth;
    RadioGroup radiogroupgender;
    RadioButton radioButtongender;
    CheckBox checkagreement;
    UsersDB usersDB;
    String date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ETUsername = findViewById(R.id.edittextusername);
        ETPassword = findViewById(R.id.edittextpassword);
        ETPhone = findViewById(R.id.edittextphone);
        ETConfirmationPass = findViewById(R.id.edittextrepassword);
        BTNRegister = findViewById(R.id.regbtnreg);
        BTNDatebirth = findViewById(R.id.datebirthbutton);
        radiogroupgender = findViewById(R.id.radiogroup);
        checkagreement = findViewById(R.id.checkboxagreement);
        usersDB = new UsersDB(this);
        initDatePicker();

        BTNRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkusername() && checkpassword() &&  checkPhone() && checkconfirmationpass() && checkDateOfBirth() && checkGender() && checkTerms() ){
                    Users user = new Users();
                    user.setUsername(ETUsername.getText().toString());
                    user.setPassword(ETPassword.getText().toString());
                    user.setPhone(ETPhone.getText().toString());
                    user.setGender(radioButtongender.getText().toString());
                    user.setDateOfBirth(date);
                    usersDB.insertUsers(user);
                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
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

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String monthString = null;
                if(month == 1){
                    monthString="January";
                }
                if(month == 2){
                    monthString="February";
                }
                if(month == 3){
                    monthString="March";
                }
                if(month == 4){
                    monthString="April";
                }
                if(month == 5){
                    monthString="May";
                }
                if(month == 6){
                    monthString="June";
                }
                if(month == 7){
                    monthString="July";
                }
                if(month == 8){
                    monthString="August";
                }
                if(month == 9){
                    monthString="September";
                }
                if(month == 10){
                    monthString="October";
                }
                if(month == 11){
                    monthString="Nov";
                }
                if(month == 12){
                    monthString="Des";
                }
                date = day + "-" + monthString + "-" + year;
                BTNDatebirth.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datebirth = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datebirth.getDatePicker().setMaxDate(System.currentTimeMillis());
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
            ETUsername.setError("Username cannot be empty");
            return false;
        }
        else{
            ETUsername.setError("Username must be between 6 and 12 characters");
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
            ETPassword.setError("Password cannot be empty");
            return false;
        }
        else if (passtext.length()<8){
            ETPassword.setError("Password must be more than 8 characters");
            return false;
        }
        else if(!checknumeric && !checkalpha){
            ETPassword.setError("Password must be alphanumeric");
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
        else if(confirmpasstext.isEmpty()){
            ETConfirmationPass.setError("Confirmation Password cannot be empty");
            return false;
        }
        else {
            ETConfirmationPass.setError("Confirmation Password must be the same with Password");
            return false;
        }
    }

    private boolean checkDateOfBirth()
    {
        if (date.isEmpty())
        {
            BTNDatebirth.setError("DOB cannot be empty");
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
            ETPhone.setError("Phone Number cannot be emtpy");
            return false;
        }
        else if(!phonenum){
            ETPhone.setError("Phone Number must contain only numbers");
            return false;
        }
        else if(PhoneNumberlength<10 && PhoneNumberlength>12){
            ETPhone.setError("Phone Number must be between 10 to 12 digits");
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
            checkagreement.setError("User must agree the terms and conditions");
            return false;
        }

    }

    private boolean checkGender()
    {
        if (radioButtongender == null){
            Toast.makeText(this, "Gender must be selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    public void opendatepicker(View view) {
        datebirth.show();
    }
}