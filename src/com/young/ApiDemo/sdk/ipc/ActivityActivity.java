package com.young.ApiDemo.sdk.ipc;

import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private Button activityStartBtn;
    private Button activityStartForResultBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_ipc_activity);
        Log.i(TAG, "enter SDK IPC Activity Activity");
        Log.i(TAG, "activity - onCreate");
        // get buttons and set listeners
        activityStartBtn = (Button)findViewById(R.id.activity_start_button);
        activityStartForResultBtn = (Button)findViewById(R.id.activity_start_for_result_button);
        activityStartBtn.setOnClickListener(this);
        activityStartForResultBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "activity - onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "activity - onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "activity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "activity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "activity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "activity - onDestroy");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.activity_start_button:
            Intent startActivityIntent = new Intent(ActivityActivity.this, DestActivity.class);
            startActivityIntent.putExtra("booleanValue", false);
            startActivityIntent.putExtra("intValue", 100);
            Bundle bundle = new Bundle();
            bundle.putString("string", "hello");
            bundle.putInt("int", 100);
            bundle.putFloat("float", 3.14f);
            double[] d_array = new double[]{1.1, 1.2, 1.3}; // 'new double[]' can be missed
            bundle.putDoubleArray("double_array", d_array);
            startActivityIntent.putExtras(bundle);
            startActivity(startActivityIntent);
            break;
        case R.id.activity_start_for_result_button:
            Intent startActivityForResultIntent = new Intent(ActivityActivity.this, DestResultActivity.class);
            startActivityForResult(startActivityForResultIntent, 1); // requestCode
            break;
        default:
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1 && resultCode == 2) {
            String result = intent.getStringExtra("dest_result");
            Log.i(TAG, "onActivityResult: " + result);
        }
    }
}
