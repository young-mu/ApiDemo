package com.young.ApiDemo.sdk.ipc;

import java.lang.String;
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
        boolean booleanValue = this.getIntent().getBooleanExtra("booleanValue", true);
        boolean booleanValue2 = this.getIntent().getBooleanExtra("booleanValue2", true);
        int intValue = this.getIntent().getIntExtra("intValue", 200);
        int intValue2 = this.getIntent().getIntExtra("intValue2", 200);
        Log.i(TAG, "booleanValue: " + booleanValue + ", intValue: " + intValue);
        Log.i(TAG, "booleanValue2: " + booleanValue2 + ", intValue2: " + intValue2);
        Bundle bundle = this.getIntent().getExtras();
        String stringBundle = bundle.getString("string");
        int intBundle = bundle.getInt("int");
        float floatBundle = bundle.getFloat("float");
        double[] doubleArrBundle = bundle.getDoubleArray("double_array");
        Log.i(TAG, "Bundle string: " + stringBundle);
        Log.i(TAG, "Bundle int: " + intBundle);
        Log.i(TAG, "Bundle float: " + floatBundle);
        Log.i(TAG, "Bundle double array: " + doubleArrBundle[0] + ", " + doubleArrBundle[1] + ", " + doubleArrBundle[2]);
    }
}
