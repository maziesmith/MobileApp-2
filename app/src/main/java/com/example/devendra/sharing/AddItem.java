package com.example.devendra.sharing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.jar.Manifest;


/**
 * Created by Devendra on 3/20/2017.
 */

public class AddItem extends MainActivity {

    ImageView imageUpload;
    Button bUploadmage , bAddItem;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_CODE_GALLERY= 999;

    Uri imageUri;
    Spinner spinner;

    DatabaseHelper dbs ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Commented below line to get drawable layout*/
        //setContentView(R.layout.additem);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.additem,null,false);
        drawerLayout.addView(contentView,0);

        dbs = new DatabaseHelper(this);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddItem.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.itemList));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        imageUpload = (ImageView) findViewById(R.id.uploadImage);
        bUploadmage = (Button) findViewById(R.id.bUploadImage);
        bAddItem = (Button) findViewById(R.id.bAddItem);

        bUploadmage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddItem.this , new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY
                );
//                openGallery();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setType("image/*");
                startActivityForResult(gallery,REQUEST_CODE_GALLERY);
            }else {
                Toast.makeText(getApplicationContext(),"You do not have permission to access file location!!",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode  == RESULT_OK && requestCode == REQUEST_CODE_GALLERY && data!=null){
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageUpload.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public void onAddItemClick(View view){

        if(view.getId() == R.id.bAddItem){
            EditText name = (EditText)findViewById(R.id.edTItemName);
            EditText description = (EditText)findViewById(R.id.editText2);
            String category = (String) spinner.getSelectedItem();

            //ImageView imageView = (ImageView) findViewById(R.id.imageView);

            String nameStr = name.getText().toString();
            String descStr = description.getText().toString();
            //String categoryStr = category.getText().toString();

            if(nameStr.trim().length()<=0){
                Toast passPop = Toast.makeText(this, "Please enter Item title!",Toast.LENGTH_SHORT);
                passPop.show();
            }
            else if(descStr.trim().length()<=0){
                Toast passPop = Toast.makeText(this, "Please enter Item description!",Toast.LENGTH_SHORT);
                passPop.show();
            }
            else if(imageUpload == null){
                Toast passPop = Toast.makeText(this, "Please enter Item photo!",Toast.LENGTH_SHORT);
                passPop.show();
            }else {
                Bitmap bitmap = ((BitmapDrawable) imageUpload.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                Items i = new Items();
                i.setName(nameStr);
                i.setDescription(descStr);
                i.setCategory(category);
                i.setImage(imageInByte);
                i.setUserId(((AppData)this.getApplication()).getField1());
                long rowInserted = dbs.insertItemObj(i);
                if(rowInserted != -1){
                    Toast.makeText(AddItem.this, "New item inserted successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddItem.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AddItem.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
