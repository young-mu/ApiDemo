package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignalActivity extends Activity {
    private static final String TAG = "JNIitf";
    private Button methodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);
        // get button and set listener
        methodBtn = (Button)findViewById(R.id.sig_method_button);
        methodBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, downcallMtd1());
            }
        });
    }

    public native String downcallMtd1();

    static {
        System.loadLibrary("signal");
    }
}
