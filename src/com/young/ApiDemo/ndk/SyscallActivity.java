package com.young.ApiDemo.ndk;

import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SyscallActivity extends Activity implements OnClickListener {
    private TextView SyscallTxt;
    private Button methodBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ndk_syscall);
        // get textview
        SyscallTxt = (TextView)findViewById(R.id.syscall_text);
        // get buttons and set listeners
        methodBtn1 = (Button)findViewById(R.id.sys_method_button1);
        methodBtn1.setOnClickListener(this);
    }

    public native String SyscallTest1();

    static {
        System.loadLibrary("syscall");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.sys_method_button1:
            SyscallTxt.setText(SyscallTest1());
            break;
        default:
            break;
        }
    }
}
