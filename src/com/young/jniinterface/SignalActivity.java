package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignalActivity extends Activity implements OnClickListener {
    private TextView SignalTxt;
    private Button methodBtn1;
    private Button methodBtn2;
    private Button methodBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);
        // get textview
        SignalTxt = (TextView)findViewById(R.id.signal_text);
        // get buttons and set listeners
        methodBtn1 = (Button)findViewById(R.id.sig_method_button1);
        methodBtn2 = (Button)findViewById(R.id.sig_method_button2);
        methodBtn3 = (Button)findViewById(R.id.sig_method_button3);
        methodBtn1.setOnClickListener(this);
        methodBtn2.setOnClickListener(this);
        methodBtn3.setOnClickListener(this);
    }

    public native String SignalTest1();
    public native String SignalTest2();
    public native String SignalTest3();

    static {
        System.loadLibrary("signal");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.sig_method_button1:
            SignalTxt.setText(SignalTest1());
            break;
        case R.id.sig_method_button2:
            SignalTxt.setText(SignalTest2());
            break;
        case R.id.sig_method_button3:
            SignalTxt.setText(SignalTest3());
            break;
        default:
            break;
        }
    }
}
