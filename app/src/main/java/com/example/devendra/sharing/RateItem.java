package com.example.devendra.sharing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Devendra on 3/27/2017.
 */

public class RateItem extends MainActivity {

    private List<Items> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.rateitem,null,false);
        drawerLayout.addView(contentView,0);


        DatabaseHelper dbs = new DatabaseHelper(this);
        ListView listView = (ListView) findViewById(R.id.lvRate);
        int sessionUserId = ((AppData)this.getApplication()).getField1();
        list = dbs.searchRateItem(sessionUserId);
        if(list != null){
            Log.d("list array", String.valueOf(list.size()));
            RateItemAdapter rateItemAdapter = new RateItemAdapter(this,list);
            listView.setAdapter(rateItemAdapter);
        } else {
            Toast.makeText(RateItem.this, "No Items requested so far!!", Toast.LENGTH_SHORT).show();
        }
    }
}


class RateItemAdapter extends ArrayAdapter<Items> {

    private Context context;
    List<Items> itemsList = null;

    public RateItemAdapter(Context context, List<Items> itemsList) {
        super(context, 0, itemsList);
        this.context = context;
        this.itemsList = itemsList;
    }

    public View getView(int position, View row, ViewGroup parent) {
        // View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.rateitemrow, parent, false);
            TextView itemName;
            RatingBar rb ;
            Button bRequest;

            final int ordersID;
            itemName = (TextView) row.findViewById(R.id.txvRateItem);
            bRequest = (Button) row.findViewById(R.id.bRateItem);
            rb = (RatingBar) row.findViewById(R.id.rateItemBar);
            ordersID = itemsList.get(position).getItemId();
            final float rating = rb.getRating();
            final float dbrating = itemsList.get(position).getRateItems();
            Log.d("rating from database ",""+dbrating);
            rb.setRating(dbrating);
            itemName.setText(itemsList.get(position).getName());
            bRequest.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                   DatabaseHelper dbs = new DatabaseHelper(v.getContext());
                    dbs.updateOrderObj(rating,ordersID);
                    Intent i = new Intent(v.getContext(),MainActivity.class);
                    Toast.makeText(v.getContext(), "Item rated successfully!!", Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(i);

                }
            });
        }
        return row;
    }
}