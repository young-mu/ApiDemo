package com.young.ApiDemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private Button sdkBtn;
    private Button ndkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i(TAG, "enter MainActivity");
        // get buttons and set listeners
        sdkBtn = (Button)findViewById(R.id.sdk_button);
        ndkBtn = (Button)findViewById(R.id.ndk_button);
        sdkBtn.setOnClickListener(this);
        ndkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.sdk_button:
            Intent sdkIntent = new Intent(MainActivity.this, SdkActivity.class);
            startActivity(sdkIntent);
            break;
        case R.id.ndk_button:
            Intent ndkIntent = new Intent(MainActivity.this, NdkActivity.class);
            startActivity(ndkIntent);
            break;
        default:
            break;
        }
    }
}
