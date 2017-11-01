package com.example.devendra.sharing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Devendra on 3/20/2017.
 */

public class ManageItems extends MainActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.manageitems);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.manageitems,null,false);
        drawerLayout.addView(contentView,0);

        String [] functions = {"Add Item","Edit Item"};

        ListAdapter functionAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,functions);
        ListView functionList = (ListView) findViewById(R.id.functionList);
        functionList.setAdapter(functionAdapter);

        functionList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch( position )
                        {
                            case 0:  Intent newActivity = new Intent(ManageItems.this, AddItem.class);
                                startActivity(newActivity);
                                break;
                            case 1:  Intent newActivity1 = new Intent(ManageItems.this, EditItem.class);
                                startActivity(newActivity1);
                                break;

                        }
                    }
                }
        );
    }

}
