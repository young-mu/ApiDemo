package com.young.ApiDemo.sdk;

import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataStorageActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private Button spBtn;
    private Button sqliteBtn;
    private Button cpBtn;
    private Button fileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_ds);
        Log.i(TAG, "enter SDK DataStorage Activity");
        // get buttons and set listeners
        spBtn = (Button)findViewById(R.id.sp_button);
        sqliteBtn = (Button)findViewById(R.id.sqlite_button);
        cpBtn = (Button)findViewById(R.id.cp_button);
        fileBtn = (Button)findViewById(R.id.file_button);
        spBtn.setOnClickListener(this);
        sqliteBtn.setOnClickListener(this);
        cpBtn.setOnClickListener(this);
        fileBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.sp_button:
            Log.i(TAG, "store SharedPreferences data");
            SharedPreferences sp_store = getSharedPreferences("sp_test", MODE_PRIVATE);
            Editor editor = sp_store.edit();
            editor.putString("sp_string", "I'm SP string");
            editor.commit();
            Log.i(TAG, "access SharedPreferences data");
            SharedPreferences sp_access = getSharedPreferences("sp_test", MODE_PRIVATE);
            String sp_string = sp_access.getString("sp_string", "NOT found");
            String sp_string2 = sp_access.getString("sp_string2", "NOT found");
            Log.i(TAG, "sp_string: " + sp_string);
            Log.i(TAG, "sp_string2: " + sp_string2);
        case R.id.sqlite_button:
            break;
        case R.id.cp_button:
            break;
        case R.id.file_button:
            break;
        default:
            break;
        }
    }
}
