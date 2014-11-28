package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = "JNIitf";
    private Button downcallBtn;
    private Button downcallOnloadBtn;
    private Button upcallBtn;
    private Button invokeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "enter MainActivity");
        // get buttons and set listeners
        downcallBtn = (Button)findViewById(R.id.downcall_button);
        downcallOnloadBtn = (Button)findViewById(R.id.downcall_onload_button);
        upcallBtn = (Button)findViewById(R.id.upcall_button);
        invokeBtn = (Button)findViewById(R.id.invoke_button);
        downcallBtn.setOnClickListener(this);
        downcallOnloadBtn.setOnClickListener(this);
        upcallBtn.setOnClickListener(this);
        invokeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.downcall_button:
            Log.i(TAG, "enter DowncallActivity");
            Intent dIntent = new Intent(MainActivity.this, DowncallActivity.class);
            startActivity(dIntent);
            break;
        case R.id.downcall_onload_button:
            Log.i(TAG, "enter DowncallOnloadActivity");
            Intent doIntent = new Intent(MainActivity.this, DowncallOnloadActivity.class);
            startActivity(doIntent);
            break;
        case R.id.upcall_button:
            Log.i(TAG, "enter UpcallActivity");
            Intent uIntent = new Intent(MainActivity.this, UpcallActivity.class);
            startActivity(uIntent);
            break;
        case R.id.invoke_button:
            Log.i(TAG, "enter InvokeActivity");
            Intent iIntent = new Intent(MainActivity.this, InvokeActivity.class);
            startActivity(iIntent);
            break;
        default:
            break;
        }
    }
}
