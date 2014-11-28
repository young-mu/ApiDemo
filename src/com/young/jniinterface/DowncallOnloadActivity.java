package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DowncallOnloadActivity extends Activity implements OnClickListener {
    private TextView downcallOnloadTxt;
    private Button methodBtn1;
    private Button methodBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downcall_onload);
        // get textview
        downcallOnloadTxt = (TextView)findViewById(R.id.downcall_onload_text);
        // get buttons and set listeners
        methodBtn1 = (Button)findViewById(R.id.dco_method_button1);
        methodBtn2 = (Button)findViewById(R.id.dco_method_button2);
        methodBtn1.setOnClickListener(this);
        methodBtn2.setOnClickListener(this);
    }

    public native String downcallOnloadMtd1();
    public native String downcallOnloadMtd2();

    static {
        System.loadLibrary("downcall_onload");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.dco_method_button1:
            downcallOnloadTxt.setText(downcallOnloadMtd1());
            break;
        case R.id.dco_method_button2:
            downcallOnloadTxt.setText(downcallOnloadMtd2());
            break;
        default:
            break;
        }
    }
}
