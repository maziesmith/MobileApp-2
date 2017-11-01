package com.example.devendra.sharing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Devendra on 3/15/2017.
 */

public class Register extends Activity{

    DatabaseHelper dbs = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

    }

    private boolean validateEmail(String email){
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password){
        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void onRegisterClick(View v){
        if(v.getId() == R.id.btnSign){

            EditText name = (EditText)findViewById(R.id.edtName);
            EditText uName = (EditText)findViewById(R.id.edtUsername);
            EditText pass = (EditText)findViewById(R.id.edtPass);
            EditText confirmPass = (EditText)findViewById(R.id.edtConfirmPass);
            EditText address = (EditText)findViewById(R.id.edtAddress);
            EditText email = (EditText)findViewById(R.id.edtEmail);
            EditText phone = (EditText)findViewById(R.id.edtPhone);


            String nameStr = name.getText().toString();
            String unameStr = uName.getText().toString();
            String passStr = pass.getText().toString();
            String confirmPassStr = confirmPass.getText().toString();
            String addressStr = address.getText().toString();
            String emailStr = email.getText().toString();
            String phoneStr = phone.getText().toString();



            if(!passStr.equals(confirmPassStr)){

                Toast passPop = Toast.makeText(this, "Passwords don't match!",Toast.LENGTH_SHORT);
                passPop.show();
            }
            else if(nameStr.trim().length()<=0){

                Toast passPop = Toast.makeText(this, "Please enter name!",Toast.LENGTH_SHORT);
                passPop.show();
            } else if(unameStr.trim().length()<=0){

                Toast passPop = Toast.makeText(this, "Please enter username!",Toast.LENGTH_SHORT);
                passPop.show();
            }else if(phoneStr.trim().length()<=0){

                Toast passPop = Toast.makeText(this, "Please enter phone number!",Toast.LENGTH_SHORT);
                passPop.show();
            }else if(addressStr.trim().length()<=0){

                Toast passPop = Toast.makeText(this, "Please enter postal address!",Toast.LENGTH_SHORT);
                passPop.show();
            }else if(emailStr.trim().length()<=0){

                Toast passPop = Toast.makeText(this, "Please enter email address!",Toast.LENGTH_SHORT);
                passPop.show();
            }else if(!validateEmail(emailStr)){
                Toast passPop = Toast.makeText(this, "Email address should be in abc@gmail.com format!",Toast.LENGTH_LONG);
                passPop.show();
            }else if(!validatePassword(passStr)){
                Toast passPop = Toast.makeText(this, "Password should contain one capital letter ,number and one special character (@,$,%,#) and greater than 6 characters!",Toast.LENGTH_LONG);
                passPop.show();
            }else
            {
                Person p = new Person();
                p.setName(nameStr);
                p.setUsername(unameStr);
                p.setAddress(addressStr);
                p.setEmail(emailStr);
                p.setPassword(passStr);
                p.setPhone(phoneStr);

                dbs.insertPersonObj(p);
                Intent i = new Intent(Register.this,Landing.class);
                Toast.makeText(Register.this, "User registered successfully!!", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        }
    }
}
