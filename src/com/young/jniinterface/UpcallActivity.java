package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UpcallActivity extends Activity implements OnClickListener {
    private static final String TAG = "JNIitf";
    private TextView upcallTxt;
    private Button methodBtn1;
    private Button methodBtn2;
    public String str = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcall);
        // get textview
        upcallTxt = (TextView)findViewById(R.id.upcall_text);
        // get buttons and set listeners
        methodBtn1 = (Button)findViewById(R.id.uc_method_button1);
        methodBtn2 = (Button)findViewById(R.id.uc_method_button2);
        methodBtn1.setOnClickListener(this);
        methodBtn2.setOnClickListener(this);
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

    // downcall (call staticUpcall)
    public native String downcallMtd1();
    // downcall (call nonstaticUpcall)
    public native String downcallMtd2();

    static {
        System.loadLibrary("upcall");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.uc_method_button1:
            upcallTxt.setText(downcallMtd1());
            break;
        case R.id.uc_method_button2:
            upcallTxt.setText(downcallMtd2());
            break;
        default:
            break;
        }
    }
}
