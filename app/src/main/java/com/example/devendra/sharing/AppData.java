package com.example.devendra.sharing;

import android.app.Application;

/**
 * Created by Devendra on 3/26/2017.
 */

public class AppData extends Application {
    int field1;
    String field2;

    public int getField1() {
        return field1;
    }

    public void setField1(int field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
