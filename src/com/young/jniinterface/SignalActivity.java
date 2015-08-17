package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SignalActivity extends Activity implements OnClickListener {
    private TextView signalTxt;
    private Button methodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);
        signalTxt = (TextView)findViewById(R.id.signal_text);
        methodBtn = (Button)findViewById(R.id.sig_method_button);
        methodBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.sig_method_button:
            signalTxt.setText(downcallMtd1());
            break;
        }
    }

    public native String downcallMtd1();

    static {
        System.loadLibrary("signal");
    }
}
