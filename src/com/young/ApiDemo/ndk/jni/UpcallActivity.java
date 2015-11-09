package com.young.ApiDemo.ndk.jni;

import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UpcallActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private TextView upcallTxt;
    private Button methodBtn1;
    private Button methodBtn2;
    private Button methodBtn3;
    private Button methodBtn4;
    private Button methodBtn5;
    private Button methodBtn6;
    private Button methodBtn7;
    public String str = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ndk_jni_upcall);
        Log.i(TAG, "enter NDK JNI Upcall Activity");
        // get textview
        upcallTxt = (TextView)findViewById(R.id.upcall_text);
        // get buttons and set listeners
        methodBtn1 = (Button)findViewById(R.id.uc_method_button1);
        methodBtn2 = (Button)findViewById(R.id.uc_method_button2);
        methodBtn3 = (Button)findViewById(R.id.uc_method_button3);
        methodBtn4 = (Button)findViewById(R.id.uc_method_button4);
        methodBtn5 = (Button)findViewById(R.id.uc_method_button5);
        methodBtn6 = (Button)findViewById(R.id.uc_method_button6);
        methodBtn7 = (Button)findViewById(R.id.uc_method_button7);
        methodBtn1.setOnClickListener(this);
        methodBtn2.setOnClickListener(this);
        methodBtn3.setOnClickListener(this);
        methodBtn4.setOnClickListener(this);
        methodBtn5.setOnClickListener(this);
        methodBtn6.setOnClickListener(this);
        methodBtn7.setOnClickListener(this);
    }

    public UpcallActivity() {
        // nothing to do
    }

    // static function with passed parameter
    public static String staticUpcall(String str) {
        Log.i(TAG, "trigger upcall! (staticUpcall)");
        return "static upcall. (parameter 'str' = " + str + ")";
    }

    // nonstatic function with modified attribute
    public String nonstaticUpcall() {
        Log.i(TAG, "trigger upcall! (nonstaticUpcall)");
        return "non-static upcall. (attribute 'str' = " + str + ")";
    }

    public native int downcallMtd1();
    public native String downcallMtd2();
    public native String downcallMtd3();
    public native int downcallMtd4();
    public native int downcallMtd5(int init);
    public native int downcallMtd6();
    public native float downcallMtd7();

    static {
        System.loadLibrary("upcall");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.uc_method_button1:
            String version = Integer.toHexString(downcallMtd1());
            upcallTxt.setText("JNI version: " + version);
            break;
        case R.id.uc_method_button2:
            upcallTxt.setText(downcallMtd2());
            break;
        case R.id.uc_method_button3:
            upcallTxt.setText(downcallMtd3());
            break;
        case R.id.uc_method_button4:
            upcallTxt.setText("(NewObject) init = " + downcallMtd4());
            break;
        case R.id.uc_method_button5:
            upcallTxt.setText("(NewObjectV) init = " + downcallMtd5(100));
            break;
        case R.id.uc_method_button6:
            upcallTxt.setText("(NewObjectA) init = " + downcallMtd6());
            break;
        case R.id.uc_method_button7:
            upcallTxt.setText("exectime = " + downcallMtd7() + "s");
            break;
        default:
            break;
        }
    }
}

class testClass {
    private int init;
    public testClass(int init) {
        this.init = init;
    }
}
