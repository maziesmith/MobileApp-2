package com.example.devendra.sharing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devendra on 3/17/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "person.db";
    private static final String TABLE_NAME = "person";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_UNAME = "username";
    private static final String COLUMN_PASS = "password";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    //SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table person (id INTEGER primary key not null,email TEXT not null,address TEXT not null,name TEXT not null,username TEXT not null,password TEXT not null,phone TEXT not null);";
    private static final String TABLE_ITEM = "create table item (ItemId INTEGER primary key not null,name TEXT not null,category TEXT not null,description TEXT not null, image BLOB not null,userId integer not null);";
    private static final String TABLE_ORDER = "create table orders(orderId INTEGER primary key not null,userId INTEGER not null,ItemId INTEGER not null,sDate TEXT not null,eDate Text not null,orderFlag INTEGER,rate REAL,review TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ORDER);
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_ITEM);
    }


    public void insertPersonObj(Person p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query  = "select * from person";

        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put(COLUMN_ID,count++);
        values.put(COLUMN_EMAIL,p.getEmail());
        values.put(COLUMN_NAME,p.getName());
        values.put(COLUMN_UNAME,p.getUsername());
        values.put(COLUMN_PASS,p.getPassword());
        values.put(COLUMN_PHONE,p.getPhone());
        values.put(COLUMN_ADDRESS,p.getAddress());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public long insertItemObj(Items i){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query  = "select * from item";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put("ItemId",count++);
        values.put("name",i.getName());
        values.put("description",i.getDescription());
        values.put("category",i.getCategory());
        values.put("image",i.getImage());
        values.put("userId",i.getUserId());
        long rowinserted = db.insert("item",null,values);
        db.close();
        return rowinserted;
    }

    public long insertOrderObj(Order o){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query  = "select * from orders";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put("orderId",count++);
        values.put("ItemId",o.getItemId());
        values.put("userId",o.getUserId());
        values.put("sDate",o.getSDate());
        values.put("eDate",o.getEDate());
        values.put("rate",o.getRate());
        values.put("review",o.getReview());
        values.put("orderFlag ",o.getOrderFlag());
        long rowinserted = db.insert("orders",null,values);
        db.close();
        return rowinserted;
    }

    public void updateOrderObj(float rate,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("rate",rate);
        db.update("orders",values,"orderId="+id,null);
        db.close();
    }

    public List searchPerson(String userNameStr){
        SQLiteDatabase db = this.getReadableDatabase();
        String fetchQuery = "Select id, username , password from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(fetchQuery,null);
        String uname , password;
        int id;
        List list = new ArrayList();
        password = "not found";
        if(cursor.moveToFirst()){
            do {
                uname = cursor.getString(1);
                if (uname.equals(userNameStr)) {
                    id = cursor.getInt(0);
                    password = cursor.getString(2);
                    list.add(0,id);
                    list.add(1,password);
                    break;
                }
            }while (cursor.moveToNext());

        }
        return list;
    }


    public List<Items> searchItem(String categoryStr,int sessionUserId){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Items> listItems = new ArrayList<Items>();
        String fetchQuery = "Select ItemId,name,category,userId from item";
        Cursor cursor = db.rawQuery(fetchQuery,null);
        String dbItemName , dbcategory;
        int dbItemId,dbUserId;
        if(cursor.moveToFirst()){
            do {
                dbcategory = cursor.getString(2);
                dbUserId = cursor.getInt(3);

                if (dbcategory.equals(categoryStr) && sessionUserId != dbUserId) {
                    dbItemId = cursor.getInt(0);
                    dbItemName = cursor.getString(1);
                    Items items = new Items();
                    items.setItemId(dbItemId);
                    items.setName(dbItemName);
                    items.setCategory(dbcategory);
                    listItems.add(items);
                }
            }while (cursor.moveToNext());

        }
        return listItems;
    }

    public List<Items> searchRateItem(int sessionUserId){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Items> list = new ArrayList<Items>();
        String fetchQuery = "Select item.name,orders.userId,orders.orderId,orders.rate from orders INNER JOIN item on orders.ItemId = item.ItemId";
        Cursor cursor = db.rawQuery(fetchQuery,null);
        String dbItemName;
        int dbUserId,dbOrdersId;
        float dbItemrate;
        if(cursor.moveToFirst()){
            do {
                dbUserId = cursor.getInt(1);
                if (sessionUserId == dbUserId) {
                    dbOrdersId = cursor.getInt(2);
                    dbItemName = cursor.getString(0);
                    dbItemrate = cursor.getFloat(3);

                    Items items = new Items();
                    items.setItemId(dbOrdersId); //stores the order id for the requested object
                    items.setName(dbItemName);
                    items.setRateItems(dbItemrate);
                    list.add(items);
                }
            }while (cursor.moveToNext());

        }
        return list;
    }

    public List<Items> dashboardItem(int sessionUserId){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Items> list = new ArrayList<Items>();
        String fetchQuery = "Select ItemId,name,category,userId from item";
        Cursor cursor = db.rawQuery(fetchQuery,null);
        String dbItemName , dbcategory;
        int dbItemId,dbUserId;
        if(cursor.moveToFirst()){
            do {
                dbcategory = cursor.getString(2);
                dbUserId = cursor.getInt(3);

                if (sessionUserId == dbUserId) {
                    dbItemId = cursor.getInt(0);
                    dbItemName = cursor.getString(1);
                    Items items = new Items();
                    items.setItemId(dbItemId);
                    items.setName(dbItemName);
                    items.setCategory(dbcategory);
                    list.add(items);
                }
            }while (cursor.moveToNext());

        }
        return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        String query1 = "DROP TABLE IF EXISTS item";
        String query2 = "DROP TABLE IF EXISTS orders";

        db.execSQL(query);
        db.execSQL(query1);
       // db.execSQL(TABLE_ITEM);
        //db.execSQL(TABLE_ORDER);
        this.onCreate(db);
    }
}
