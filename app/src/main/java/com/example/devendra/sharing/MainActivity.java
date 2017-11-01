package com.example.devendra.sharing;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    private ActionBarDrawerToggle aToggle;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*String username  = getIntent().getStringExtra("Username");
        TextView textView = (TextView) findViewById(R.id.tvUsername);
        textView.setText(username);*/

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        aToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(aToggle);
        aToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(aToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onManageItemClick(MenuItem view){
        Intent intent = new Intent(MainActivity.this, ManageItems.class);
        this.startActivity(intent);

        return  true;
    }

    public boolean onLogoutClick(MenuItem view){

        Intent landingPage=new Intent(MainActivity.this,Landing.class);
        landingPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(landingPage);
        this.finish();

        return  true;
    }

    public boolean onSearchItemClick(MenuItem view){
        Intent intent = new Intent(MainActivity.this, SearchItem.class);
        this.startActivity(intent);

        return  true;
    }

    public boolean onRateItemClick(MenuItem view){
        Intent intent = new Intent(MainActivity.this, RateItem.class);
        this.startActivity(intent);

        return  true;
    }

    public boolean onDashboardClick(MenuItem view){
        Intent intent = new Intent(MainActivity.this, Dashboard.class);
        this.startActivity(intent);

        return  true;
    }
}

