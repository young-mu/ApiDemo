package com.young.jniinterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;
import android.os.Environment;

public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = "JNIitf";
    private Button downcallBtn;
    private Button downcallOnloadBtn;
    private Button upcallBtn;
    private Button invokeBtn;
    private Button signalBtn;
    private Button syscallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "enter MainActivity");
        // get buttons and set listeners
        downcallBtn = (Button)findViewById(R.id.downcall_button);
        downcallOnloadBtn = (Button)findViewById(R.id.downcall_onload_button);
        upcallBtn = (Button)findViewById(R.id.upcall_button);
        invokeBtn = (Button)findViewById(R.id.invoke_button);
        signalBtn = (Button)findViewById(R.id.signal_button);
        syscallBtn = (Button)findViewById(R.id.syscall_button);
        downcallBtn.setOnClickListener(this);
        downcallOnloadBtn.setOnClickListener(this);
        upcallBtn.setOnClickListener(this);
        invokeBtn.setOnClickListener(this);
        signalBtn.setOnClickListener(this);
        syscallBtn.setOnClickListener(this);
        getSystemABI();
        getEnvironmentDirectories();
        getApplicationDirectories(this);
    }

    public void getSystemABI() {
        // get ABI from Java level
        Log.i(TAG, "CPU_ABI(java): " + Build.CPU_ABI);
        String[] abis = Build.SUPPORTED_ABIS;
        for (int i = 0; i < abis.length; i ++) {
            Log.i(TAG, "abis[" + i + "](java): " + abis[i]);
        }
        // get ABI from native level
        try {
            Process process = Runtime.getRuntime().exec("getprop ro.product.cpu.abi");
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Log.i(TAG, "CPU_ABI(native): " + bufferedReader.readLine());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getEnvironmentDirectories() {
        Log.i(TAG, "getRootDirectory(): " + Environment.getRootDirectory().toString());
        Log.i(TAG, "getDataDirectory(): " + Environment.getDataDirectory().toString());
        Log.i(TAG, "getDownloadCacheDirectory(): " + Environment.getDownloadCacheDirectory().toString());
        Log.i(TAG, "getExternalStorageDirectory(): " + Environment.getExternalStorageDirectory().toString());
        Log.i(TAG, "getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES): " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
        Log.i(TAG, "isExternalStorageEmulated(): " + Environment.isExternalStorageEmulated());
        Log.i(TAG, "isExternalStorageRemovable(): " + Environment.isExternalStorageRemovable());
    }

    public void getApplicationDirectories(Context context) {
        Log.i(TAG, "getFilesDir(): " + context.getFilesDir().toString());
        Log.i(TAG, "getCacheDir(): " + context.getCacheDir().toString());
        Log.i(TAG, "getExternalFilesDir(null): " + context.getExternalFilesDir(null).toString());
        Log.i(TAG, "getExternalCacheDir(): " + context.getExternalCacheDir().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.downcall_button:
            Log.i(TAG, "enter DowncallActivity");
            Intent dIntent = new Intent(MainActivity.this, DowncallActivity.class);
            startActivity(dIntent);
            break;
        case R.id.downcall_onload_button:
            Log.i(TAG, "enter DowncallOnloadActivity");
            Intent doIntent = new Intent(MainActivity.this, DowncallOnloadActivity.class);
            startActivity(doIntent);
            break;
        case R.id.upcall_button:
            Log.i(TAG, "enter UpcallActivity");
            Intent uIntent = new Intent(MainActivity.this, UpcallActivity.class);
            startActivity(uIntent);
            break;
        case R.id.invoke_button:
            Log.i(TAG, "enter InvokeActivity");
            Intent iIntent = new Intent(MainActivity.this, InvokeActivity.class);
            startActivity(iIntent);
            break;
        case R.id.signal_button:
            Log.i(TAG, "enter SignalActivity");
            Intent sigIntent = new Intent(MainActivity.this, SignalActivity.class);
            startActivity(sigIntent);
            break;
        case R.id.syscall_button:
            Log.i(TAG, "enter SyscallActivity");
            Intent sysIntent = new Intent(MainActivity.this, SyscallActivity.class);
            startActivity(sysIntent);
            break;
        default:
            break;
        }
    }
}
