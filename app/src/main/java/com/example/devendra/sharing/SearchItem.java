package com.example.devendra.sharing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ServiceCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import android.support.design.widget.Snackbar;
import static android.R.attr.resource;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static java.security.AccessController.getContext;

/**
 * Created by Devendra on 3/25/2017.
 */

public class SearchItem extends MainActivity implements DatePickerDialog.OnDateSetListener{

    TextView textView ,textView1;
    public static Boolean flag = null;
    Spinner spinner;
    DatabaseHelper dbs ;
    ItemAdapter itemAdapter;
    ListView listView;
    private List<Items> list = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.searchitem);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.searchitem,null,false);
        drawerLayout.addView(contentView,0);

        dbs = new DatabaseHelper(this);
        spinner = (Spinner)findViewById(R.id.SearchSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SearchItem.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.itemList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year,month,dayOfMonth);
        setDate(cal);
    }


    public void setDate(final Calendar calender){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD);
        if(flag){
            textView = (TextView)findViewById(R.id.fromDate);
            textView.setText(dateFormat.format(calender.getTime()));
        }else{
            textView1 = (TextView)findViewById(R.id.toDate);
            textView1.setText(dateFormat.format(calender.getTime()));
        }
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);
        }
    }

    public void onSearchItemClick(View view){
        String category = (String) spinner.getSelectedItem();
        if(category.equals("Select")){
            Toast categoryPop = Toast.makeText(this, "Please enter a Category!",Toast.LENGTH_SHORT);
            categoryPop.show();
        }else{
            listView = (ListView) findViewById(R.id.lvItem);
            int sessionUserId = ((AppData)this.getApplication()).getField1();
            list = dbs.searchItem(category,sessionUserId);
            if(list != null){
                itemAdapter = new ItemAdapter(this,list);
                listView.setAdapter(itemAdapter);
            }else{
                Toast.makeText( SearchItem.this, "No item Found !!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


class ItemAdapter extends ArrayAdapter<Items> {

    private Context context;
    int layoutResourceId;
    List<Items> itemsList = null;
    public static int itemId;

    public ItemAdapter(Context context, List<Items> itemsList) {
        super(context,0,itemsList);
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
       // View row = convertView;

        if(row == null){
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(R.layout.itemrow,parent,false);
        TextView itemName ;
        final EditText fromDate ;
        final EditText toDate ;
        Button bFromDate;
        Button bToDate;
        RatingBar rb ;
        Button bRequest;

        itemName = (TextView) row.findViewById(R.id.txvItemName);
        fromDate = (EditText) row.findViewById(R.id.fromDate);
        toDate = (EditText) row.findViewById(R.id.toDate);
        bFromDate = (Button) row.findViewById(R.id.bFromDate);
        bToDate = (Button) row.findViewById(R.id.bToDate);
        bRequest = (Button) row.findViewById(R.id.bSelectItem);
        rb = (RatingBar) row.findViewById(R.id.ItemratingBar);

        itemName.setText(itemsList.get(position).getName());
            itemId = itemsList.get(position).getItemId();

        bFromDate.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SearchItem.flag = true;
                DialogFragment fragment = new SearchItem.DatePickerFragment();
                fragment.show(((FragmentActivity)context).getSupportFragmentManager(),"From date picker dialog");

            }
        });
            bToDate.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    SearchItem.flag = false;
                    DialogFragment fragment = new SearchItem.DatePickerFragment();
                    fragment.show(((FragmentActivity)context).getSupportFragmentManager(),"To date picker dialog");

                }
            });

            bRequest.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                   try {
                       Date  compareFromDate = new SimpleDateFormat("MM/dd/yy").parse(fromDate.getText().toString());
                        Date  compareToDate = new SimpleDateFormat("MM/dd/yy").parse(toDate.getText().toString());
                        Date todayDate = new Date();

                        if(compareFromDate.after(compareToDate)){
                            Toast.makeText( v.getContext(), "From Date is after To date !!", Toast.LENGTH_SHORT).show();
                        }else if(compareToDate.before(compareFromDate)){
                            Toast.makeText( v.getContext(), "From Date is after To date !!", Toast.LENGTH_SHORT).show();
                        }else if(compareFromDate.before(todayDate)){
                            Toast.makeText( v.getContext(), "From Date should be greater than current date !!", Toast.LENGTH_LONG).show();
                        }else if(compareToDate.before(todayDate)){
                            Toast.makeText( v.getContext(), "To Date should be greater than current date !!", Toast.LENGTH_LONG).show();
                        }else{
                            DatabaseHelper dbs = new DatabaseHelper(v.getContext());
                            Order p = new Order();
                            p.setItemId(itemId);
                            p.setUserId(((AppData)v.getContext().getApplicationContext()).getField1());
                            p.setSDate(fromDate.getText().toString());
                            p.setEDate(toDate.getText().toString());
                            //p.setRate(passStr);
                            //p.setReview(phoneStr);
                            //p.setOrderFlag(1);
                            dbs.insertOrderObj(p);
                            Intent i = new Intent(v.getContext(),MainActivity.class);
                            Toast.makeText(v.getContext(), "Item requested successfully!!", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(i);
                        }
                }catch (ParseException e) {
                    e.printStackTrace();
                }
            }});
        }
        return row;
    }
}
