package com.example.devendra.sharing;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Devendra on 3/15/2017.
 */

public class Login extends Activity {

    DatabaseHelper helper = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }

    public void onMainLogin(View view){
        if(view.getId() == R.id.btnLogin){

            EditText e = (EditText)findViewById(R.id.eTUsername);
            String str = e.getText().toString();

            EditText ePass = (EditText)findViewById(R.id.eTPassword);
            String strPass = ePass.getText().toString();

            if(str.trim().length()<=0){
                Toast userPop = Toast.makeText(this, "Please enter a Username!",Toast.LENGTH_SHORT);
                userPop.show();
            }
            else if(strPass.trim().length()<=0){
                Toast passwordPop = Toast.makeText(this, "Please enter a Password!",Toast.LENGTH_SHORT);
                passwordPop.show();
            } else{
                List list = helper.searchPerson(str);
                String actualPass = list.get(1).toString();
                if (strPass.equals(actualPass)) {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    ((AppData) this.getApplication()).setField1((Integer) list.get(0));
                    i.putExtra("Username", str);
                    startActivity(i);
                } else {
                    Toast personPop = Toast.makeText(Login.this, "User details don't match!", Toast.LENGTH_SHORT);
                    personPop.show();
                }
            }
        }
    }
}
