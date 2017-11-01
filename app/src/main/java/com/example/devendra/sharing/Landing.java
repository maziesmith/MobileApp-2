package com.example.devendra.sharing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Devendra on 3/15/2017.
 */

public class Landing extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);

    }

    public void onLoginClick(View view){
        if(view.getId() == R.id.btnLogin){
            Intent i = new Intent(Landing.this,Login.class);
            startActivity(i);
        }

        if(view.getId() == R.id.btnRegister){
            Intent i = new Intent(Landing.this,Register.class);
            startActivity(i);
        }

    }
}
