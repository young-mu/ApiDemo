package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DowncallActivity extends Activity implements OnClickListener {
    private TextView downcallTxt;
    private Button methodBtn1;
    private Button methodBtn2;
    private Button methodBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downcall);
        // get textview
        downcallTxt = (TextView)findViewById(R.id.downcall_text);
        // get buttons and set listeners
        methodBtn1 = (Button)findViewById(R.id.dc_method_button1);
        methodBtn2 = (Button)findViewById(R.id.dc_method_button2);
        methodBtn3 = (Button)findViewById(R.id.dc_method_button3);
        methodBtn1.setOnClickListener(this);
        methodBtn2.setOnClickListener(this);
        methodBtn3.setOnClickListener(this);
    }

    public native String downcallMtd1();
    public native String downcallMtd2();
    public native String downcallMtd3();

    static {
        System.loadLibrary("downcall");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.dc_method_button1:
            downcallTxt.setText(downcallMtd1());
            break;
        case R.id.dc_method_button2:
            downcallTxt.setText(downcallMtd2());
            break;
        case R.id.dc_method_button3:
            downcallTxt.setText(downcallMtd3());
            break;
        default:
            break;
        }
    }
}
