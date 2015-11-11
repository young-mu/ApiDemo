package com.young.ApiDemo.sdk.ipc;

import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;

public class DestActivity extends Activity {
    private static final String TAG = "ApiDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_ipc_activity_dest);
        Log.i(TAG, "enter SDK IPC DestActivity Activity");
        boolean booleanValue = getIntent().getBooleanExtra("booleanValue", true);
        boolean booleanValue2 = getIntent().getBooleanExtra("booleanValue2", true);
        int intValue = getIntent().getIntExtra("intValue", 200);
        int intValue2 = getIntent().getIntExtra("intValue2", 200);
        Log.i(TAG, "booleanValue: " + booleanValue + ", intValue: " + intValue);
        Log.i(TAG, "booleanValue2: " + booleanValue2 + ", intValue2: " + intValue2);
    }
}
