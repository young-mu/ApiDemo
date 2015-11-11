package com.young.ApiDemo.sdk;

import com.young.ApiDemo.sdk.ipc.ActivityActivity;
import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IpcActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private Button activityBtn;
    private Button serviceBtn;
    private Button contentProviderBtn;
    private Button broadcastReceiverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdk_ipc);
        Log.i(TAG, "enter SDK IPC Activity");
        // get buttons and set listeners
        activityBtn = (Button)findViewById(R.id.activity_button);
        serviceBtn = (Button)findViewById(R.id.service_button);
        contentProviderBtn = (Button)findViewById(R.id.contentprovider_button);
        broadcastReceiverBtn = (Button)findViewById(R.id.broadcastreceiver_button);
        activityBtn.setOnClickListener(this);
        serviceBtn.setOnClickListener(this);
        contentProviderBtn.setOnClickListener(this);
        broadcastReceiverBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.activity_button:
            Intent activityIntent = new Intent(IpcActivity.this, ActivityActivity.class);
            startActivity(activityIntent);
            break;
        case R.id.service_button:
            break;
        case R.id.contentprovider_button:
            break;
        case R.id.broadcastreceiver_button:
            break;
        default:
            break;
        }
    }
}
