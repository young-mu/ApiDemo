package com.young.ApiDemo.ndk.jni;

import com.young.ApiDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

public class DowncallActivity extends Activity implements OnClickListener {
    private static final String TAG = "ApiDemo";
    private TextView downcallTxt;
    private Button simpleDowncallBtn;
    private Button paramTestBtn;
    private Button funopenCallbackBtn;
    private Button fgetsCallbackBtn;
    private Button dlopenLibBtn;
    private Button perfTestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ndk_jni_downcall);
        Log.i(TAG, "enter NDK JNI Downcall Activity");
        downcallTxt = (TextView)findViewById(R.id.downcall_text);
        simpleDowncallBtn = (Button)findViewById(R.id.dc_method_button1);
        paramTestBtn = (Button)findViewById(R.id.dc_method_button2);
        funopenCallbackBtn = (Button)findViewById(R.id.dc_method_button3);
        fgetsCallbackBtn = (Button)findViewById(R.id.dc_method_button4);
        dlopenLibBtn = (Button)findViewById(R.id.dc_method_button5);
        perfTestBtn = (Button)findViewById(R.id.dc_method_button6);
        simpleDowncallBtn.setOnClickListener(this);
        paramTestBtn.setOnClickListener(this);
        funopenCallbackBtn.setOnClickListener(this);
        fgetsCallbackBtn.setOnClickListener(this);
        dlopenLibBtn.setOnClickListener(this);
        perfTestBtn.setOnClickListener(this);
        createFile();
    }

    public void createFile() {
        File fileDir = this.getFilesDir();
        File file = new File(fileDir, "test");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            String str = "12345";
            byte[] buffer = str.getBytes();
            fos.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public native String simpleDowncall();
    public native boolean paramTest(int i1, long i2, float i3);
    public native String funopenCallback();
    public native String fgetsCallback();
    public native String dlopenLib();
    public native boolean perfTest(int i1, int i2);

    static {
        System.loadLibrary("downcall");
    }

    class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            String ret = String.valueOf(paramTest(-1, 0x1234567890abcdefL, -3.14F));
            Log.i(TAG, "paramTest: " + ret);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.dc_method_button1:
            downcallTxt.setText(simpleDowncall());
            break;
        case R.id.dc_method_button2:
            String ret = String.valueOf(paramTest(-1, 0x1234567890abcdefL, -3.14F));
            Log.i(TAG, "paramTest: " + ret);
            downcallTxt.setText(ret);
            myThread mt1 = new myThread();
            myThread mt2 = new myThread();
            mt1.start();
            mt2.start();
            break;
        case R.id.dc_method_button3:
            downcallTxt.setText(funopenCallback());
            break;
        case R.id.dc_method_button4:
            downcallTxt.setText(fgetsCallback());
            break;
        case R.id.dc_method_button5:
            downcallTxt.setText(dlopenLib());
            break;
        case R.id.dc_method_button6:
            long startTime, endTime;
            float time;
            int cnt = 0;
            final int COUNT = 1000000;
            startTime = System.currentTimeMillis();
            for (int i = 0; i < COUNT; i++) {
                if ((perfTest(100, 200)) == true) {
                    cnt++;
                } else {
                    break;
                }
            }
            endTime = System.currentTimeMillis();
            if (cnt == COUNT) {
                time = (float)(endTime - startTime) / 1000;
                downcallTxt.setText("exectime = " + time + "s");
            } else {
                downcallTxt.setText("error");
            }
            break;
        default:
            break;
        }
    }
}
