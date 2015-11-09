package com.young.ApiDemo;

import com.young.ApiDemo.ndk.JniActivity;
import com.young.ApiDemo.ndk.SyscallActivity;
import com.young.ApiDemo.ndk.SignalActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NdkActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private Button jniBtn;
    private Button syscallBtn;
    private Button signalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ndk);
        Log.i(TAG, "enter NDK Activity");
        // get buttons and set listeners
        jniBtn = (Button)findViewById(R.id.jni_button);
        syscallBtn = (Button)findViewById(R.id.syscall_button);
        signalBtn = (Button)findViewById(R.id.signal_button);
        jniBtn.setOnClickListener(this);
        syscallBtn.setOnClickListener(this);
        signalBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.jni_button:
            Intent jniIntent = new Intent(NdkActivity.this, JniActivity.class);
            startActivity(jniIntent);
            break;
        case R.id.syscall_button:
            Intent syscallIntent = new Intent(NdkActivity.this, SyscallActivity.class);
            startActivity(syscallIntent);
            break;
        case R.id.signal_button:
            Intent signalIntent = new Intent(NdkActivity.this, SignalActivity.class);
            startActivity(signalIntent);
            break;
        default:
            break;
        }
    }
}
