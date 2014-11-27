package com.young.jniinterface;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DowncallActivity extends Activity {
    private TextView downcallTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downcall);
        downcallTxt = (TextView)findViewById(R.id.downcall_text);
        downcallTxt.setText(downcall());
    }

    public native String downcall();

    static {
        System.loadLibrary("downcall");
    }
}
