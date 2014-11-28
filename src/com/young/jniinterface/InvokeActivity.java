package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InvokeActivity extends Activity {
    private static final String TAG = "JNIitf";
    private Button methodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoke);
        // get button and set listener
        methodBtn = (Button)findViewById(R.id.iv_method_button);
        methodBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainThread();
            }
        });
        globalizeVar();
    }

    public static void invoke(int i) {
        Log.i(TAG, "trigger upcall! (invoke, i = " + i + ")");
    }

    public native void mainThread();
    public native void globalizeVar();

    static {
        System.loadLibrary("invoke");
    }
}
