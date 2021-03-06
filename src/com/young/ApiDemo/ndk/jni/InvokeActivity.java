package com.young.ApiDemo.ndk.jni;

import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InvokeActivity extends Activity {
    private static final String TAG = "ApiDemo";
    private Button methodBtn;
    private TextView invokeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ndk_jni_invoke);
        Log.i(TAG, "enter NDK JNI Invoke Activity");
        invokeTxt = (TextView)findViewById(R.id.invoke_text);
        // get button and set listener
        methodBtn = (Button)findViewById(R.id.iv_method_button);
        methodBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mainThread();
                invokeTxt.setText("OK");
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
