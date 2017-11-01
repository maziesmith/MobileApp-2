package com.example.devendra.sharing;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.category;
import static android.R.id.list;

/**
 * Created by Devendra on 3/30/2017.
 */

public class Dashboard extends MainActivity {

    private List<Items> list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dashboard,null,false);
        drawerLayout.addView(contentView,0);

        DatabaseHelper dbs = new DatabaseHelper(this);
        ListView listView = (ListView) findViewById(R.id.lvDashboard);
        int sessionUserId = ((AppData)this.getApplication()).getField1();
        list = dbs.dashboardItem(sessionUserId);
        if(!list.isEmpty()){
                DashboardAdapter dashboardAdapter = new DashboardAdapter(this,list);
                listView.setAdapter(dashboardAdapter);
        } else {
            Toast.makeText(Dashboard.this, "No Items found", Toast.LENGTH_SHORT).show();
        }
    }
}
class DashboardAdapter extends ArrayAdapter<Items> {

    private Context context;
    List<Items> itemsList = null;

    public DashboardAdapter(Context context, List<Items> itemsList) {
        super(context, 0, itemsList);
        this.context = context;
        this.itemsList = itemsList;
    }

    public View getView(int position, View row, ViewGroup parent) {
        // View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.dashboardrow, parent, false);

                TextView itemName = (TextView) row.findViewById(R.id.txvDashboardItem);
                itemName.setText(itemsList.get(position++).getName());

        }
        return row;
    }
}